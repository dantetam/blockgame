package info.dei.system;

import info.dei.render.Runner;

//Controls all delayed services.

public class TickSystem extends BaseSystem {

	public int frame = 0;
	
	public TickSystem(Runner p) {
		super(p);
	}

	public void tick()
	{
		frame++;
		if (frame % 300 == 0)
		{
			p.player.hunger -= 0.25;
			p.burningSystem.tick();
			p.processSystem.tick();
			p.chickenSystem.specialTick();
		}
		if (frame % 30 == 0)
		{
			p.personSystem.tick();
			//System.out.println(p.chunkSystem.findPlayerChunk(p.d).row + "," + p.chunkSystem.findPlayerChunk(p.d).col);
		}
	}
	
}
