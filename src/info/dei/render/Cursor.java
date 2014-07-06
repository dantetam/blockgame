package info.dei.render;

import processing.core.PApplet;

public class Cursor extends PApplet {

	public Cursor()
	{
		
	}
	
	public void setup()
	{
		noLoop();
		stroke(255);
		fill(0);
	}
	
	public void draw()
	{
		background(0);
		rect(0,0,4,4);
	}
	
}
