package net.carlos.ide.editor.javascript;

import java.util.ArrayList;
import net.carlos.ide.Util;
import net.carlos.ide.MainActivity;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class JavaScriptAPI
{
   public ArrayList<String> math = new ArrayList<>();

   public JavaScriptAPI()
   {

   }

   private void loadMath()
   {
      try
      {
	 InputStream is = MainActivity.APP_CONTEXT.getAssets().open("");
	 InputStreamReader isr = new InputStreamReader(is);
	 BufferedReader br = new BufferedReader(isr);
	 String nextLine = "";

	 while ((nextLine = br.readLine()) != null)
	 {
	    math.add(nextLine);
	 }

	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Um erro ocorreu: %s%n", e.toString());
      }
   }
}
