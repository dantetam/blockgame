package info.dei.system;

import java.util.ArrayList;

import info.dei.entity.Entity;
import info.dei.render.Runner;

//Contains all animals, NPCs, etc. and controls their behavior. A delayed service

public class AnimalSystem extends BaseSystem {

	//list of all animals in a level
	public static ArrayList<Entity> animals;
	
	public AnimalSystem(Runner p) {
		super(p);
		animals = new ArrayList<Entity>();
	}
	
	public void tick()
	{
		for (int i = 0; i < animals.size(); i++)
		{
			Entity en = animals.get(i);
			en.moveTo(en.posX + 0.1F*Math.cos(en.rotY), en.posY, en.posZ + 0.1F*Math.sin(en.rotY));
		}
	}
	
	public void specialTick()
	{
		for (int i = 0; i < animals.size(); i++)
		{
			animals.get(i).rotY = Math.random()*360;
		}
	}

}

