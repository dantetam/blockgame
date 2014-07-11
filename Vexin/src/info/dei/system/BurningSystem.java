package info.dei.system;

import java.util.ArrayList;

import info.dei.entity.Entity;
import info.dei.render.Runner;
import info.dei.service.Data;

//Controls all burning things. A delayed service.

public class BurningSystem extends BaseSystem {

	public ArrayList<Entity> burningThings = new ArrayList<Entity>();

	public BurningSystem(Runner p)
	{
		super(p);
	}

	public void tick()
	{
		//if (burningThings.size() < 0) return;
		//System.out.println(burningThings.size());
		for (int i = 0; i < burningThings.size(); i++)
		{
			Entity burning = burningThings.get(i);
			if (--burning.burning <= 0)
			{
				burning.removeFlag = true;
				burningThings.remove(burning);
			}
		}
		for (int i = 0; i < burningThings.size(); i++)
		{
			for (int j = 0; j < p.level.entities.size(); j++)
			{
				Entity en = p.level.entities.get(j);
				//System.out.println(en.name);

				if (en.name.contains("Branch") || en.name.contains("Leaves") || en.name.contains("Log") || en.name.contains("Bark") || en.name.contains("Wood"))
				{
					if (Math.random() < 0.1)
					{
						if (burningThings.get(i).distTo(en) < 10 && en.burning <= 0)
						{
							en.burning = (int)(en.sizeX*en.sizeY*en.sizeZ/30);
							burningThings.add(en);
						}
					}
				}
				else if (en.name.contains("Raw"))
				{
					if (burningThings.get(i).distTo(en) < 10)
					{
						Entity newEn = new Entity(Data.rawItems.get(en.typeId));
						newEn.moveTo(en.posX, en.posY, en.posZ);
						p.level.add(newEn);
						en.removeFlag = true;
					}
				}
			}
		}
	}
}

