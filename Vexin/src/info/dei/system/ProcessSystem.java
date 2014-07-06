package info.dei.system;

import java.util.ArrayList;

import info.dei.render.Runner;
import info.dei.entity.Entity;

//Controls all timed conversion i.e. rotting leaves into compost. A delayed service.

public class ProcessSystem extends BaseSystem {

	public static ArrayList<Entity> processingThings;
	
	public ProcessSystem(Runner p) {
		super(p);
		processingThings = new ArrayList<Entity>();
	}

	public void tick()
	{
		for (int i = 0; i < processingThings.size(); i++)
		{
			Entity en = processingThings.get(i);
			if (--en.ticker == 0)
			{
				Entity replace = null;
				if (en.typeId == -1)
				{
					replace = new Entity(26);
				}
				else if (en.typeId == -2)
				{
					replace = new Entity(9001);
				}
				replace.moveTo(en.posX, en.posY, en.posZ);
				p.level.entities.add(replace);
				
				en.removeFlag = true;
				processingThings.remove(i);
				i--;
			}
			else
			{
				System.out.println(en.ticker);
			}
		}
	}
	
}
