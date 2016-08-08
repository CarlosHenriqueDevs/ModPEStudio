package net.carlos.ide.apiAndroid;

import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;
import net.carlos.ide.MainActivity;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import net.carlos.ide.Util;
import android.content.Context;

public class AndroidAPI
{
   private ArrayList<String> allClasses = new ArrayList<>();
   private ArrayList<String> android_widget = new ArrayList<>();

   private ArrayList<String> interfaces = new ArrayList<>();

   public AndroidAPI()
   {
      try
      {
	 loadAndroidWidget();
      }
      catch (IOException e)
      {
	 Util.showDialog("Um erro ocorreu", "Código do erro: " + e.toString());
      }
   }

   public ArrayList<String> getAllClasses()
   {
      return allClasses;
   }

   public ArrayList<String> getWidget()
   {
      return android_widget;
   }

   private void loadAndroidWidget() throws IOException
   {
      InputStream is = MainActivity.APP_CONTEXT.getAssets().open("javaAPI/androidAPI/android.widget.txt");

      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String nextLine = "";

      while ((nextLine = br.readLine()) != null)
      {
	 android_widget.add(nextLine);
	 allClasses.add(nextLine);
      }

      br.close();
   }

   private void loadInterface()
   {
      
   }

   public String getInterfaceCode(String interfaceName)
   {
      switch (interfaceName)
      {
	 case "OnClickListener":
	    return "public void onClick(view)\n{\n\n}";
      }
      
      return null;
   }
}
