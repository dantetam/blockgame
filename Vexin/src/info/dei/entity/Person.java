package info.dei.entity;

import info.dei.render.Runner;
import info.dei.service.ChunkService.Chunk;

import java.util.ArrayList;

public class Person {

	public String name; 
	public boolean removeFlag; //notice to be removed, ref: TrashSystem
	public float posX, posY, posZ;
	public float sizeX, sizeY, sizeZ;
	
	public float hunger;
	
	//The queue of activities is the macro. 
	//The activities themselves are micro.
	public ArrayList<String> macro = new ArrayList<String>();
	public int micro = 0;
	public Runner runner;
	
	public Person(Runner runner)
	{
		this.runner = runner;
	}
	
	public void moveTo(float x, float y, float z)
	{
		float prevX = posX;
		float prevY = posY;
		float prevZ = posZ;
		posX = (float)x;
		posY = (float)y;
		posZ = (float)z;
		Chunk chunk = runner.chunkSystem.findPlayerChunk((Player) this);
		for (int i = 0; i < chunk.occupants.size(); i++)
		{
			Entity en = chunk.occupants.get(i);
			if (en.name.equals("Levelpart"))
			{
				//System.out.println("Player " + d.posX + " " + d.posY + " " + d.posZ);
				//System.out.println("Colliding " + en.posX + " " + en.posY + " " + en.posZ);
				if (collides(en))
				{
					posX = prevX;
					posY = prevY;
					posZ = prevZ;
					//d.tarX = tX;
					//d.tarY = tY;
					//d.tarZ = tZ;
					break;
				}
			}
		}
	}
	
	public boolean collides(Entity en)
	{
		return (
				posX + sizeX/2 > en.posX - en.sizeX/2 &&
				posY + sizeY/2 > en.posY - en.sizeY/2 &&
				posZ + sizeZ/2 > en.posZ - en.sizeZ/2 &&
				posX - sizeX/2 < en.posX + en.sizeX/2 &&
				posY - sizeY/2 < en.posY + en.sizeY/2 &&
				posZ - sizeZ/2 < en.posZ + en.sizeZ/2 
				);
		/*
		return (
				posX + sizeX/2 > en.posX - en.sizeX/2 &&
				posY > en.posY - en.sizeY/2 &&
				posZ + sizeZ/2 > en.posZ - en.sizeZ/2 &&
				posX - sizeX/2 < en.posX + en.sizeX/2 &&
				posY < en.posY + en.sizeY/2 &&
				posZ - sizeZ/2 < en.posZ + en.sizeZ/2 
				);
		*/
	}
	
	public boolean nearCollides(Entity en)
	{
		return (
				posX + sizeX/2 >= en.posX - en.sizeX/2 - 1 &&
				posY + sizeY/2 >= en.posY - en.sizeY/2 - 1 &&
				posZ + sizeZ/2 >= en.posZ - en.sizeZ/2 - 1 &&
				posX - sizeX/2 <= en.posX + en.sizeX/2 + 1 &&
				posY - sizeY/2 <= en.posY + en.sizeY/2 + 1 &&
				posZ - sizeZ/2 <= en.posZ + en.sizeZ/2 + 1
				);
		/*
		return (
				posX + sizeX/2 > en.posX - en.sizeX/2 &&
				posY > en.posY - en.sizeY/2 &&
				posZ + sizeZ/2 > en.posZ - en.sizeZ/2 &&
				posX - sizeX/2 < en.posX + en.sizeX/2 &&
				posY < en.posY + en.sizeY/2 &&
				posZ - sizeZ/2 < en.posZ + en.sizeZ/2 
				);
		*/
	}
	
	public void move(double x, double y, double z)
	{
		float prevX = posX;
		float prevY = posY;
		float prevZ = posZ;
		posX += (float)x;
		posY += (float)y;
		posZ += (float)z;
		Chunk chunk = runner.chunkSystem.findPlayerChunk((Player) this);
		for (int i = 0; i < chunk.occupants.size(); i++)
		{
			Entity en = chunk.occupants.get(i);
			if (en.name.equals("Levelpart"))
			{
				//System.out.println("Player " + d.posX + " " + d.posY + " " + d.posZ);
				//System.out.println("Colliding " + en.posX + " " + en.posY + " " + en.posZ);
				if (collides(en))
				{
					posX = prevX;
					posY = prevY;
					posZ = prevZ;
					//d.tarX = tX;
					//d.tarY = tY;
					//d.tarZ = tZ;
					break;
				}
			}
		}
	}
	
}
