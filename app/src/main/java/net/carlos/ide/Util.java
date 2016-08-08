package net.carlos.ide;

import android.widget.Toast;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import android.webkit.WebView;

public class Util
{
   public static void showToast(String message, int duration)
   {
      Toast.makeText(MainActivity.APP_CONTEXT, message, duration);
   }
   
   public static void showDialog(String title, String message)
   {
      AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.APP_CONTEXT);
      dialog.setTitle(title);
      dialog.setMessage(message);
      dialog.setNeutralButton("Ok", null);
      dialog.show();
   }
   
   public static void showProgress(String title, String message)
   {
      ProgressDialog pd = new ProgressDialog(MainActivity.APP_CONTEXT);
      pd.setMessage(message);
      pd.setTitle(title);
      pd.show();
   }
   
   public static String getFileContent(InputStream file) throws IOException
   {
      String result = "";
      InputStreamReader isr = new InputStreamReader(file);
      BufferedReader br = new BufferedReader(isr);
      String nextLine = "";
      
      while ((nextLine = br.readLine()) != null)
      {
	 result = nextLine + "\n";
      }
      
      br.close();
      
      return result;
   }
   
   public static void showWebView(String url, boolean enableJavaScript)
   {
      WebView web = new WebView(MainActivity.APP_CONTEXT);
      web.loadUrl(url);
      web.getSettings().setJavaScriptEnabled(enableJavaScript);
      
      new AlertDialog.Builder(MainActivity.APP_CONTEXT).setView(web).show();
   }
}
