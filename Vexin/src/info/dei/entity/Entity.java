package info.dei.entity;

import info.dei.service.Data;
import info.dei.system.AnimalSystem;
import info.dei.system.ProcessSystem;

public class Entity {

	//data common to all entities
	public String name; 
	public int typeId; //special entity, ref: Data
	public boolean removeFlag; //notice to be removed, ref: TrashSystem

	//extra block data
	public double posX, posY, posZ;
	public double sizeX, sizeY, sizeZ;
	public int color;
	public boolean locked; //whether or not it can be taken by the player
	public double rotX, rotY, rotZ;
	public int burning; //how long an object is burning for, ref: BurningSystem
	public int harvestTool; //if a special tool is needed
	public int ticker; //frames to do something, ref: TickSystem
	public boolean selected;
	public double transparency;
	public boolean canCollide; //whether or not people can go through it
	
	//chunk data, ref: ChunkSystem
	public int chunkRow = -1; 
	public int chunkCol = -1;
	
	public Entity(int typeId)
	{
		Entity en = Data.entityTypeMap.get(typeId);
		this.typeId = typeId;
		sizeX = en.sizeX;
		sizeY = en.sizeY;
		sizeZ = en.sizeZ;
		color = en.color;
		name = en.name;
		harvestTool = en.harvestTool;
		transparency = en.transparency;
		canCollide = en.canCollide;

		locked = false;
		burning = 0;
		ticker = 0;
		selected = false;

		if (typeId > 9000) 
		{
			AnimalSystem.animals.add(en);
			en.harvestTool = 3;
			//println(ed[10]);
		}

		moveTo(Math.random()*200-100,Math.random()*200-100,Math.random()*200-100);
	}
	
	public static double[] boundingBox(Entity[] en)
	{
		//Some default values. May or may not be replaced. Finds the minimum and maximum values of the box.
		double minX = en[0].posX; 
		double minY = en[0].posY;
		double minZ = en[0].posZ;
		double maxX = en[0].posX; 
		double maxY = en[0].posY;
		double maxZ = en[0].posZ;
		for (int i = 0; i < en.length; i++)
		{
			if (en[i].posX < minX) minX = en[i].posX;
			if (en[i].posY < minY) minY = en[i].posY;
			if (en[i].posZ < minZ) minZ = en[i].posZ;
			if (en[i].posX > maxX) maxX = en[i].posX;
			if (en[i].posY > maxY) maxY = en[i].posY;
			if (en[i].posZ > maxZ) maxZ = en[i].posZ;
		}
		return new double[]{minX, minY, minZ, maxX, maxY, maxZ};
	}

	public Item toItem()
	{
		Item en = new Item(typeId);
		//physId = 2;
		return en;
	}

	public void move(double x, double y, double z)
	{
		posX += x;
		posY += y;
		posZ += z;
	}
	
	public void moveTo(double x, double y, double z)
	{
		posX = x;
		posY = y;
		posZ = z;
		
	}

	public void rotate(double x, double y, double z)
	{
		rotX = x;
		rotY = y;
		rotZ = z;
	}

	public boolean within(double x, double y, double z)
	{
		return 
				x >= posX - sizeX/2 && 
				x <= posX + sizeX/2 &&
				y >= posY - sizeY/2 && 
				y <= posY + sizeY/2 &&
				z >= posZ - sizeZ/2 && 
				z <= posZ + sizeZ/2;
	}

	public Entity(int typeId, double sizeX, double sizeY, double sizeZ, int color, String name, int harvestTool, double transparency, boolean canCollide)
	{
		this.typeId = typeId;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.color = color;
		this.name = name;
		this.harvestTool = harvestTool;
		this.transparency = transparency;
		this.canCollide = canCollide;

		if (typeId > 9000) AnimalSystem.animals.add(this);

		locked = false;
	}

	public double distTo(Entity en)
	{
		return Math.sqrt( Math.pow(posX-en.posX,2) + Math.pow(posY-en.posY,2) + Math.pow(posZ-en.posZ,2));
	}

	public void setTicker(int ticker)
	{
		ProcessSystem.processingThings.add(this);
		locked = true;
		this.ticker = ticker;
		//return this;
	}

}
