package info.dei.level;

import info.dei.entity.Entity;

import java.util.ArrayList;

//A level containing all the entities in a map.

public class Level {
	
	public ArrayList<Entity> entities;
	
	public Level()
	{
		entities = new ArrayList<Entity>();
	}
	
	public void add(Entity entity)
	{
		entities.add(entity);
	}

}
