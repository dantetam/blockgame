package info.dei.entity;

import java.util.ArrayList;

import info.dei.render.Runner;
import info.dei.service.ChunkService.Chunk;
import info.dei.vector.*;

//player is 8 tall?

public class Player extends Person {

	public float tarX, tarY, tarZ; //what the player is looking at

	public ArrayList<Item> inventory = new ArrayList<Item>();
	
	public Runner runner;

	public Player(Runner runner)
	{
		super(runner);
		name = "Player";
		hunger = 100;
		sizeX = 5;
		sizeY = 10;
		sizeZ = 5;
		this.runner = runner;
	}

	public boolean equals(Player o)
	{
		return posX == o.posX && posY == o.posY && posZ == o.posZ; 
	}
	
	public void giveItem(Item en)
	{
		inventory.add(en);
	}

	public Line getLookVector()
	{
		return new Line(tarX-posX,posX,tarY-posY,posY,tarZ-posZ,posZ);
	}
	
	public Plane[] lookingAtEntity(Entity en)
	{
		Line lookVector = getLookVector();
		//Find 3 closest planes.
		//Find intersection of player's look vector with these planes.
		//Return an array of the planes that intersect.
		//Point pPoint = new Point(posX,posY,posZ);
		Plane[] faces = new Plane[6];
		//Order of these faces matters.
		faces[0] = (planeFromPoints(
				new Point(en.posX + en.sizeX/2,en.posY,en.posZ),
				new Point(en.posX + en.sizeX/2,en.posY+en.sizeY/2,en.posZ),
				new Point(en.posX + en.sizeX/2,en.posY+en.sizeY/2,en.posZ+en.sizeZ/2)
				));
		faces[1] = (planeFromPoints(
				new Point(en.posX - en.sizeX/2,en.posY,en.posZ),
				new Point(en.posX - en.sizeX/2,en.posY+en.sizeY/2,en.posZ),
				new Point(en.posX - en.sizeX/2,en.posY+en.sizeY/2,en.posZ+en.sizeZ/2)
				));
		faces[2] = (planeFromPoints(
				new Point(en.posX,en.posY + en.sizeY/2,en.posZ),
				new Point(en.posX+en.sizeX/2,en.posY + en.sizeY/2,en.posZ+en.sizeZ/2),
				new Point(en.posX+en.sizeX/2,en.posY + en.sizeY/2,en.posZ+en.sizeZ/2)
				));
		faces[3] = (planeFromPoints(
				new Point(en.posX,en.posY - en.sizeY/2,en.posZ),
				new Point(en.posX+en.sizeY/2,en.posY - en.sizeY/2,en.posZ),
				new Point(en.posX+en.sizeY/2,en.posY - en.sizeY/2,en.posZ+en.sizeZ/2)
				));
		faces[4] = (planeFromPoints(
				new Point(en.posX,en.posY,en.posZ + en.sizeZ/2),
				new Point(en.posX,en.posY+en.sizeY/2,en.posZ + en.sizeZ/2),
				new Point(en.posX+en.sizeX/2,en.posY+en.sizeY/2,en.posZ + en.sizeZ/2)
				));
		faces[5] = (planeFromPoints(
				new Point(en.posX,en.posY,en.posZ - en.sizeZ/2),
				new Point(en.posX,en.posY+en.sizeY/2,en.posZ - en.sizeZ/2),
				new Point(en.posX+en.sizeX/2,en.posY+en.sizeY/2,en.posZ - en.sizeZ/2)
				));

		ArrayList<Point> intersections = new ArrayList<Point>();
		for (int i = 0; i < faces.length; i++)
		{
			intersections.add(faces[i].intersect(lookVector));
		}
		
		for (int i = 0; i < intersections.size(); i++)
		{
			if (!en.within(intersections.get(i).x,intersections.get(i).y,intersections.get(i).z))
			{
				faces[i] = null;
			}
		}

		return faces;

		/*ArrayList<Point> closestFaces = new ArrayList<Point>();
		for (int i = 0; i < 3; i++)
		{
			double min = 100000;
			int flag = 0;
			for (int j = 0; j < faces.size(); j++)
			{
				double dist = pPoint.dist(faces.get(i));
				if (dist < min) 
				{
					min = dist;
					flag = j;
				}
			}
			closestFaces.add(faces.get(flag));
			faces.remove(flag);
		}
		 */
	}

	public Plane planeFromPoints(Point a, Point b, Point c)
	{
		int x = (int)((b.y - a.y)*(c.z - a.z) - (c.y - a.y)*(b.z - a.z));
		int y = (int)(-(b.x - a.x)*(c.z - a.z) + (c.x - a.x)*(b.z - a.z));
		int z = (int)((b.x - a.x)*(c.y - a.y) - (c.x - a.x)*(b.y - a.y));
		int d = (int)(x*a.x + y*a.y + z*a.z);
		//int e = (int)(x*b.x + y*b.y + z*b.z);
		//System.out.println(d + " " + e);
		return new Plane(x,y,z,d);
	}

	public double distTo(Entity en)
	{
		return Math.sqrt( Math.pow(posX-en.posX,2) + Math.pow(posY-en.posY,2) + Math.pow(posZ-en.posZ,2));
	}

}
