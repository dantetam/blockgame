package info.dei.entity;

import info.dei.service.Data;

public class Item {

	public String name;
	public int typeId; //special type of thingy, ref: Data
	//public int physId;
	public boolean removeFlag; //ref: TrashSystem
	
	public Item(int typeId)
	{
		Entity en = Data.entityTypeMap.get(typeId);
		this.typeId = typeId;
		name = en.name;
	}
	
	public Entity toEntity()
	{
		return new Entity(typeId);
	}
	
}
