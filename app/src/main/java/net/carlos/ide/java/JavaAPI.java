package net.carlos.ide.java;

import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.content.Context;
import net.carlos.ide.MainActivity;
import java.io.IOException;

public class JavaAPI
{
   private ArrayList<String> allClasses = new ArrayList<>();
   private ArrayList<String> java_lang = new ArrayList<>();
   private ArrayList<String> java_io = new ArrayList<>();
   private ArrayList<String> java_util = new ArrayList<>();

   private InputStream is;
   private InputStreamReader isr;
   private BufferedReader br;

   public JavaAPI()
   {
      loadJavaLang();
      loadJavaIO();
      loadJavaUtil();
   }

   public ArrayList<String> getJavaLang()
   {
      return java_lang;
   }
   
   private void loadJavaUtil()
   {
      try
      {
	 InputStream is = MainActivity.APP_CONTEXT.getAssets().open("javaAPI/java.util.txt");
	 InputStreamReader isr = new InputStreamReader(is);
	 BufferedReader br = new BufferedReader(isr);
	 String nextLine = "";
	 
	 while ((nextLine = br.readLine()) != null)
	 {
	    java_util.add(nextLine);
	    allClasses.add(nextLine);
	 }
	 
	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Um erro ocorreu: %s%n", e.toString());
      }
   }

   private void loadJavaLang()
   {
      Context c = MainActivity.APP_CONTEXT;

      try
      {
	 is = c.getAssets().open("javaAPI/java.lang.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);
	 String line = "";

	 while ((line = br.readLine()) != null)
	 {
	    java_lang.add(line);
	    allClasses.add(line);
	 }

	 br.close();
      }
      catch (IOException e)
      {
	 System.out.printf("Um erro ocorreu: %s%n", e.toString());
      }
   }

   private void loadJavaIO()
   {
      Context c = MainActivity.APP_CONTEXT;

      try
      {
	 is = c.getAssets().open("javaAPI/java.io.txt");
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);
	 String nextLine = "";

	 while ((nextLine = br.readLine()) != null)
	 {
	    java_io.add(nextLine);
	    allClasses.add(nextLine);
	 }

	 br.close();
      }
      catch (IOException e)
      {

      }
   }

   public ArrayList<String> getAllClasses()
   {
      return allClasses;
   }
}
