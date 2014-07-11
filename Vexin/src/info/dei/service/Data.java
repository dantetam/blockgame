package info.dei.service;

import java.util.HashMap;

import info.dei.entity.Entity;

public class Data {

	public static HashMap<Integer, Color> brickColorMap; //Defines integer brickcolors e.g. 21, 1003
	public static HashMap<Integer, Entity> entityTypeMap; //Defines sizes and color
	public static HashMap<Integer, Integer> ticksToProcess; //Defines how long it takes for an entity to convert
	public static HashMap<Integer, Integer> rawItems; //?
	
	public static HashMap<Integer, Integer> groundColorMap; //Defines color of ground of certain biomes
	
	
	public Data()
	{
		
	}
	
	public static void init()
	{
		brickColorMap = new HashMap<Integer,Color>();
		entityTypeMap = new HashMap<Integer, Entity>();
		ticksToProcess = new HashMap<Integer, Integer>();
		rawItems = new HashMap<Integer, Integer>();
		groundColorMap = new HashMap<Integer, Integer>();
		
		setupColors();
		setupEntityTypes();
		ticksToProcess();
		rawItems();
		groundColorMap();
	}
	
	public static void ticksToProcess()
	{
		ticksToProcess.put(-1, 6);
		ticksToProcess.put(-2, 3);
	}
	
	public static void rawItems()
	{
		rawItems.put(30,40);
	}
	
	public static void setupEntityTypes()
	{
		entityTypeMap.put(1, new Entity(1,4,4,4,217,"Pile of Dirt",0,0,true));
		entityTypeMap.put(2, new Entity(2,2,2,2,194,"Stone",0,0,true));
		entityTypeMap.put(3, new Entity(3,4,4,4,194,"Boulder",2,0,true));
		entityTypeMap.put(4, new Entity(4,1,4,1,192,"Branch",0,0,false));
		entityTypeMap.put(5, new Entity(5,2,8,2,192,"Large Branch",0,0,true));
		entityTypeMap.put(6, new Entity(6,4,8,4,192,"Log",1,0,true));
		entityTypeMap.put(7, new Entity(7,1,4,4,217,"Bark",1,0,false));
		entityTypeMap.put(8, new Entity(8,1,1,1,199,"Sharp Stone",0,0.2,false));
		entityTypeMap.put(9, new Entity(9,3,3,3,102,"Ice",0,0.5,true));
		
		entityTypeMap.put(20, new Entity(20,4,2,4,37,"Leaves",0,0,false));
		entityTypeMap.put(21, new Entity(21,8,2,8,37,"Large Leaves",0,0,false));
		entityTypeMap.put(22, new Entity(22,1,1,1,21,"Apple",0,0,false));
		//entityTypeMap.put(23, new Entity(23,1,2,1,119,"Pear",0));
		entityTypeMap.put(23, new Entity(23,3,5,3,119,"Cactus",0,0,true));
		entityTypeMap.put(24, new Entity(24,1,3,1,24,"Banana",0,0,false));
		entityTypeMap.put(25, new Entity(25,1,4,1,105,"Wheat",0,0,false));
		entityTypeMap.put(26, new Entity(26,4,1,4,217,"Compost",0,0,true));
		entityTypeMap.put(27, new Entity(27,1,4,1,1022,"Tall Grass",0,0,false));
		entityTypeMap.put(28, new Entity(28,8,2,8,141,"Foilage",0,0,false));
		entityTypeMap.put(29, new Entity(29,1,2,1,105,"Nest",0,0,false));
		entityTypeMap.put(30, new Entity(30,1,2,1,1,"Raw Flour",0,0,false));
		
		entityTypeMap.put(40, new Entity(40,2,2,2,5,"Bread",0,0,false));
		
		entityTypeMap.put(100, new Entity(100,1,1,4,192,"Handle",0,0,true));
		entityTypeMap.put(101, new Entity(101,8,1,8,119,"Thatching",0,0,true));
		entityTypeMap.put(102, new Entity(102,8,1,8,194,"Stone Slab",2,0,true));
		entityTypeMap.put(103, new Entity(103,2,8,8,192,"Wood Wall",0,0,true));
		entityTypeMap.put(104, new Entity(104,1,4,1,192,"Rope",0,0,true));
		
		entityTypeMap.put(1000, new Entity(1000,1,1,1,194,"Flint",0,0,false));
		entityTypeMap.put(1001, new Entity(1001,1,2,1,199,"Stone Axe",0,0,true));
		entityTypeMap.put(1002, new Entity(1002,1,2,1,199,"Stone Pickaxe",0,0,true));
		entityTypeMap.put(1003, new Entity(1003,1,3,1,199,"Stone Sword",0,0,true));
		entityTypeMap.put(1010, new Entity(1010,1,1,1,1003,"Raft",0,0,true));
		
		entityTypeMap.put(9001, new Entity(9001,2,2,3,1,"Chicken",3,0,true)); //harvest by sword
		
		entityTypeMap.put(-1, new Entity(-1,8,2,8,141,"Rotting Foilage",0,0,false));
		entityTypeMap.put(-2, new Entity(-2,1,1,1,5,"Hatching Egg",0,0,true));

	}
	
	public static void setupColors()
	{
		brickColorMap.put(1,new Color(0.94901967048645,0.95294123888016,0.95294123888016));
		brickColorMap.put(5,new Color(0.84313732385635,0.77254909276962,0.60392159223557));
		brickColorMap.put(9,new Color(0.90980398654938,0.7294117808342,0.78431379795074));
		brickColorMap.put(11,new Color(0.50196081399918,0.73333334922791,0.85882359743118));
		brickColorMap.put(18,new Color(0.80000007152557,0.55686277151108,0.41176474094391));
		brickColorMap.put(21,new Color(0.76862752437592,0.15686275064945,0.10980392992496));
		brickColorMap.put(23,new Color(0.050980396568775,0.41176474094391,0.6745098233223));
		brickColorMap.put(24,new Color(0.96078437566757,0.80392163991928,0.18823531270027));
		brickColorMap.put(26,new Color(0.10588236153126,0.16470588743687,0.20784315466881));
		brickColorMap.put(28,new Color(0.15686275064945,0.49803924560547,0.27843138575554));
		brickColorMap.put(29,new Color(0.63137257099152,0.76862752437592,0.54901963472366));
		brickColorMap.put(37,new Color(0.29411765933037,0.59215688705444,0.29411765933037));
		brickColorMap.put(38,new Color(0.62745100259781,0.37254902720451,0.20784315466881));
		brickColorMap.put(45,new Color(0.70588237047195,0.82352948188782,0.89411771297455));
		brickColorMap.put(101,new Color(0.85490202903748,0.52549022436142,0.47843140363693));
		brickColorMap.put(102,new Color(0.43137258291245,0.60000002384186,0.79215693473816));
		brickColorMap.put(104,new Color(0.41960787773132,0.19607844948769,0.48627454042435));
		brickColorMap.put(105,new Color(0.88627457618713,0.60784316062927,0.25098040699959));
		brickColorMap.put(106,new Color(0.85490202903748,0.52156865596771,0.2549019753933));
		brickColorMap.put(107,new Color(0,0.56078433990479,0.61176472902298));
		brickColorMap.put(119,new Color(0.64313727617264,0.74117648601532,0.27843138575554));
		brickColorMap.put(125,new Color(0.91764712333679,0.72156864404678,0.57254904508591));
		brickColorMap.put(135,new Color(0.45490199327469,0.52549022436142,0.61568629741669));
		brickColorMap.put(141,new Color(0.15294118225574,0.27450981736183,0.17647059261799));
		brickColorMap.put(151,new Color(0.47058826684952,0.56470590829849,0.50980395078659));
		brickColorMap.put(153,new Color(0.58431375026703,0.47450983524323,0.46666669845581));
		brickColorMap.put(192,new Color(0.41176474094391,0.25098040699959,0.15686275064945));
		brickColorMap.put(194,new Color(0.63921570777893,0.63529413938522,0.64705884456635));
		brickColorMap.put(199,new Color(0.38823533058167,0.37254902720451,0.38431376218796));
		brickColorMap.put(208,new Color(0.89803928136826,0.89411771297455,0.87450987100601));
		brickColorMap.put(217,new Color(0.48627454042435,0.36078432202339,0.27450981736183));
		brickColorMap.put(226,new Color(0.99215692281723,0.91764712333679,0.55294120311737));
		brickColorMap.put(1001,new Color(0.97254908084869,0.97254908084869,0.97254908084869));
		brickColorMap.put(1002,new Color(0.80392163991928,0.80392163991928,0.80392163991928));
		brickColorMap.put(1003,new Color(0.066666670143604,0.066666670143604,0.066666670143604));
		brickColorMap.put(1004,new Color(1,0,0));
		brickColorMap.put(1005,new Color(1,0.68627452850342,0));
		brickColorMap.put(1006,new Color(0.70588237047195,0.50196081399918,1));
		brickColorMap.put(1007,new Color(0.63921570777893,0.29411765933037,0.29411765933037));
		brickColorMap.put(1008,new Color(0.75686281919479,0.74509805440903,0.258823543787));
		brickColorMap.put(1009,new Color(1,1,0));
		brickColorMap.put(1010,new Color(0,0,1));
		brickColorMap.put(1011,new Color(0,0.12549020349979,0.37647062540054));
		brickColorMap.put(1012,new Color(0.1294117718935,0.32941177487373,0.72549021244049));
		brickColorMap.put(1013,new Color(0.015686275437474,0.68627452850342,0.92549026012421));
		brickColorMap.put(1014,new Color(0.66666668653488,0.33333334326744,0));
		brickColorMap.put(1015,new Color(0.66666668653488,0,0.66666668653488));
		brickColorMap.put(1016,new Color(1,0.40000003576279,0.80000007152557));
		brickColorMap.put(1017,new Color(1,0.68627452850342,0));
		brickColorMap.put(1018,new Color(0.070588238537312,0.93333339691162,0.83137261867523));
		brickColorMap.put(1019,new Color(0,1,1));
		brickColorMap.put(1020,new Color(0,1,0));
		brickColorMap.put(1021,new Color(0.22745099663734,0.49019610881805,0.082352943718433));
		brickColorMap.put(1022,new Color(0.49803924560547,0.55686277151108,0.39215689897537));
		brickColorMap.put(1023,new Color(0.54901963472366,0.35686275362968,0.6235294342041));
		brickColorMap.put(1024,new Color(0.68627452850342,0.8666667342186,1));
		brickColorMap.put(1025,new Color(1,0.78823536634445,0.78823536634445));
		brickColorMap.put(1026,new Color(0.69411766529083,0.65490198135376,1));
		brickColorMap.put(1027,new Color(0.6235294342041,0.95294123888016,0.91372555494308));
		brickColorMap.put(1028,new Color(0.80000007152557,1,0.80000007152557));
		brickColorMap.put(1029,new Color(1,1,0.80000007152557));
		brickColorMap.put(1030,new Color(1,0.80000007152557,0.60000002384186));
		brickColorMap.put(1031,new Color(0.38431376218796,0.14509804546833,0.81960791349411));
		brickColorMap.put(1032,new Color(1,0,0.74901962280273));
	}
	
	public static void groundColorMap()
	{
		groundColorMap.put(0,1);
		groundColorMap.put(1,102);
		groundColorMap.put(2,5);
		groundColorMap.put(3,1022);
		groundColorMap.put(4,1022);
		groundColorMap.put(5,37);
		groundColorMap.put(6,217);
		groundColorMap.put(7,226);
	}
	
}
