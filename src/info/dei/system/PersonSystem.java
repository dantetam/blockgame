package info.dei.system;

import java.util.ArrayList;

import info.dei.entity.*;
import info.dei.render.Runner;

public class PersonSystem extends BaseSystem {

	public PersonSystem(Runner p) {
		super(p);
	}
	
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public void init(Player player)
	{

	}
	
	public void tick() 
	{
		
	}
}
