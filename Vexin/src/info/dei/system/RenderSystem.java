package info.dei.system;

import info.dei.entity.Entity;
import info.dei.entity.Player;
import info.dei.level.Level;
import info.dei.render.Runner;
import info.dei.service.ChunkService.Chunk;
import info.dei.service.Color;
import info.dei.service.Data;
import info.dei.vector.Plane;

import java.util.ArrayList;

//Controls all rendering.

public class RenderSystem extends BaseSystem {

	public Level level;
	public Player d = p.player;
	public double cutoff1 = 2; //within this distance all parts will be shown
	public double cutoff2 = 8; //only level parts

	//public MouseMap mouseMap = new MouseMap(this);

	public RenderSystem(Runner p) {
		super(p);
		level = p.level;
	}

	public Entity calMouseMap()
	{
		Chunk chunk = p.chunkSystem.findPlayerChunk(p.player);
		ArrayList<Chunk> chunks = p.chunkSystem.findChunksWithin(chunk.row, chunk.col, 2);

		for (int j = 0; j < chunks.size(); j++)
		{
			//System.out.println(chunk.occupants.size());
			for (int i = 0; i < chunks.get(j).occupants.size(); i++)
			{
				Entity en = chunks.get(j).occupants.get(i);
				if (!en.locked)
				{
					if (checkOneValid(d.lookingAtEntity(en)))
					{
						return en;
					}
				}
			}
		}
		return null;
	}
	
	private boolean checkOneValid(Plane[] planes)
	{
		for (int i = 0; i < planes.length; i++)
		{
			if (planes[i] != null) return true;
		}
		return false;
	}

	public void tick()
	{
		p.background(135, 206, 235);
		p.pushMatrix();
		p.camera(d.posX, d.posY, d.posZ, d.tarX, d.tarY, d.tarZ, 0, -1, 0);
		p.perspective((float)(3.14/2F), 1.9F, 1F, 10000F);
		//p.frustum(-100, 0, 0, 100, 0, 200);
		float pChunkRow = (float)p.chunkSystem.findPlayerChunk(p.player).row;
		float pChunkCol = (float)p.chunkSystem.findPlayerChunk(p.player).col;
		
		//TODO: Uncomment and work on.
		/*for (int i = 0; i < p.personSystem.people.size(); i++)
		{
			Person person = p.personSystem.people.get(i);
			p.fill(0,255,255);
			p.pushMatrix();
			p.translate((float)person.posX,(float)person.posY,(float)person.posZ);
			//System.out.println(person.posX + " " + person.posY + " " + person.posZ);
			p.box(5,10,5);
			p.popMatrix();
		}*/
		for (int i = 0; i < level.entities.size(); i++)
		{
			//p.strokeWeight(2); 
			Entity en = level.entities.get(i);
			//if (en.name.equals("Levelpart")) p.noStroke();
			//if (en.selected) p.stroke(0,0,255); 
			//double dist = Math.sqrt(Math.pow(pChunkRow - en.chunkRow, 2) + Math.pow(pChunkCol - en.chunkCol, 2));
			double dist = p.dist((float)pChunkRow,(float)pChunkCol,en.chunkRow,en.chunkCol);
			//System.out.println(dist);
			//if (Math.random() < 0.01) System.out.println(pChunkRow + " " + pChunkCol + " " + en.chunkRow + " " + en.chunkCol);

			if (dist > cutoff2) continue;
			
			if (dist < cutoff1)
			{
				p.stroke(50);
			}
			else
			{
				p.noStroke();
			}
			
			if (dist < cutoff1 || en.name.equals("Levelpart"))
			{
				if (en.typeId < 9000)
				{
					p.pushMatrix();
					//System.out.println(dist);

					Color c = Data.brickColorMap.get(en.color);

					if (en.burning > 0)
					{
						p.fill(200,0,0,0);
					}
					else
					{
						p.fill((float)c.r,(float)c.g,(float)c.b,(1-(float)en.transparency)*255);
					}

					p.translate((float)en.posX, (float)en.posY, (float)en.posZ);
					p.box((float)en.sizeX, (float)en.sizeY, (float)en.sizeZ);
					//p.fill(255,0,255);
					//p.box(1,1,1);

					p.popMatrix();
				}
				else if (en.typeId > 9000) //over 9000 is implied...or not
				{
					String[] stringy = p.loadStrings("/models/" + en.name.toLowerCase() + ".txt");
					for (int j = 1; j < stringy.length; j++)
					{
						p.pushMatrix();
						String[] eString = p.split(stringy[j],",");
						float[] ed = new float[eString.length];
						for (int k = 0; k < ed.length; k++)
						{
							ed[k] = Float.parseFloat(eString[k]);
						}

						Color c = Data.brickColorMap.get((int)ed[9]);
						p.fill((float)c.r, (float)c.g, (float)c.b);

						float x = (float)en.posX + ed[0];
						float y = (float)en.posY + ed[1];
						float z = (float)en.posZ + ed[2];
						p.translate(x,y,z);
						//p.println(x + "," + y + "," + z);
						p.rotateX(ed[3]);
						p.rotateY(ed[4]);
						p.rotateZ(ed[5]);
						p.box(ed[6],ed[7],ed[8]);
						//en.locked = true;
						//level.add(en);
						p.popMatrix();
					}
				}
			}
		}

		//show tool now
		//i give up
		/*if (p.mSystem.selectingInvIndex < d.inventory.size() && p.mSystem.selectingInvIndex >= 0)
		{
			Entity tool = d.inventory.get(p.mSystem.selectingInvIndex);
			if (tool.physId == 1 || tool.name.equals("Flint"))
			{
				//System.out.println("ans");
				p.pushMatrix();
				Color c = Data.brickColorMap.get(tool.color);

				p.translate(d.posX - d.tarX/10,d.posY,d.posZ - d.tarZ/10);
				p.box((float)tool.sizeX, (float)tool.sizeY, (float)tool.sizeZ);
				p.popMatrix();
			}
		}*/

		p.popMatrix();
	}

}
