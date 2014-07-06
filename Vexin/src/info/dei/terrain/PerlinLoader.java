package info.dei.terrain;

import java.util.ArrayList;

import info.dei.entity.Entity;
import info.dei.level.Level;
import info.dei.service.ChunkService;
import info.dei.service.ChunkService.Chunk;
import info.dei.service.Data;
import info.dei.service.SpawningService;
import info.dei.terrain.PerlinNoise;

//Takes a level and imports Perlin terrain into it.

public class PerlinLoader {

	public ChunkService chunkService;
	public int[][] biomes;
	public int rows; public int cols;
	public long seed = 870L;
	
	public double[][] terrain;

	public PerlinLoader(Level level, int r, int c, double nDiv)
	{
		rows = r;
		cols = c;
		//this.chunkService = chunkService;
		double[][] terrain = assignNewTerrain(nDiv);
		double[][] temperature = assignTemperature(nDiv);
		double[][] rain = assignRain(temperature);

		//ChunkService.printTable(temperature);
		//System.out.println(" ");
		//ChunkService.printTable(rain);
		//System.out.println(" ");
		assignBiomes(temperature, rain);

		int seaLevel = addTerrain(level, terrain, temperature, rain);
		
		this.terrain = terrain;
		
		new IslandHelper(terrain, seaLevel);
	}

	//int chunkLength = chunkService.returnChunkLength();
	//double[][] oldSource = new PerlinNoise().makePerlinNoise(chunkLength,chunkLength,3,8,3,0.5,2);
	//temperature = new PerlinNoise().toInt(PerlinNoise.recurInter(oldSource,2,chunkLength/2));

	//Assigns resources to their native biomes (ice to permafrost, trees to forest, etc.)
	public void assignStuff()
	{
		//System.out.println(chunkService);
		for (int i = 0; i < chunkService.chunks.size(); i++)
		{
			Chunk chunk = chunkService.chunks.get(i);
			int biome;
			try {biome = biomes[chunk.row][chunk.col];} 
			catch (Exception e) {
				System.out.println(chunk.row + " " + chunk.col); 
				chunk.clear();
				continue;
			}
			for (int j = 0; j < chunk.occupants.size(); j++)
			{
				Entity part = chunk.occupants.get(j);
				if (!part.name.equals("Levelpart"))
				{
					continue;
				}
				/*if (biome >= 2 && biome <= 4) 
				{
					biome = 7;
				}*/
				part.color = Data.groundColorMap.get(biome);
				spawnStuff(chunk, biome, part);
			}
		}
	}

	//Look up spawningservice and calculate what is placed 
	public void spawnStuff(Chunk chunk, int biome, Entity part)
	{
		double[][] resBiomeTable = SpawningService.biomeSpawnChance.get(biome);

		//Iterate through various possibilities.
		for (int i = 0; i < resBiomeTable.length; i++)
		{
			if (resBiomeTable[i][0] == 0)
			{

			}
			else if (resBiomeTable[i][0] % 1 == 0)
			{
				double rand = Math.random();
				if (rand < resBiomeTable[i][3])
				{
					//System.out.println(rand + " is less than " + resBiomeTable[i][3]);
					double minQ = resBiomeTable[i][1]; //min quantity
					double maxQ = resBiomeTable[i][2]; //max quantity
					int quantity = (int)(minQ + Math.random()*(maxQ - minQ + 1)); //determine randomly how many are placed
					//place that quantity
					for (int j = 0; j < quantity; j++)
					{
						//System.out.println("spam");
						Entity spawnThis = new Entity((int)resBiomeTable[i][0]);
						chunk.occupants.add(spawnThis);

						//System.out.println(chunk.row + " " + chunk.col);
						spawnThis.chunkRow = chunk.row;
						spawnThis.chunkCol = chunk.col;

						chunkService.level.add(spawnThis);
						place(spawnThis, part);
					}
				}
			}
			else
			{
				double rand = Math.random();
				if (rand < resBiomeTable[i][3])
				{
					//TODO: Places model. Check if model is intersecting anything. If it is, delete the model and do it again. If not, move on.

					double[][] resModel = SpawningService.spawnModels.get(resBiomeTable[i][0]);

					double minQ = resBiomeTable[i][1]; //min quantity
					double maxQ = resBiomeTable[i][2]; //max quantity
					int quantity = (int)(minQ + Math.random()*(maxQ - minQ + 1)); //determine randomly how many are placed
					for (int k = 0; k < quantity; k++)
					{
						int posX = (int)(part.posX + part.sizeX * (Math.random()-0.5));
						double posY; //= (int)(part.posY + part.sizeY/2);
						int posZ = (int)(part.posZ + part.sizeZ * (Math.random()-0.5));
						for (int j = 0; j < resModel.length; j++)
						{
							Entity spawnThis = new Entity((int)resModel[j][0]);
							posY = (double)(part.posY + part.sizeY/2 + spawnThis.sizeY/2);
							chunk.occupants.add(spawnThis);

							//System.out.println(chunk.row + " " + chunk.col);
							spawnThis.chunkRow = chunk.row;
							spawnThis.chunkCol = chunk.col;

							chunkService.level.add(spawnThis);
							//place(spawnThis, part);
							spawnThis.moveTo(posX, posY, posZ);
							spawnThis.move(resModel[j][1],resModel[j][2],resModel[j][3]);
						}
					}
				}
			}
		}
	}

	/*public void spawnStuff(Chunk chunk, int biome, Entity part)
	{
		double[][] resBiomeTable = SpawningService.biomeSpawnChance.get(biome);

		double bar = 0;
		double random = Math.random();
		//Iterate through various possibilities.
		for (int i = 0; i < resBiomeTable.length; i++)
		{
			bar += resBiomeTable[i][3];
			if (random < bar)
			{
				if (resBiomeTable[i][0] != 0)
				{
					double minQ = resBiomeTable[i][1]; //min quantity
					double maxQ = resBiomeTable[i][2]; //max quantity
					int quantity = (int)(minQ + Math.random()*(maxQ - minQ + 1)); //determine randomly how many are placed
					//place that quantity
					for (int j = 0; j < quantity; j++)
					{
						//System.out.println("spam");
						Entity spawnThis = new Entity((int)resBiomeTable[i][0]);
						chunk.occupants.add(spawnThis);

						//System.out.println(chunk.row + " " + chunk.col);
						spawnThis.chunkRow = chunk.row;
						spawnThis.chunkCol = chunk.col;

						chunkService.level.add(spawnThis);
						place(spawnThis, part);
					}
				}
				return;
			}
			else
			{
				//do nothing.
			}
		}
	}*/

	public void place(Entity placing, Entity on)
	{
		placing.moveTo(
				(int)(on.posX + on.sizeX * (Math.random()-0.5)),
				(double)(on.posY + on.sizeY/2D + placing.sizeY/2D),
				(int)(on.posZ + on.sizeZ * (Math.random()-0.5))
				);
	}

	//Creates a map based on terrain
	public int addTerrain(Level level, double[][] terra, double[][] temperature, double[][] rain)
	{
		int side = 50; int cutoff = returnOceanLevel(terra,0.85);
		//System.out.println(cutoff);
		//int cutoff = 100;
		for (int i = 0; i < terra.length; i++)
		{
			for (int j = 0; j < terra[0].length; j++)
			{
				if (terra[i][j] > cutoff)
				{
					int multiplier = 2;
					Entity en = new Entity(-1,side,(terra[i][j] - cutoff)*multiplier,side,37,"Levelpart",0,0,true);
					en.moveTo(i*side,(terra[i][j]/2 - cutoff)*multiplier,j*side);
					en.locked = true;
					level.add(en);
				}
			}
		}
		return cutoff; //Don't have to calculate it twice
		//Add the ocean.
		//Entity ocean = new Entity(-1,side*terra.length,2,side*terra.length,102,"Levelpart",0,0);
		//ocean.moveTo(side*terra.length/4, -cutoff, side*terra.length/4);
		//level.add(ocean);
		//\\Ok, don't add the ocean...
	}

	//Returns an ocean level based on terrain. Raises ocean level until percent of the world is below sea level.
	public int returnOceanLevel(double[][] terra, double submerged)
	{
		int cutoff = 50;
		while (true)
		{
			cutoff += 5;
			double land = 0;
			double sea = 0;
			for (int i = 0; i < terra.length; i++)
			{
				for (int j = 0; j < terra[0].length; j++)
				{
					if (terra[i][j] > cutoff) land++;
					else sea++;
				}
			}
			//System.out.println(cutoff + " " + land + " " + sea);
			if (sea/(land+sea) > submerged) break;
		}
		return cutoff;
	}

	//Returns an interpolated terrain
	public double[][] assignNewTerrain(double nDiv)
	{
		//(int)(Math.log(rows)/Math.log(2))-1
		//seed = (long)(Math.random()*1000000L);
		double[][] source = new PerlinNoise(seed).makePerlinNoise((int)nDiv,(int)nDiv,128,16,3,0.5,4);
		double[][] newSource = PerlinNoise.recurInter(source,2,nDiv/4);
		//double[][] newSource = PerlinNoise.expand(PerlinNoise.expand(source,nDiv/2),nDiv);
		//System.out.println(newSource.length);
		return newSource;
	}

	//Returns an interpolated map which gives each chunk a temperature, 0 to 4 (arctic to tropical)
	public double[][] assignTemperature(double nDiv)
	{
		//int chunkLength = rows; //chunkService.returnChunkLength();
		double[][] oldSource = new PerlinNoise(seed).makePerlinNoise((int)nDiv,(int)nDiv,3,8,3,0.5,2);
		return PerlinNoise.recurInter(oldSource,2,nDiv/4);
	}

	//Returns an interpolated map which gives each chunk a level of rain, based on temperature
	//Arctic climates do not have rain, tropical climates can have any level
	public double[][] assignRain(double[][] temperature)
	{
		double[][] returnThis = new double[temperature.length][temperature[0].length];
		for (int i = 0; i < temperature.length; i++)
		{
			for (int j = 0; j < temperature[0].length; j++)
			{
				returnThis[i][j] = Math.random()*temperature[i][j] + Math.random();
			}
		}
		returnThis = PerlinNoise.recurInter(returnThis,1,returnThis.length/2);
		return returnThis;
	}

	public int[][] assignBiomes(double[][] t, double[][] r)
	{
		int[][] returnThis = new int[t.length][t[0].length];
		for (int i = 0; i < t.length; i++)
		{
			for (int j = 0; j < t[0].length; j++)
			{
				returnThis[i][j] = returnBiome(t[i][j], r[i][j]);
			}
		}
		biomes = returnThis;
		return returnThis;
	}

	//Helper class. Returns arraylist of islands. An island is an arraylist of locations.
	public class IslandHelper
	{
		//Wrapper class
		public class Location {public int r; public int c; public Location(int x, int y) {r = x; c = y;}};

		//Tiles that have been assigned islands
		public boolean[][] accounted;

		//An arraylist containing lists of islands (an island is a list, so this is a list of all those lists)
		public ArrayList<ArrayList<Location>> listIslands = new ArrayList<ArrayList<Location>>();

		public IslandHelper(double[][] terrain, double cutoff)
		{
			accounted = new boolean[terrain.length][terrain[0].length];
			getListIslands(terrain, cutoff);
			for (int i = 0; i < listIslands.size(); i++)
			{
				ArrayList<Location> island = listIslands.get(i);
				System.out.println("Island " + i);
				for (int j = 0; j < island.size(); j++)
				{
					System.out.println(">>>>>" + island.get(j).r + "," + island.get(j).c);
				}
			}
		}

		public void getListIslands(double[][] terrain, double cutoff)
		{
			for (int r = 0; r < terrain.length; r++)
			{
				for (int c = 0; c < terrain[0].length; c++)
				{
					//Do not look at islands below cutoff e.g. sea
					accounted[r][c] = terrain[r][c] < cutoff;
					//System.out.println(accounted[r][c]);
				}
			}
			for (int r = 0; r < terrain.length; r++)
			{
				for (int c = 0; c < terrain[0].length; c++)
				{
					if (!accounted[r][c])
					{
						loc = new ArrayList<Location>(); //Reset it.
						startIsland(r,c); //Change it.
						if (loc.size() > 0)
							listIslands.add(loc); //Record it.
					}
				}
			}
		}

		//Recursive method that returns a list for one island containing r,c
		ArrayList<Location> loc; // = new ArrayList<Location>();
		//A temporary global. Lazy fix.
		public void startIsland(int r, int c)
		{
			if (r <= 0 || c <= 0 || r >= accounted.length || c >= accounted[0].length) return;
			if (!accounted[r][c])
			{
				accounted[r][c] = true;
				loc.add(new Location(r,c));
				//if (r > 0 && c > 0 && r < accounted.length - 1 && c < accounted[0].length - 1)
				{
					startIsland(r+1,c);
					startIsland(r-1,c);
					startIsland(r,c+1);
					startIsland(r,c-1);
				}
			}
			else
			{
				return;
				//return new ArrayList<Location>(); //add nothing
			}
		}

	}

	//returns the biome based on temperature, t, and rain, r
	public int returnBiome(double t, double r)
	{
		if (t > 3)
		{
			if (r > 3)
				return 6;
			else if (r > 2)
				return 5;
			else if (r > 1)
				return 4;
			else if (r > 0.5)
				return 3;
			else 
				return 2;
		}
		else if (t > 2)
		{
			if (r > 2)
				return 5;
			else if (r > 1)
				return 4;
			else if (r > 0.5)
				return 3;
			else 
				return 2;
		}
		else if (t > 1)
		{
			if (r > 1)
				return 4;
			else if (r > 0.5)
				return 3;
			else 
				return 2;
		}
		else
		{
			if (r > 0.5)
				return 1;
			else 
			{
				//System.out.println(t + " " + r);
				return 0;
			}
		}
	}

}
