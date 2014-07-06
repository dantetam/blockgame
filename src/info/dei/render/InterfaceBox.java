package info.dei.render;

import processing.core.PApplet;

public class InterfaceBox extends PApplet {

	public double posX, posY;
	public double sizeX, sizeY;
	public boolean active;
	public String text;
	
	public InterfaceBox(Runner runner, String text, double posX, double posY, double sizeX, double sizeY)
	{
		this.text = text;
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		active = false;
	}
	
	public void setup()
	{
		noLoop();
		textSize(12);
	}
	
	public void draw()
	{
		if (active)
		{
			background(255);
			fill(255,0,0);
			text(text,0,(float)sizeY/2);
		}
		else
		{
			background(0);
			fill(255);
			text(text,0,(float)sizeY/2);
		}
	}
	
}
