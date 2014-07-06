package info.dei.service;

import java.util.ArrayList;

public class Color {

	public double r,g,b;

	public Color(double x, double y, double z)
	{
		if (r < 1 && g < 1 && b < 1)
		{
			r = x*255;
			g = y*255;
			b = z*255;
		}
		else if (r > 255 && g > 255 && b > 255)
		{
			r = x/255;
			g = y/255;
			b = z/255;
		}
		else
		{
			r = x;
			g = y;
			b = z;
		}
	}

	public String toString()
	{
		return r + "," + g + "," + b;
	}

	public boolean equals(Color c)
	{
		return (int)r == (int)c.r && (int)g == (int)c.g && (int)b == (int)c.b;
	}

	public static ArrayList<Color> usedColors = new ArrayList<Color>();
	public static Color returnNewColor()
	{
		//random number from 1 to 254
		Color color = new Color(((Math.random()*254) + 1)/255D,((Math.random()*254) + 1)/255D,((Math.random()*254) + 1)/255D);
		for (int i = 0; i < usedColors.size(); i++)
		{
			if (usedColors.get(i).equals(color))
				return returnNewColor(); //return a new color if used already
		}
		usedColors.add(color);
		//System.out.println(color.toString());
		return color;
	}

}
