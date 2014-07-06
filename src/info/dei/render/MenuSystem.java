package info.dei.render;

import java.util.ArrayList;

import processing.core.PApplet;
import info.dei.entity.Entity;
import info.dei.entity.Item;
import info.dei.entity.Person;
import info.dei.entity.Player;
import info.dei.service.CraftingService;
import info.dei.service.Data;
import info.dei.vector.Plane;
import info.dei.vector.Point;

//2D GUI which is overlayed over the 3D display.

public class MenuSystem extends PApplet {

	public Runner p;
	public Player player;
	public int mode = 0; //mode corresponds to place in p.boxes

	public int selectingInvIndex = 0;

	public MenuSystem(Runner p)
	{
		this.p = p;
		player = p.player;
	}

	public void setup()
	{
		noLoop();
		loadFont("/misc/Consolas.vlw");
		textSize(20);

		addBox("Gather",p.width-500,p.height-100,75,75);
		addBox("Inventory",p.width-400,p.height-100,75,75);
		//addBox("Craft",p.width-300,p.height-100,75,75);
		addBox("Move",p.width-200,p.height-100,75,75);
		addBox("Command",p.width-100,p.height-100,75,75);
		//addBox("Gather",p.width-100,p.height-100,75,75);
	}

	public void addBox(String text, double a, double b, double c, double d)
	{
		InterfaceBox box = new InterfaceBox(this.p,text,a,b,c,d);
		p.add(box);
		p.boxes.add(box);
		box.init();
		box.setBounds((int)box.posX,(int)box.posY,(int)box.sizeX,(int)box.sizeY);
	}

	public boolean[] selected = new boolean[50];
	public void draw()
	{
		//background(0);
		//text("YOLO",200,200);
		//background(0,0,0,100);
		//rect(0,0,500,500);
		for (int i = 0; i < p.boxes.size(); i++)
		{
			p.boxes.get(i).redraw();
		}
		p.inventoryMenu.setVisible(false);
		p.commandMenu.setVisible(false);
		if (mode == 1)
		{
			p.inventoryMenu.setVisible(true);
			//p.inventoryMenu.redraw();
			redrawInventory();
		}
		else if (mode == 3)
		{
			p.commandMenu.setVisible(true);
			//p.inventoryMenu.redraw();
			redrawCommand();
		}
	}

	public void redrawInventory()
	{
		p.inventoryMenu.background(0);
		for (int i = 0; i < player.inventory.size(); i++)
		{
			p.inventoryMenu.fill(255);
			if (selected[i]) p.inventoryMenu.fill(255,0,0); 
			p.inventoryMenu.text(player.inventory.get(i).name,50,i*15 + 50);
			if (i == selectingInvIndex)
			{
				p.inventoryMenu.stroke(255);
				p.inventoryMenu.line(50,i*15 + 50,player.inventory.get(i).name.length()*10 + 50,i*15 + 50);
			}
		}
	}

	public void redrawCommand()
	{
		/*
		p.commandMenu.background(0);
		int entries = 0;
		for (int i = 0; i < p.personSystem.people.size(); i++)
		{
			Person person = p.personSystem.people.get(i);
			if (person.allegiance.equals(player))
			{
				p.commandMenu.fill(255);
				p.commandMenu.text(person.name,50,entries*25 + 50);
				entries++;
			}
		}
		*/
	}

	/*public void forwardPlace(Entity entityUnder)
	{
		if (selectingInvIndex < p.player.inventory.size())
		{
			Entity en = new Entity(p.player.inventory.get(selectingInvIndex).typeId);
			en.moveTo(entityUnder.posX,entityUnder.posY+entityUnder.sizeY/2+en.sizeY/2,entityUnder.posZ);
			p.level.entities.add(en);
			p.player.inventory.get(selectingInvIndex).removeFlag = true;
			for (int i = 0; i < selected.length; i++)
			{
				selected[i] = false;
			}	
			selectingInvIndex = 0;
		}
	}*/

	public void forwardPlace(Entity on)
	{
		if (selectingInvIndex >= p.player.inventory.size()) return;
		
		//Find entity selected in inventory and put it into the world.
		Entity en = new Entity(p.player.inventory.get(selectingInvIndex).typeId);
		//en.moveTo(entityUnder.posX,entityUnder.posY+entityUnder.sizeY/2+en.sizeY/2,entityUnder.posZ);
		p.level.entities.add(en);

		Plane[] planes = p.player.lookingAtEntity(on);
		//p.menuSystem.forwardPlace(enInteract);
		for (int i = 0; i < planes.length; i++)
		{
			if (planes[i] != null)
			{
				//Find where the plane and the look vector of the player intersect.
				Point intersect = planes[i].intersect(p.player.getLookVector());
				
				//System.out.println((int)intersect.x + " " + (int)intersect.y + " " + (int)intersect.z);
				//System.out.println((int)p.player.posX + " " + (int)p.player.posY + " " + (int)p.player.posZ);
				
				//Move to that point and adjust.
			    en.moveTo((int)intersect.x, (int)on.posY, (int)intersect.z); //+ on.sizeY/2 + en.sizeY/2
			    p.chunkSystem.assignEntity(en);
			    
			    if (i == 0) en.move(en.sizeX/2,0,0);
			    else if (i == 1) en.move(-en.sizeX/2,0,0);
			    else if (i == 2) en.move(0,en.sizeY/2,0);
			    //else if (i == 3) en.move(0,-en.sizeY/2,0);
			    else if (i == 4) en.move(0,0,en.sizeZ/2);
			    else if (i == 5) en.move(0,0,-en.sizeZ/2);
			   
				break;
			}
		}

		//Remove item from inventory.
		p.player.inventory.get(selectingInvIndex).removeFlag = true;
		for (int i = 0; i < selected.length; i++)
		{
			selected[i] = false;
		}	
		selectingInvIndex = 0;
	}

	public Entity selectedEn = null;
	public void forwardMove(Entity moving)
	{
		if (selectedEn != null) selectedEn.selected = false;
		selectedEn = moving;
		moving.selected = true;
		//p.println(moving.selected);
	}

	public void forwardMouse(double mouseX, double mouseY)
	{

	}

	public void forwardKey(char key)
	{
		if (Character.isDigit(key))
		{
			int temp = Integer.parseInt(Character.toString(key));
			if (temp > 0)
				mode = temp - 1;
			else
				mode = temp;

			//make selected "tool" a different color
			for (int i = 0; i < p.boxes.size(); i++)
			{
				p.boxes.get(i).active = false;
			}
			if (mode <= p.boxes.size() - 1)
			{
				p.boxes.get(mode).active = true;
			}
		}
		else if (key == 'q')
		{
			selectingInvIndex++;
			if (selectingInvIndex >= player.inventory.size()) selectingInvIndex = 0;
		}
		else if (key == 'e')
		{
			selected[selectingInvIndex] = !selected[selectingInvIndex];
		}
		else if (key == 'c')
		{
			ArrayList<Integer> attemptToCraft = new ArrayList<Integer>();
			for (int i = 0; i < selected.length; i++)
			{
				if (selected[i])
				{
					attemptToCraft.add(player.inventory.get(i).typeId);
				}
			}
			Item item = CraftingService.craft(attemptToCraft);
			if (item != null)
			{
				if (item.typeId > 0)
				{
					player.giveItem(item);
					for (int i = 0; i < selected.length; i++)
					{
						if (selected[i])
						{
							player.inventory.get(i).removeFlag = true;
						}
					}
				}
				else //processes like compost for example
				{
					Entity en = item.toEntity();
					p.level.add(en);
					en.moveTo(player.posX, player.posY - 6 + en.sizeY/2, player.posZ); //3 is half of player size, i think
					en.setTicker(Data.ticksToProcess.get(en.typeId));
				}
				for (int i = 0; i < selected.length; i++)
				{
					if (selected[i])
					{
						player.inventory.get(i).removeFlag = true;
					}
				}
			}
			for (int i = 0; i < selected.length; i++)
			{
				selected[i] = false;
			}
		}
	}

	public void dropSelected()
	{
		if (mode == 2)
		{
			Entity en = new Entity(p.player.inventory.get(selectingInvIndex).typeId);
			en.moveTo((int)player.posX,(int)(player.posY-7+en.sizeY/2),(int)player.posZ);
			p.level.entities.add(en);
			p.player.inventory.get(selectingInvIndex).removeFlag = true;
			for (int i = 0; i < selected.length; i++)
			{
				selected[i] = false;
			}	
			selectingInvIndex = 0;
		}
	}

	public void passToolUse(Entity en)
	{
		if (p.player.inventory.size() > selectingInvIndex)
		{
			if (p.player.inventory.get(selectingInvIndex).name.equals("Flint"))
			{
				//System.out.println(en.name + "," + en.typeId);
				if (en.name.contains("Branch") || en.name.contains("Leaves") || en.name.contains("Log") || en.name.contains("Bark") || en.name.contains("Wood"))
				{
					en.burning = (int)(en.sizeX*en.sizeY*en.sizeZ/30);
					p.burningSystem.burningThings.add(en);
				}
			}
		}
	}

}
