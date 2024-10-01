package com.practice.mathematical;

public class Class1 {
	String MULTI_STORY="Multi-Story";
	String RANCH="Ranch";
	Class3 c=new Class3();
	public Class1(int n)
	{
		this.MULTI_STORY=c.getInput("Multi-Story").concat(" "+this.MULTI_STORY);
	}
	public Class1(double n)
	{
		this.RANCH=c.getInput("Ranch").concat(" "+this.RANCH);
	}
	
	public String getMulti()
	{
		return this.MULTI_STORY;
	}
	public String getRanch()
	{
		return this.RANCH;
	}

}
