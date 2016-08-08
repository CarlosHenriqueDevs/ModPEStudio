package net.carlos.ide;

import android.app.ProgressDialog;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import net.carlos.ide.editor.EditorCode;

public class OpenFileBackground extends Thread
{
   private String path;
   private int lines;
   private ProgressDialog loading;
   private EditorCode editor;
   private String nextLine = new String("");

   public OpenFileBackground(String path, EditorCode editor)
   {
      this.path = path;
      this.editor = editor;

      this.loading = new ProgressDialog(MainActivity.APP_CONTEXT);
      MainActivity.APP_CONTEXT.runOnUiThread(new Runnable()
         {

            @Override
            public void run()
            {
               loading.setMessage("Loading line: " + 1);
               loading.setTitle("Please wait");
            }
            
         
      });
      this.loading.show();
   }

   @Override
   public void run()
   {
      EditorCode.loaded = false;

      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      final StringBuilder sb = new StringBuilder();

      try
      {
	 is = new FileInputStream(path);
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    sb.append(nextLine + "\n");

	    MainActivity.APP_CONTEXT.runOnUiThread(new Runnable()
	       {

		  @Override
		  public void run()
		  {
		     editor.setText((sb.toString()));
		  }

	       });

	    lines++;

	    loading.setMessage("Loading line: " + lines);
	 }

	 br.close();
	 loading.dismiss();

	 EditorCode.loaded = true;

	 try
	 {
	    join();
	 }
	 catch (InterruptedException e)
	 {
	    Util.showToast("Um erro ocorreu ao interromper", 2);
	 }
      }
      catch (IOException e)
      {
	 Util.showDialog("Um erro ocorreu", e.toString());
      }
   }

}
