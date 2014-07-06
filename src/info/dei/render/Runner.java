package info.dei.render;

import processing.core.*;

import java.util.ArrayList;

import info.dei.entity.Entity;
import info.dei.entity.Item;
import info.dei.entity.Player;
import info.dei.level.Level;
import info.dei.service.*;
import info.dei.system.*;
import info.dei.terrain.PerlinLoader;

public class Runner extends PApplet {

	public ArrayList<BaseSystem> systems = new ArrayList<BaseSystem>();
	//public static int width = 1900;
	//public static int height = 1300;

	public Level level = new Level();

	public Player player = new Player(this);

	//systems that tick every frame, ref: draw()
	public TrashSystem trashSystem = new TrashSystem(this);
	public TickSystem tickSystem = new TickSystem(this);
	public InputSystem inputSystem = new InputSystem(this);
	public MenuSystem menuSystem = new MenuSystem(this);
	public RenderSystem renderSystem = new RenderSystem(this);
	public PhysicsSystem physicsSystem = new PhysicsSystem(this);

	//delayed services, controlled by tick service
	public BurningSystem burningSystem = new BurningSystem(this);
	public ProcessSystem processSystem = new ProcessSystem(this);
	public AnimalSystem chickenSystem = new AnimalSystem(this);
	public PersonSystem personSystem = new PersonSystem(this);

	public ChunkService chunkSystem;
	
	public Cursor cursor = new Cursor();
	public ArrayList<InterfaceBox> boxes = new ArrayList<InterfaceBox>();
	public InventoryMenu inventoryMenu = new InventoryMenu(this);
	public CommandMenu commandMenu = new CommandMenu();
	
	public boolean flying = true;
	
	public static void main(String args[])
	{
		//Launch a new window.
		PApplet.main(new String[] { info.dei.render.Runner.class.getName() });
	}
	
	public PerlinLoader perlinLoader;
	
	public void setup()
	{
		//noStroke();
		size(1900, 1000, P3D);
		smooth(2);
		frameRate(40);
		noCursor();
		//shader(null);
		//strokeWeight(2); 

		Data.init();
		SpawningService.init();
		CraftingService.setupRecipes();
		
		systems.add(trashSystem);
		systems.add(chickenSystem); 
		systems.add(tickSystem);
		systems.add(renderSystem);
		systems.add(physicsSystem);
		systems.add(inputSystem);
		systems.add(personSystem);
		
		this.add(menuSystem);
		this.setBounds(0,0,width,height);
		
		menuSystem.init();
		menuSystem.setBounds(0,0,width,height);
		menuSystem.setVisible(false);
		
		this.add(inventoryMenu);
		inventoryMenu.init();
		inventoryMenu.setBounds(width-500,100,475,400);
		inventoryMenu.setVisible(false);
		
		this.add(commandMenu);
		commandMenu.init();
		commandMenu.setBounds(width-500,100,475,400);
		commandMenu.setVisible(false);
		
		this.add(cursor);
		cursor.init();
		cursor.setBounds(width/2 - 2, height/2 - 2, 5, 5);
		
		perlinLoader = new PerlinLoader(level,128,128,128);
		chunkSystem = new ChunkService(level);
		//chunkSystem.adjustChunks();
		perlinLoader.chunkService = chunkSystem;
		perlinLoader.assignStuff();
		
		//chunkSystem = new ChunkService(level); //add goodies to chunks
		
		//player.giveItem(new Item(103));
		
		inputSystem.calPlayerView(-1,-1);
		personSystem.init(player);
	}

	public boolean cinematic = false;
	
	public void draw()
	{
		ambientLight(200, 200, 200);
		pointLight(200, 200, 200, 0, 10000, 0);
		lightFalloff(1, 0, 0);
		lightSpecular(0, 0, 0);
		//directionalLight(204, 204, 204, 0, -1, 0);
		for (int i = 0; i < systems.size(); i++)
		{
			systems.get(i).tick();
		}
		//if (!cinematic)
		menuSystem.redraw();
		cursor.redraw();
		//if (Math.random() < 0.03) System.out.println(player.posZ);
		//background((int) (Math.random()*255));
	}

	public void mousePressed()
	{
		if (mouseButton == LEFT)
		{
			inputSystem.forwardMouse(mouseX, mouseY);
			menuSystem.forwardMouse(mouseX, mouseY);
			menuSystem.redraw();
		}
		else if (mouseButton == RIGHT)
		{
			inputSystem.forwardRightMouse(mouseX, mouseY);
		}
		else
		{
			//menuSystem.dropSelected();
		}
	}

	public boolean menuVisible = false;
	public void keyPressed()
	{
		if (Character.isUpperCase(key))
			key = Character.toLowerCase(key);
		if (key == '[')
		{
			cinematic = !cinematic;
			if (cinematic)
				frameRate(15);
			else
				frameRate(30);
		}
		if (key == 'f')
		{
			flying = !flying;
			if (!flying)
			{
				//player.posY = 10;
				//player.tarY = 10;
			}
		}
		if (key == 'o')
		{
			renderSystem.cutoff1++;
			renderSystem.cutoff2++;
		}
		else if (key == 'l')
		{
			renderSystem.cutoff1--;
			renderSystem.cutoff2--;
		}
		/*if (key == 'i')
		{
			menuVisible = !menuVisible;
			menuSystem.setVisible(menuVisible);
			renderSystem.tick();
		}*/
		inputSystem.forwardKey(key);
		menuSystem.forwardKey(key);
		if (key == 'w')
		{
			inputSystem.forwardCommand("wPressed");
		}
		else if (key == 's')
		{
			inputSystem.forwardCommand("sPressed");
		}
		else if (key == 'a')
		{
			inputSystem.forwardCommand("aPressed");
		}
		else if (key == 'd')
		{
			inputSystem.forwardCommand("dPressed");
		}
	}

	public void keyReleased()
	{
		if (key == 'w')
		{
			inputSystem.forwardCommand("wReleased");
		}
		else if (key == 's')
		{
			inputSystem.forwardCommand("sReleased");
		}
		else if (key == 'a')
		{
			inputSystem.forwardCommand("aReleased");
		}
		else if (key == 'd')
		{
			inputSystem.forwardCommand("dReleased");
		}
	}

}
