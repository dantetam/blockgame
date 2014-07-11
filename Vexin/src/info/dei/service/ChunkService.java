package info.dei.service;

import java.util.ArrayList;

import info.dei.entity.Entity;
import info.dei.entity.Player;
import info.dei.level.Level;

public class ChunkService {

	public ArrayList<Chunk> chunks; //list of all chunks
	public Level level; //the level being divided into chunks
	public int chunkSize = 256; //in "studs", or pixels, determines how many chunks the world is divided into
	//public double pivotX, pivotZ; //location of the first chunk for reference
	public int spawnChunkRow; public int spawnChunkCol;
	
	//public int chunkErrorRow; public int chunkErrorCol;

	public ChunkService(Level level)
	{
		chunks = new ArrayList<Chunk>();
		this.level = level;
		int[] temp = setupChunks();
		assignChunks(temp);

		/*for (int i = 0; i < chunks.size(); i++)
		{
			Chunk chunk = chunks.get(i);
			System.out.println("Chunk " + chunk.row + " " + chunk.col);
			for (int j = 0; j < chunk.occupants.size(); j++)
			{
				System.out.println("     " + chunk.occupants.get(j).posX + " " + chunk.occupants.get(j).posZ);
			}
		}
		System.out.println(evalMinMax()[0] + "," + evalMinMax()[1] + "," + evalMinMax()[2] + "," + evalMinMax()[3]);*/
	}

	public class Chunk
	{
		public ArrayList<Entity> occupants;
		public int row, col;
		public int biome;
		
		public Chunk(int row, int col)
		{
			this.occupants = new ArrayList<Entity>();
			this.row = row;
			this.col = col;
		}
		
		public int dist(Chunk other)
		{
			return (int) Math.pow( (other.row - row)^2 + (other.col - col)^2, 0.5 );
		}
		
		public void clear()
		{
			for (int i = 0; i < occupants.size(); i++)
			{
				Entity en = occupants.get(i);
				en.chunkRow = 1000;
				en.chunkCol = 1000;
				en.removeFlag = true;
			}
		}
	}
	
	//Fix negative chunks.
	/*public void adjustChunks()
	{
		//Find lowest chunk.
		int minRow = 0; int minCol = 0;
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).row < minRow)
				minRow = chunks.get(i).row;
			if (chunks.get(i).col < minCol)
				minCol = chunks.get(i).col;
		}
		if (minRow >= 0 && minCol >= 0) return;
		
		//System.out.println("MIN " + minRow + " " + minCol);
		//Adjust chunks accordingly.
		for (int i = 0; i < chunks.size(); i++)
		{
			chunks.get(i).row -= minRow;
			chunks.get(i).col -= minCol;
			for (int j = 0; j < chunks.get(i).occupants.size(); j++)
			{
				Entity en = chunks.get(i).occupants.get(j);
				en.chunkRow -= minRow;
				en.chunkCol -= minCol;
			}
		}
		
		chunkErrorRow = minRow;
		chunkErrorCol = minCol;
		
	}*/
	
	public static void printTable(double[][] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			for (int j = 0; j < a[0].length; j++)
			{
				System.out.print((int)a[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public static void printTable(int[][] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			for (int j = 0; j < a[0].length; j++)
			{
				System.out.print(a[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public int returnChunkLength()
	{
		int max = 0;
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).row > max)
			{
				max = chunks.get(i).row;
			}
			if (chunks.get(i).col > max)
			{
				max = chunks.get(i).col;
			}
		}
		return max;
	}

	public Chunk findChunk(int row, int col)
	{
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).row == row && chunks.get(i).col == col)
			{
				return chunks.get(i);
			}
		}
		Chunk temp = new Chunk(row, col);
		chunks.add(temp);
		return temp;
	}
	
	public ArrayList<Chunk> findChunksWithin(int row, int col, int slack)
	{
		ArrayList<Chunk> returnChunks = new ArrayList<Chunk>();
		Chunk firstChunk = findChunk(row,col);
		returnChunks.add(firstChunk);
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).dist(firstChunk) <= slack)
				returnChunks.add(chunks.get(i));
		}
		return returnChunks;
	}

	public int round(double num, int factor)
	{
		int temp = (int)num / factor * factor;
		temp = temp > 0 ? temp : temp - factor;
		return temp;
	}

	//public int[] pivot;
	public Chunk findPlayerChunk(Player player)
	{
		int row = (round(player.posX,chunkSize))/chunkSize;
		int col = (round(player.posZ,chunkSize))/chunkSize;
		return findChunk(row,col);
	}

	public int[] setupChunks()
	{
		double[] temp = evalMinMax();

		int minX = round(temp[0], chunkSize);
		int maxX = round(temp[1], chunkSize);
		int minZ = round(temp[2], chunkSize);
		int maxZ = round(temp[3], chunkSize);

		for (int i = 0; i <= (int)(maxX-minX)/chunkSize; i++)
		{
			for (int j = 0; j <= (int)(maxZ-minZ)/chunkSize; j++)
			{
				chunks.add(new Chunk(i,j));
				//System.out.println(i + " " + j);
			}
		}

		//System.out.println(minX + " " + maxX + " " + minZ + " " + maxZ);
		//pivot = new int[] {minX, minZ};
		return new int[] {minX, minZ};
	}

	public void assignChunks(int[] pivot)
	{
		for (int i = 0; i < chunks.size(); i++)
		{
			chunks.get(i).occupants = new ArrayList<Entity>();
		}
		for (int i = 0; i < level.entities.size(); i++)
		{
			Entity en = level.entities.get(i);
			assignEntity(en);
		}
	}
	
	public void assignEntity(Entity en)
	{
		int row = (round(en.posX,chunkSize))/chunkSize;
		int col = (round(en.posZ,chunkSize))/chunkSize;
		if (en.chunkRow == row && en.chunkCol == col) return;
		Chunk chunk = findChunk(row,col);
		chunk.occupants.add(en);
		en.chunkRow = row; 
		en.chunkCol = col;
	}

	public double[] evalMinMax()
	{
		double[] returnThis = {level.entities.get(0).posX,level.entities.get(0).posX,level.entities.get(0).posZ,level.entities.get(0).posZ}; 
		//minX, maxX, minZ, maxZ

		for (int i = 1; i < level.entities.size(); i++)
		{
			Entity en = level.entities.get(i);
			if (!en.locked)
			{
				if (en.posX < returnThis[0]) returnThis[0] = en.posX; 
				else if (en.posX > returnThis[1]) returnThis[1] = en.posX;
				if (en.posZ < returnThis[2]) returnThis[2] = en.posZ; 
				else if (en.posZ > returnThis[3]) returnThis[3] = en.posZ; 
			}
		}
		return returnThis;
	}

}
