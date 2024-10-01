package com.practice.mathematical;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;
public class Client {
    public static void main(String[] args) {
    	Scanner sc = new Scanner (System.in);
    	System.out.println("Please enter ip address of index server:");
    	String indexIp=sc.next();
    	System.out.println("Please enter port of index server:");
    	int indexPort=sc.nextInt();
    	System.out.println("Please give me the path where I can search for the content requested by any client:");
    	String contentPath=sc.next();
        try (Socket socket = new Socket(indexIp, indexPort)) {
        	System.out.println("Please enter port number to run client server:");
        	
        	int port = sc.nextInt();
        	new Thread(() -> Client.startPeerServer(port,contentPath)).start();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String serverResponse;
            while (true) {
            	
                serverResponse = in.readLine();
                String arr[]=serverResponse.split("`");
                //System.out.println(serverResponse);
                if(arr[0].equals("Content Not Found"))
                {
                	System.out.println("Incorrect File name");
                	continue;
                }
                if(isIp(arr[0].split(" ")[0]))
                {
                	System.out.println(arr[0].split(" ")[0]);
                	downloadFiles(arr[0].split(" ")[0],Integer.parseInt(arr[0].split(" ")[1]),arr[0].split(" ")[2]);
                	System.out.println("file downloaded successfully...");
                	break;
                }
                for (String string : arr) {
					System.out.println(string);
				}
                
                if(serverResponse.equals("close"))
                	break;
               
                String input=stdIn.readLine();
                out.println(input);
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
    
    private static void downloadFiles(String ip, int port,String fileName) {
    	System.out.println("trying to download....");
    	 try (Socket socket = new Socket(ip, port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
    		 	out.println(fileName);
    		 	System.out.println("enter the path where you want to store the file:");
    		 	Scanner sc=new Scanner(System.in);
    		 	String path=sc.next()+"\\"+fileName+".txt";
    		 	
    		 	System.out.println("path="+path);
    		 	try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.replace("\\", "\\\\"), true))) { // 'true' for appending
    	            String line;

    	            while ((line = in.readLine()) != null) {
    	                System.out.println("client machine=" + line);
    	                writer.write(line);
    	                writer.newLine();
    	            }
    	        } catch (IOException e) {
    	            System.out.println("An error occurred while writing to the file: " + e.getMessage());
    	        }
    		        System.out.println("file saved at:"+path);
    		 
            } catch (IOException e) {
                e.printStackTrace();
            }
		
	}

	private static boolean isIp(String string) {
		final String IPV4_REGEX =
	            "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})$";
	    
	    final Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

	        return IPV4_PATTERN.matcher(string).matches();
	}

    private static void startPeerServer(int port,String contentPath) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Client server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new PeerServerHandler(socket,contentPath)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	 static class PeerServerHandler implements Runnable {
	        private Socket socket;
	        private String contentPath;
	        public PeerServerHandler(Socket socket,String contentPath) {
	            this.socket = socket;
	            this.contentPath=contentPath;
	        }

	        @Override
	        public void run() {
	        	System.out.println("new client connected..");
	            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
	            	String requestedFile = in.readLine();
	            	String filePath=contentPath+"\\"+requestedFile+".txt";
	            	BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
	                String line;
	                while((line=fileReader.readLine())!=null)
	                {
	                	out.print(line);
	                	out.println();
	                }
	            	System.out.println("new="+requestedFile);
	                socket.close();

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
