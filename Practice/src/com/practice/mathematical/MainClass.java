package com.practice.mathematical;

import javax.swing.JOptionPane;

public class MainClass {

	public static void main(String[] args) {
		Class1 c1 = new Class1(1);
		Class1 c2 = new Class1(1.0);
		JOptionPane.showMessageDialog(null, "THE NUMBER OF HOUSES YOU BUILT LAST YEAR\n" 
		+ "you built " + c1.getMulti()
				+ "houses\n" + "you built " + c2.getRanch()+"houses");
	}

}
