package com.practice.mathematical;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Pattern1 {

	public static void main(String[] args) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\saidi\\eclipse-workspace\\AOSProject1\\src\\abcd.txt", true))) { // 'true' for appending
            String line="skljdflkjflaslkdfjlkjfjkhfs skldjfasklshf";

                writer.write(line);                // Write the line
                writer.newLine();                  // Add a new line
           
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

	}

}
