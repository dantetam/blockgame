package info.dei.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import info.dei.entity.Entity;
import info.dei.entity.Item;

public class CraftingService {

	//list of recipes, key is typeId of product, entry is an arraylist of ingredients
	private static HashMap<Entity,ArrayList<Integer>> recipes;
	
	public static void setupRecipes()
	{
		recipes = new HashMap<Entity,ArrayList<Integer>>();
		recipes.put(new Entity(2),new ArrayList<Integer>(Arrays.asList(3)));
		//recipes.put(new Entity(3),new ArrayList<Integer>(Arrays.asList(2,2)));
		recipes.put(new Entity(8),new ArrayList<Integer>(Arrays.asList(2,2)));
		recipes.put(new Entity(28),new ArrayList<Integer>(Arrays.asList(21,21)));
		recipes.put(new Entity(29),new ArrayList<Integer>(Arrays.asList(25)));
		recipes.put(new Entity(30),new ArrayList<Integer>(Arrays.asList(25,25,25)));
		recipes.put(new Entity(100),new ArrayList<Integer>(Arrays.asList(5)));
		recipes.put(new Entity(101),new ArrayList<Integer>(Arrays.asList(27,27,27,27)));
		recipes.put(new Entity(102),new ArrayList<Integer>(Arrays.asList(3,3)));
		recipes.put(new Entity(103),new ArrayList<Integer>(Arrays.asList(6)));
		recipes.put(new Entity(104),new ArrayList<Integer>(Arrays.asList(27,27)));
		//recipes.put(new Entity(1000),new ArrayList<Integer>(Arrays.asList(2,8)));
		recipes.put(new Entity(1001),new ArrayList<Integer>(Arrays.asList(2,100)));
		recipes.put(new Entity(1002),new ArrayList<Integer>(Arrays.asList(2,2,100)));
		recipes.put(new Entity(1003),new ArrayList<Integer>(Arrays.asList(2,8,100)));
		//recipes.put(new Entity(1010),new ArrayList<Integer>(Arrays.asList(6)));
		
		recipes.put(new Entity(-1),new ArrayList<Integer>(Arrays.asList(28)));
		recipes.put(new Entity(-2),new ArrayList<Integer>(Arrays.asList(29)));

	}
	
	public static Item craft(ArrayList<Integer> items)
	{
		for (Entry i : recipes.entrySet())
		{
			Entity key = (Entity) i.getKey();
			ArrayList<Integer> value = (ArrayList<Integer>) i.getValue();
			if (arrayEquals(value,items))
			{
				Item en = new Item(key.typeId);
				return en;
			}
		}
		return null;
	}
	
	public static boolean arrayEquals(ArrayList<Integer> a, ArrayList<Integer> b)
	{
		Collections.sort(a); Collections.sort(b);
		if (a.size() != b.size()) return false;
		for (int i = 0; i < a.size(); i++)
		{
			if (!a.get(i).equals(b.get(i)))
			{
				return false;
			}
		}
		return true;
	}
	
}
