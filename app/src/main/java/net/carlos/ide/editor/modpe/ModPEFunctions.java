package net.carlos.ide.editor.modpe;

import java.util.ArrayList;
import java.io.InputStream;
import android.content.Context;
import net.carlos.ide.MainActivity;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ModPEFunctions
{
   // Functions do ModPE
   private static ArrayList<String> functionsGlobais = new ArrayList<>();
   private static ArrayList<String> playerFunctions = new ArrayList<>();
   private static ArrayList<String> levelFunctions = new ArrayList<>();
   private static ArrayList<String> hooks = new ArrayList<>();
   private static ArrayList<String> allFunctions = new ArrayList<>();
   private static ArrayList<String> entityFunctions = new ArrayList<>();
   private static ArrayList<String> serverFunctions = new ArrayList<>();
   private static ArrayList<String> blockFunctions = new ArrayList<>();
   private static ArrayList<String> mobEffect = new ArrayList<>();
   private static ArrayList<String> modpeFunctions = new ArrayList<>();
   private static ArrayList<String> renderer = new ArrayList<>();

   private Context ctx;

   public ModPEFunctions()
   {
      this.ctx = MainActivity.APP_CONTEXT;

      loadGlobalFunctions();
      loadHooks();
      loadPlayerFunctions();
      loadLevelFunctions();
      loadEntityFunctions();
      loadRendererFunctions();
      loadModPEFunctions();
   }

   public ArrayList<String> getGlobalFunctions()
   {
      return functionsGlobais;
   }

   public ArrayList<String> getPlayerFunctions()
   {
      return playerFunctions;
   }

   public ArrayList<String> getLevelFunctions()
   {
      return levelFunctions;
   }

   public ArrayList<String> getHooks()
   {
      return hooks;
   }

   public ArrayList<String> getAllFunctions()
   {
      return allFunctions;
   }

   public ArrayList<String> getEntityFunctions()
   {
      return entityFunctions;
   }

   private boolean loadGlobalFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";

      try
      {
	 is = ctx.getAssets().open("modpe/functionsGlobais.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    functionsGlobais.add(nextLine);
	    allFunctions.add(nextLine);
	 }

	 br.close();

	 return true;
      }
      catch (IOException e)
      {}

      return false;
   }
   private void loadPlayerFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";

      try
      {
	 is = ctx.getAssets().open("modpe/playerFunctions.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
            playerFunctions.add(nextLine);
	    allFunctions.add(nextLine);
	 }

	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Um erro ocorreu %s%n", e.toString());
      }
   }

   private void loadLevelFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";

      try
      {
	 is = ctx.getAssets().open("modpe/levelFunctions.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    levelFunctions.add(nextLine);
	    allFunctions.add(nextLine);
	 }

	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Um erro ocorreu: %s%n", e.toString());
      }
   }

   private void loadHooks()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";

      try
      {
	 is = ctx.getAssets().open("modpe/hooks.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    hooks.add(nextLine);
	    allFunctions.add(nextLine);
	 }

	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Um erro ocorreu: %s%n", e.toString());
      }
   }

   private void loadEntityFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";

      try
      {
	 is = ctx.getAssets().open("modpe/entityFunctions.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    entityFunctions.add(nextLine);
	    allFunctions.add(nextLine);
	 }

	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Um erro ocorreu: %s%n", e.toString());
      }
   }

   private void loadServerFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";

      try
      {
	 is = ctx.getAssets().open("modpe/serverFunctions.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    serverFunctions.add(nextLine);
	 }

	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Uma exception foi lançada!: %s%n", e.toString());
      }
   }

   private void loadBlockFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";

      try
      {
	 is = ctx.getAssets().open("modpe/blockFunctions.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    blockFunctions.add(nextLine);
	 }
      }
      catch (IOException e)
      {}
   }
   
   private void loadRendererFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";
      
      try
      {
	 is = ctx.getAssets().open("modpe/rendererFunctions.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);
	 
	 while ((nextLine = br.readLine()) != null)
	 {
	    renderer.add(nextLine);
	    allFunctions.add(nextLine);
	 }
	 
	 br.close();
      }
      catch (IOException e)
      {}
   }
   
   private void loadModPEFunctions()
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      String nextLine = "";
      
      try
      {
	 is = ctx.getAssets().open("modpe/modpeFunctions.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);
	 
	 while ((nextLine = br.readLine()) != null)
	 {
	    modpeFunctions.add(nextLine);
	    allFunctions.add(nextLine);
	 }
	 
	 br.close();
      }
      catch (IOException e)
      {}
   }
}
