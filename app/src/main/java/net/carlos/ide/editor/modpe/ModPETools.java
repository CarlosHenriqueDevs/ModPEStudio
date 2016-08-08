package net.carlos.ide.editor.modpe;

public class ModPETools
{
   public ModPETools()
   {

   }

   public String createItem(String name, int id, int damage, int count, String texture)
   {
      String code = "ModPE.setItem(" 
	 + id 
	 + "," 
	 + texture
	 + "," 
	 + damage 
	 + "," 
	 + name 
	 + "," 
	 + count + ");";

      return code;
   }

   public String createBlock()
   {
      return null;
   }

   public String setTime(int time)
   {
      return "Level.setTime(" + time + ");";
   }

   public String setTile(int tileId, int damage, float x, float y, float z)
   {
      return "setTile(" + x + "," + y + "," + z + "," + tileId + "," + damage + ");";
   }
} 
