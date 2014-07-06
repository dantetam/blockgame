package info.dei.system;

import info.dei.entity.Entity;
import info.dei.render.Runner;
import info.dei.service.ChunkService.Chunk;

//Safely removes objects from the world with the remove flag. 

public class TrashSystem extends BaseSystem {

	public TrashSystem(Runner p) {
		super(p);
	}
	
	public void tick()
	{
		for (int i = p.level.entities.size() - 1; i >= 0; i--)
		{
			if (p.level.entities.get(i).removeFlag)
			{
				Entity en = p.level.entities.get(i);
				Chunk chunk = p.chunkSystem.findChunk(p.level.entities.get(i).chunkRow, p.level.entities.get(i).chunkCol); 
				//Remove entity from chunk
				for (int j = 0; j < chunk.occupants.size(); j++)
				{
					if (chunk.occupants.get(j).equals(en))
					{
						chunk.occupants.remove(j);
						break;
					}
				}
				p.level.entities.remove(i);
			}
		}
		for (int i = p.player.inventory.size() - 1; i >= 0; i--)
		{
			if (p.player.inventory.get(i).removeFlag)
			{
				p.player.inventory.remove(i);
			}
		}
	}

}
