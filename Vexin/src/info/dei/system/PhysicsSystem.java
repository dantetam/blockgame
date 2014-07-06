package info.dei.system;

import java.util.ArrayList;

import info.dei.entity.Entity;
import info.dei.render.Runner;
import info.dei.service.ChunkService.Chunk;

public class PhysicsSystem extends BaseSystem {

	public PhysicsSystem(Runner p) {
		super(p);
	}

	public void tick()
	{
		boolean heldUp = false; //whether or not there is a levelpart under the player
		ArrayList<Chunk> chunks = p.chunkSystem.findChunksWithin( //only search relevant chunks
				p.chunkSystem.findPlayerChunk(p.player).row, 
				p.chunkSystem.findPlayerChunk(p.player).col, 3);
		for (int i = 0; i < chunks.size(); i++)
		{
			Chunk chunk = chunks.get(i);
			for (int j = 0; j < chunk.occupants.size(); j++)
			{
				Entity en = chunk.occupants.get(j);
				if (en.canCollide) //TODO: Add a collision property to blocks.
				{
					if (p.player.nearCollides(en))
					{
						if (en.posY + en.sizeY/2 - p.player.posY < 8 || p.flying)
						{
							//p.player.posY = (float) (en.posY + en.sizeY/2 + 4);
							p.player.moveTo(p.player.posX, (float) (en.posY + en.sizeY/2 + 5), p.player.posZ);
							heldUp = true;
						}
						else
						{
							//TODO: Reverse direction
						}
					}
				}
			}
		}

		if (!heldUp && !p.flying)
		{
			p.player.move(0, -1, 0);
		}
	}


}
