package com.practice.mathematical;

import javax.swing.JOptionPane;

public class Class3 {
	public String getInput(String story) {
		String type="";
		if(story.equals("Multi-Story"))
		{
			type=(JOptionPane.showInputDialog("Enter the number of Multy-story house "
					+ "you built last month"));
			return type;
		}
		else
		{
			type=(JOptionPane.showInputDialog("Enter the number of Ranch houses you "
					+ "built last month"));
			return type;
		}
	}
}
