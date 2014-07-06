package info.dei.system;

import info.dei.render.Runner;

//Base class for all systems. All systems must tick.

public abstract class BaseSystem {

	public Runner p;
	
	public BaseSystem(Runner p)
	{
		this.p = p;
	}
	
	public abstract void tick();
	
}
