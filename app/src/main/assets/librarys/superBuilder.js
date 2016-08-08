//**************************************************
//																
//@author Do mesmo criador do ModPE Studio. Carlos                  
//@twitter CarlosIdeScript					
//
//@version 1.0b
//@description> Esta é uma pequena
//Biblioteca para a construção de blocos,
//E outros itens.Podem ver, alterar o código																
//Afinal de contas, so aprendemos dessa maneira!
//																
//**************************************************

// Blocos do mundo
const AIR = 0;
const STONE = 1;
const GRASS = 2;
const DIRT = 3;
const COBBLESTONE = 4;
const WOODEN_PLANKS = 5;
const BEDROCK = 7;
const WATER = 8;
const LAVA = 10;
const GRAVEL = 13;
const IRON_ORE = 15;

// Blocos/Items que podem causar danos
const TNT = 46;
const FIRE = 51;
const MONSTER_SPAWNER = 52;

//Blocos de minérios
const BLOCK_DIAMOND = 57;
const BLOCK_IRON = 42;
const BLOCK_GOLD = 41;
const BLOCK_ESMERALD = 133;

// Variáveis da biblioteca
const LEFT = 1;
const RIGHT = 2;
const TOP = 3;
const BOTTOM = 4;
var builds = new java.lang.ArrayList();

//@param direction = Substitua por LEFT, RIGHT, TOP, BOTTOM,
//de acordo com a ordem que vc deseja que a construção siga
function buildInVertical(tile, count, x, y, z, direction)
{
	 builds.add("Tile = " + tile + "\ncount = "
	          + count + "Orientation = Vertical");
	 
   for (var v = 0; v < count; v++)
   {
      build(tile, x, y, z);
      
      switch (direction)
      {
         case TOP:
           y++;
           break;
           
         case BOTTOM:
           y--;
           break;
           
          default:
            y++;
            break;
      }
   }
}

//@param direction = O mesmo do buildInVertical, a única diferença é que esse,
//decrementa ou incrementa a variável x
function buildInHorizontal(tile, count, x, y, z, direction)
{
	 builds.add("Tile = " + tile + "\ncount = "
	          + count + "Orientation = Horizontal");
						
   for (var v = 0; v < count; v++)
   {
      build(tile, x, y, z);
			
      switch (direction)
      {
	case RIGHT:
	   x++;
	break;
						
        case LEFT:
	   x--;
	break;
						
	default:
	   x++;
	break;
	
      }
   }
}

function buildCube(tile, x, y, z, horizontalCount, colunas, altura)
{
	 // Colunas
	 for (var v = 0; v <= colunas; v++)
	 {
	   build(tile, x, y, z);
	   x++;
	   y++;
	 }
}

// Em breve
function buildHouse()
{
	 builds.add("Tile = Varios\nOrientation = Vertical, Horizontal \nBuild = House");
	 
	 var x = Player.getX();
	 var y = Player.getY();
	 var z = Player.getZ();
	 var tileVertical = WOODEN_PLANKS;
	 var tileHorizontal = COBBLESTONE;
}

function buildPortal()
{
   builds.add("Tile = Obdsian\nOrientation = Horizontal, Vertical"
	    + "build = Portal");
}

function build(tile, x, y, z)
{
   setTile(x, y, z, tile, 0);
}
