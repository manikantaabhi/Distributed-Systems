package com.practice.mathematical;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the path where I can load userdata");
		String path=sc.next();
		System.out.println("Please enter the path where I can load content details");
		String contentPath = sc.next();
		System.out.println("I am ready to accept client requests....");
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
        	while(true)
        	{
        		Socket socket = serverSocket.accept();
        		System.out.println("client connected...");
        		new Thread(new IndexServerHandler(socket,path,contentPath)).start();;
        	}
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

class IndexServerHandler implements Runnable {
	private Socket socket;
	private String path;
	private String contentPath;
	private HashMap<String, String> userList=new HashMap<String, String>();;
	private HashMap<String, String> contentList = new HashMap<String, String>();
	public IndexServerHandler(Socket socket, String path, String contentPath) {
		this.socket = socket;
		this.path=path;
		this.contentPath=contentPath;
	}

	@Override
	public void run() {
		try {
			loadUsers(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader in;
		boolean login=false;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
  
         	while(true) {
             out.println("Choose an option:`1. Register user`2. Login`3. Search content`4. Exit");
             int clientRes=Integer.parseInt(in.readLine());
             System.out.println("response: "+clientRes);
             if(clientRes==1)
             {
            	 out.println("please enter name:");
            	 String name=in.readLine();
            	 out.println("please enter email");
            	 String email=in.readLine();
            	out.println("Please enter username:");
            	String username=in.readLine();
            	out.println("please enter password:");
            	String pass=in.readLine();
            	
            	if(registerUser(name,email,username,pass,path))
            	{
            		out.print("User created Successfully`");
            		continue;
            	}
             }
             else if(clientRes==2)
             {
            	 out.println("Please enter username:");
             	String username=in.readLine();
             	out.println("please enter password:");
             	String pass=in.readLine();
            	if(login(username,pass))
            	{
            		 out.print("you have logged in successfully`");
            		 login=true;
            	}
             }
             else if(clientRes==3)
             {
            	 if(login)
            	 {
            		 while(true) {
		            	 out.println("Please enter name of the file you need to search:");
		            	 String contentName=in.readLine();
		            	 System.out.println(contentName);
		            	 String[] ipPort = searchContent(contentPath, contentName);
		            	 System.out.println("After searchcontent");
		            	 if(ipPort == null)
		            	 {
		            		 out.println("Content Not Found`");
		            	 }
		            	 else {
			            	 out.print(ipPort[0]+" "+ipPort[1]+" "+ipPort[2]+"`");
			            	 out.flush();
			            	 break;
		            	 }
            		 }
            	 }
            	 else
            		 out.print("please login before searching content`");
             }
             else if(clientRes==4)
             {
            	 out.println("close");
            	 break;
             }
             else
            	 out.println("choose options from 1,2,3,4");
         	}
         		
         	
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private boolean login(String username, String pass) {
		if(pass.equals(userList.get(username)))
				return true;
		else
			return false;
	}

	private String[] searchContent(String path, String contentName) throws FileNotFoundException, IOException {
		File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                   
            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] contentDetails = line.split("\\s+");
                    if (contentDetails.length == 5) {
                    	String name=contentDetails[0];
                    	String type=contentDetails[1];
                    	String avl= contentDetails[2];
                        String ip = contentDetails[3];
                        String port = contentDetails[4];
                        if(name.equals(contentName))
                        	return new String[]{ip,port,name};
                    }
                }
            }
        }
		return null;
	}

	private void loadUsers(String path) throws IOException {
		File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                   
            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] userData = line.split("\\s+");
                    if (userData.length == 4) {
                        String username = userData[2];
                        String password = userData[3];
                        userList.put(username,password);
                    }
                }
            }
        }
	}
	

	private boolean registerUser(String name,String email,String username, String password,String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
            try {
                file.createNewFile();
                   
            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
            }
        } else {
        	try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) { // Set 'true' for appending
               
        		bw.write(name+ " " + email + " " + username + " " + password);
        		bw.newLine();
        		userList.put(username, password);
        		return true;
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
        }
		return false;
	}

	
}

