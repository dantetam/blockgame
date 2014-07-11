package info.dei.system;

import java.awt.Robot;
import java.util.ArrayList;

import info.dei.entity.Entity;
import info.dei.entity.Player;
import info.dei.render.Runner;
import info.dei.service.ChunkService.Chunk;
import info.dei.vector.Plane;

public class InputSystem extends BaseSystem {

	public ArrayList<Character> keyQueue;
	//public 
	//public double lastMouseX = -1, lastMouseY = -1;
	public Player d = p.player;
	public Robot robot;

	public InputSystem(Runner p) {
		super(p);
		try {robot = new Robot();} catch (Exception e) {};
		d.posX = 500; d.posY = 10; d.posZ = 300;
		d.tarX = 200; d.tarY = 10; d.tarZ = 0;
		keyQueue = new ArrayList<Character>();
	}

	public double lastARotY = 0;
	public void tick()
	{
		//updateMove();
		keyQueue.clear();

		double dist = 1; if (p.flying) dist = 14;
		if (wPressed)
		{
			d.move(dist*(float)Math.cos(aRotY), 0, dist*(float)Math.sin(aRotY));
			if (p.flying)
			{
				d.move(0, dist*(float)Math.sin(aRotX), 0);
			}
		}
		if (sPressed)
		{
			d.move(-dist*(float)Math.cos(aRotY), 0, -dist*(float)Math.sin(aRotY));
			if (p.flying)
			{
				d.move(0, -dist*(float)Math.sin(aRotX), 0);
			}
		}
		if (aPressed)
		{
			d.move(-dist*(float)Math.cos(aRotY-Math.PI/2), 0, -dist*(float)Math.sin(aRotY-Math.PI/2));
			if (p.flying)
			{
				//d.posY -= dist*(float)Math.sin(aRotX+Math.PI/2);
			}
		}
		if (dPressed)
		{
			d.move(-dist*(float)Math.cos(aRotY+Math.PI/2), 0, -dist*(float)Math.sin(aRotY+Math.PI/2));
			if (p.flying)
			{
				//d.posY -= dist*(float)Math.sin(aRotX-Math.PI/2);
			}
		}
		//Chunk chunk = p.chunkSystem.findPlayerChunk(p.player);
		//ArrayList<Chunk> chunks = p.chunkSystem.findChunksWithin(chunk.row, chunk.col, 2);

		calPlayerView(p.mouseX, p.mouseY);

		
	}

	public void forwardKey(char key)
	{
		if (key >= 48 && key <= 122)
		{
			keyQueue.add(key);
		}
	}

	public void forwardMouse(double mouseX, double mouseY)
	{
		Entity enInteract = p.renderSystem.calMouseMap();
		//System.out.println(enInteract);
		if (enInteract != null)
		{
			if (p.player.distTo(enInteract) <= 150)
			{
				//System.out.println("lalala");
				if (p.menuSystem.mode == 0 && !enInteract.locked) //picking up item
				{
					if (enInteract.harvestTool == 0)
					{
						p.player.giveItem(enInteract.toItem());
						//p.println(p.d.inventory.size());
						/*for (int i = 0; i < p.player.inventory.size(); i++)
						{
							p.println(p.player.inventory.get(i));
						}*/
						enInteract.removeFlag = true;
					}
				}
				else if (p.menuSystem.mode == 9999 && enInteract.typeId >= 22 && enInteract.typeId <= 27)
				{
					p.player.hunger += 10;
					enInteract.removeFlag = true;
				}
				else if (p.menuSystem.mode == 1 && !enInteract.locked) //using tool
				{
					int harvestMode = 0;
					//p.println(p.d.inventory.get(p.mSystem.selectingInvIndex).name);
					if (p.menuSystem.selectingInvIndex < p.player.inventory.size())
					{
						if (p.player.inventory.get(p.menuSystem.selectingInvIndex).name.contains("Axe"))
						{
							//System.out.println("lalala");
							harvestMode = 1;
						}
						else if (p.player.inventory.get(p.menuSystem.selectingInvIndex).name.contains("Pickaxe"))
						{
							harvestMode = 2;
						}
						else if (p.player.inventory.get(p.menuSystem.selectingInvIndex).name.contains("Sword"))
						{
							harvestMode = 3;
							//p.println("dies irae");
						}
						else
						{
							p.menuSystem.passToolUse(enInteract);
							return;
						}

						System.out.println(enInteract.harvestTool);
						if (enInteract.harvestTool == harvestMode || enInteract.harvestTool == 0)
						{
							p.player.giveItem(enInteract.toItem());
							for (int i = 0; i < p.player.inventory.size(); i++)
							{
								p.println(p.player.inventory.get(i));
							}
							enInteract.removeFlag = true;
						}
					}

				}
			}
		}
	}

	public void forwardRightMouse(double mouseX, double mouseY)
	{
		Entity enInteract = p.renderSystem.calMouseMap();
		if (enInteract != null)
		{
			if (p.menuSystem.mode == 1)
			{
				p.menuSystem.forwardPlace(enInteract);
			}
			else if (p.menuSystem.mode == 4)
			{
				//System.out.println(enInteract);
				p.menuSystem.forwardMove(enInteract);
				//System.out.println("dies irae");
			}
		}
	}

	boolean wPressed, sPressed, aPressed, dPressed = false;
	public void forwardCommand(String command)
	{
		if (command.equals("wPressed"))
		{
			wPressed = true;
		}
		else if (command.equals("sPressed"))
		{
			sPressed = true;
		}
		else if (command.equals("aPressed"))
		{
			aPressed = true;
		}
		else if (command.equals("dPressed"))
		{
			dPressed = true;
		}

		else if (command.equals("wReleased"))
		{
			wPressed = false;
		}
		else if (command.equals("sReleased"))
		{
			sPressed = false;
		}
		else if (command.equals("aReleased"))
		{
			aPressed = false;
		}
		else if (command.equals("dReleased"))
		{
			dPressed = false;
		}
	}

	public float aRotY; //angle of rotation on the y axis
	public float aRotX;
	public float sight = 10;
	public float lastMouseX = p.width/2;
	public float lastMouseY = p.height/2;

	public void calPlayerView(double mouseX, double mouseY)
	{
		//aRotY %= 360; aRotX %= 360;

		double difX = mouseX - lastMouseX;
		double difY = mouseY - lastMouseY; //do nothing for now
		lastMouseX = (float)mouseX;
		lastMouseY = (float)mouseY;
		//robot.mouseMove(p.width/2, p.height/2);

		//robot.mouseMove((int)(mouseX + p.width/2D),(int)(mouseY + p.height/2D));

		aRotY -= difX/p.width*4*Math.PI;
		aRotX += difY/p.height*2*Math.PI;
		d.tarX = d.posX + sight*(float)Math.cos(aRotY);
		d.tarY = d.posY + sight*(float)Math.sin(aRotX);
		d.tarZ = d.posZ + sight*(float)Math.sin(aRotY); //- (float)(sight*Math.sin(aRotX)*Math.cos(Math.PI/2-aRotX/2));
	}

}
