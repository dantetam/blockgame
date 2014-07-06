package info.dei.service;

import java.util.HashMap;

public class SpawningService {

	/*
	 * This hashmap declares the chances a certain block spawning in a certain biome.
	 * The entry represents the biome number.
	 * The key of int[][] is a table containing other tables in this format:
	 * 
	 * (example key)
	 * {
	 *       {27 (tall grass id), 3 (quantity min), 5 (quantity max), 0.5 (50% chance of happening)},
	 *       {28, 4, 4, 0.25},
	 *       {29, 1, 5, 0.15},
	 *       ...
	 * }
	 * 
	 * If the first number is not an integer, it is a model
	 * Models are spawned in the form
	 * 
	 * (example key)
	 * {
	 *       {27 (tall grass id), 0, 0, 0}, (first part, no offset, attached to ground)
	 *       {27, 0, 4, 0}, (second part, 4 higher than the first)
	 *       {27, 1, 4, 1}, (third part, with 3d offset)
	 *       ...
	 * }
	 */
	
	public static HashMap<Integer, double[][]> biomeSpawnChance;
	public static HashMap<Double, double[][]> spawnModels;

	public static void init()
	{
		biomeSpawnChance = new HashMap<Integer, double[][]>();
		spawnModels = new HashMap<Double, double[][]>();
		
		biomeSpawnChance();
		spawnModels();
	}
	
	public static void spawnModels()
	{
		//1-2 rocks and minerals
		//2-3 trees and such
		spawnModels.put(1.01, new double[][]{
				new double[]{3,0,0,0},
				new double[]{3,0,4,0},
		});
		spawnModels.put(2.005, new double[][]{
				new double[]{4,0,0,0},
				new double[]{20,0,4,0},
		});
		spawnModels.put(2.01, new double[][]{
				new double[]{5,0,0,0},
				new double[]{21,0,7,0},
				new double[]{20,0,9,0},
		});
		spawnModels.put(2.02, new double[][]{
				new double[]{5,0,0,0},
				new double[]{21,0,7,0},
				new double[]{20,0,9,0},
				new double[]{22,3,6,3},
				new double[]{22,3,6,-3},
				new double[]{22,-3,6,3},
				new double[]{22,-3,6,-3},
		});
		spawnModels.put(2.03, new double[][]{
				new double[]{6,0,0,0},
				new double[]{6,0,8,0},
				new double[]{21,4,16,4},
				new double[]{21,4,16,-4},
				new double[]{21,-4,16,4},
				new double[]{21,-4,16,-4},
				new double[]{21,4,18,4},
				new double[]{21,4,18,-4},
				new double[]{21,-4,18,4},
				new double[]{21,-4,18,-4},
				new double[]{21,0,20,0},
		});
		spawnModels.put(2.04, new double[][]{
				new double[]{6,0,0,0},
				new double[]{6,0,8,0},
				new double[]{21,4,16,4},
				new double[]{21,4,16,-4},
				new double[]{21,-4,16,4},
				new double[]{21,-4,16,-4},
				new double[]{21,4,18,4},
				new double[]{21,4,18,-4},
				new double[]{21,-4,18,4},
				new double[]{21,-4,18,-4},
				new double[]{21,0,20,0},
				new double[]{24,6,13.5,6},
				new double[]{24,6,13.5,-6},
				new double[]{24,-6,13.5,6},
				new double[]{24,-6,13.5,-6},
		});
		spawnModels.put(2.11, new double[][]{
				new double[]{23,0,0,0},
				new double[]{23,0,5,0},
		});
		spawnModels.put(2.12, new double[][]{
				new double[]{23,0,0,0},
				new double[]{23,0,5,0},
				new double[]{23,0,10,0},
		});
		spawnModels.put(2.20, new double[][]{
				new double[]{27,0,0,0},
				new double[]{27,0,4,0},
		});
	}

	public static void biomeSpawnChance()
	{
		biomeSpawnChance.put(0, new double[][]{
				new double[]{1.01D,1,3,0.10},
				new double[]{2,6,12,0.10},
				new double[]{3,2,5,0.05},
				new double[]{9,2,10,0.5},
		});
		biomeSpawnChance.put(1, new double[][]{
				new double[]{1.01D,1,3,0.10},
				new double[]{2,5,10,0.05},
				new double[]{3,2,5,0.05},
				new double[]{9,2,10,0.5},
		});
		biomeSpawnChance.put(2, new double[][]{
				new double[]{1.01D,1,3,0.05},
				new double[]{2.11D,1,4,0.05},
				new double[]{2.12D,1,3,0.05},
				new double[]{2,5,10,0.10},
				new double[]{3,2,5,0.05},
				//new double[]{9,2,10,0.5},
		});
		biomeSpawnChance.put(3, new double[][]{
				new double[]{2.01D,1,3,0.15},
				new double[]{2.20D,4,8,0.3},
				new double[]{25,15,25,0.05},
				new double[]{27,10,20,0.25},
		});
		biomeSpawnChance.put(4, new double[][]{
				new double[]{2.005,1,3,0.5},
				new double[]{2.20D,4,8,0.2},
				new double[]{27,10,20,0.3},
		});
		biomeSpawnChance.put(5, new double[][]{
				new double[]{2.01D,1,3,0.5},
				new double[]{2.02D,1,1,0.05},
				new double[]{2.03D,1,2,0.1},
				new double[]{27,3,10,0.25},
		});
		biomeSpawnChance.put(6, new double[][]{
				new double[]{2.005,1,3,0.25},
				new double[]{2.01D,3,5,0.35},
				new double[]{2.03D,1,2,0.5},
				new double[]{2.04D,1,1,0.05},
		});
		biomeSpawnChance.put(7, new double[][]{
				
		});
	}

}
