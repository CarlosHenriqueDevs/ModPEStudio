package net.carlos.ide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import net.carlos.ide.apiAndroid.AndroidAPI;
import net.carlos.ide.editor.CodeCompletion;
import net.carlos.ide.editor.EditorCode;
import net.carlos.ide.editor.LineCount;
import net.carlos.ide.editor.javascript.JavaScriptTools;
import net.carlos.ide.editor.modpe.ModPEFunctions;
import net.carlos.ide.java.JavaAPI;
//import org.apache.commons.codec.binary.Base64;
import android.widget.ScrollView;

public class MainActivity extends Activity 
{
   private static EditorCode code;
   public static LineCount lineNumbers;
   public static Activity APP_CONTEXT;
   private JavaAPI javaApi;
   public static ModPEFunctions modpe;
   private ArrayAdapter<String> completionAdapter;

   private SharedPreferences prefs;
   private Editor editor;

   public static CodeCompletion codeCompletion;
   public static ScrollView scroll;

   private static final String TAG = "MainActivity";

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
      File pastaMods = new File(Environment.getExternalStorageDirectory() + "/"
				+ "ModPEStudio");

      if (!pastaMods.exists())
	 pastaMods.mkdir();

      prefs = getSharedPreferences("ModPEStudio", MODE_PRIVATE);
      editor = prefs.edit();
      editor.putBoolean("changelong", true);
      editor.putString("lastCode", "//your code");
      editor.putBoolean("showAll", false);

      if (prefs.getBoolean("changelong", true))
      {
	 AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
	 d.setTitle("Changelong 1.0Beta");
	 d.setMessage(getChangelong());
	 d.setNeutralButton("Ok", null);
	 d.show();

	 editor.putBoolean("changelong", false).commit();
      }

      MainActivity.APP_CONTEXT = MainActivity.this;
      modpe = new ModPEFunctions();

      MainActivity.codeCompletion = new CodeCompletion(this, modpe.getGlobalFunctions());

      this.javaApi = new JavaAPI();
      this.lineNumbers = new LineCount(this);
      this.code = new EditorCode(MainActivity.this, lineNumbers);

      if (Configuration.REOPEN_FILES)
      {
	 EditorCode.loaded = false;

	 code.setText(prefs.getString("lastCode", "// your code"));

	 EditorCode.loaded = true;
      }

      LinearLayout llCompletion = (LinearLayout) findViewById(R.id.llCompletion);
      LinearLayout ll = (LinearLayout) findViewById(R.id.mainLinearLayout);
      scroll = (ScrollView) findViewById(R.id.mainScrollView);

      ll.addView(lineNumbers);
      ll.addView(code);

      llCompletion.addView(codeCompletion);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      super.onActivityResult(requestCode, resultCode, data);

      switch (requestCode)
      {
	 case 0:
	    Uri u = null;

	    if (data != null)
	       u = data.getData();

	    try
	    {
	       EditorCode.loaded = false;
	       if (u != null)
		  new OpenFileBackground(getPath(this, u), code).start();
	       Util.showToast(getPath(this, u), 2);
	       EditorCode.loaded = true;
	    }
	    catch (URISyntaxException e)
	    {
	       Util.showToast(e.toString(), 2);
	    }
	    break;
      }
   }


   @Override
   protected void onDestroy()
   {		 		 
      super.onDestroy();

      editor.putString("lastCode", code.getText().toString()).commit();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      getMenuInflater().inflate(R.menu.add_code, menu);
      getMenuInflater().inflate(R.menu.open_file, menu);
      getMenuInflater().inflate(R.menu.configuration, menu);

      menu.add(0, 6, 0, "Autocomplete");
      menu.add(0, 12, 0, "Android API");
      menu.add(0, 10, 0, "Save");
      menu.add(0, 11, 0, "Texture names");
      menu.add(0, 1, 0, R.string.selection_goline);

      SubMenu base64 = menu.addSubMenu(0, 7, 0, "Base64...");
      base64.add(0, 8, 0, "Encode");
      base64.add(0, 9, 0, "Decode");

      SubMenu sm = menu.addSubMenu(0, 2, 0, R.string.code);
      sm.add(0, 3, 0, R.string.code_comment);
      sm.add(0, 4, 0, R.string.selection_rename);
      sm.add(0, 5, 0, R.string.selection_get_set);

      return super.onCreateOptionsMenu(menu);
   }
   
   private void showGoLine()
   {
      final EditText goTo = new EditText(this);
      goTo.setHint("Line");
      goTo.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
      
      AlertDialog.Builder a = new AlertDialog.Builder(this);
      a.setTitle("Go to line");
      a.setView(goTo);
      a.setNeutralButton("Cancel", null);
      a.setPositiveButton("Ok", new DialogInterface.OnClickListener()
	 {

	    @Override
	    public void onClick(DialogInterface p1, int p2)
	    {
	       code.moveToLine(Integer.valueOf(goTo.getText().toString()));
	    }
	 
      });
      a.show();
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      switch (item.getItemId())
      {
	 case 1:
	    showGoLine();
	    break;
	 case 12:
	    showCompletionAPI();
	    break;
	 case 11:
	    Util.showWebView("http://zhuoweizhang.net/mcpetexturenames/", true);
	    break;
	 case 10:
	    showDialogSave();
	    break;
	 case 8:

	    showBase64Encoder();

	    break;
	 case 9:
	    showBase64Decoder();
	    break;
	 case R.id.configuration:
	    Intent i = new Intent(getApplicationContext(), Configuration.class);
	    startActivity(i);
	    break;
	 case R.id.open_modpe_script:
	    selectFile();
	    break;
	 case 6:
	    showCompletion();
	    break;

	 case 7:
	    JavaScriptTools jst = new JavaScriptTools(code);
	    jst.indentCode(2);
	    break;
      }			
      return super.onOptionsItemSelected(item);
   }

   private void saveMod(String modName)
   {
      File mod = new File(Environment.getExternalStorageDirectory() 
			  + "/ModPEStudio/" + modName + ".js");
      try
      {
	 FileOutputStream fos = new FileOutputStream(mod);
	 fos.write(code.getText().toString().getBytes());
	 fos.flush();
	 fos.close();
      }
      catch (IOException e)
      {}
   }

   private void showBase64Decoder()
   {
      EditText input = new EditText(MainActivity.this);
      input.setHint("Your code in Base64");

      final TextView output = new TextView(MainActivity.this);
      output.setTextIsSelectable(true);
      LinearLayout ll = new LinearLayout(MainActivity.this);
      ll.setOrientation(LinearLayout.VERTICAL);

      ll.addView(input);
      ll.addView(output);

      input.addTextChangedListener(new TextWatcher()
	 {

	    @Override
	    public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
	    {

	    }

	    @Override
	    public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
	    {

	    }

	    @Override
	    public void afterTextChanged(Editable p1)
	    {
	       //byte[] decoded = Base64.decodeBase64(p1.toString().getBytes());
	       //output.setText(new String(decoded));
	    }

	 });

      AlertDialog.Builder baseEncoder = new AlertDialog.Builder(MainActivity.this);
      baseEncoder.setTitle("Base64 Decoder");
      baseEncoder.setView(ll);
      baseEncoder.show();
   }

   private void showBase64Encoder()
   {
      EditText input = new EditText(MainActivity.this);
      input.setHint("Your code");

      final TextView output = new TextView(MainActivity.this);
      output.setTextIsSelectable(true);
      LinearLayout ll = new LinearLayout(MainActivity.this);
      ll.setOrientation(LinearLayout.VERTICAL);

      ll.addView(input);
      ll.addView(output);

      input.addTextChangedListener(new TextWatcher()
	 {

	    @Override
	    public void beforeTextChanged(CharSequence text, int start, int count, int end)
	    {

	    }

	    @Override
	    public void onTextChanged(CharSequence text, int start, int count, int end)
	    {

	    }

	    @Override
	    public void afterTextChanged(Editable e)
	    {
	       //byte[] encoded = Base64.encodeBase64(p1.toString().getBytes());
	       //output.setText(new String(encoded));
	    }

	 });

      AlertDialog.Builder baseEncoder = new AlertDialog.Builder(MainActivity.this);
      baseEncoder.setTitle("Base64 Encoder");
      baseEncoder.setView(ll);
      baseEncoder.show();

   }

   private void showCompletion()
   {
      completionAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, 
						   modpe.getGlobalFunctions());

      final ListView completion = new ListView(MainActivity.this);
      completion.setAdapter(completionAdapter);

      final CheckBox allFunctions = new CheckBox(MainActivity.this);
      allFunctions.setText("Show all functions/hooks");
      allFunctions.setChecked(prefs.getBoolean("showAll", false));

      if (allFunctions.isChecked())
      {
	 completionAdapter = new ArrayAdapter<String>(MainActivity.this, 
						      android.R.layout.simple_list_item_1, 
						      modpe.getAllFunctions());

         completion.setAdapter(completionAdapter);
      }

      allFunctions.setOnCheckedChangeListener(new OnCheckedChangeListener()
	 {

	    @Override
	    public void onCheckedChanged(CompoundButton p1, boolean p2)
	    {
	       editor.putBoolean("showAll", p2).commit();
	       if (p2)
	       {
		  completionAdapter = new ArrayAdapter<String>(MainActivity.this, 
							       android.R.layout.simple_list_item_1, 
							       modpe.getAllFunctions());

		  completion.setAdapter(completionAdapter);
	       }
	       else
	       {
		  completionAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, 
							       modpe.getGlobalFunctions());

		  completion.setAdapter(completionAdapter);
	       }
	    }

	 });

      completion.setOnItemClickListener(new AdapterView.OnItemClickListener()
	 {

	    @Override
	    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	    {
	       EditorCode.loaded = false;

	       if (p1.getItemAtPosition(p3).toString().contains("Model."))
	       {
		  code.getText().insert(code.getSelectionStart(), 
					p1.getItemAtPosition(p3).toString()
					.replace("Model.", ""));
	       }
	       else
	       if (p1.getItemAtPosition(p3).toString().contains("Renderer."))
	       {
		  code.getText().insert(code.getSelectionStart(), 
					p1.getItemAtPosition(p3).toString()
					.replace("Renderer.", ""));
	       }
	       else
	       if (p1.getItemAtPosition(p3).toString().contains("Part."))
	       {
		  code.getText().insert(code.getSelectionStart(), 
					p1.getItemAtPosition(p3).toString()
					.replace("Part.", ""));
	       }
	       else
		  code.getText().insert(code.getSelectionStart(), 
					p1.getItemAtPosition(p3).toString());

	       EditorCode.loaded = true;
	    }

	 });

      EditText text = new EditText(MainActivity.this);
      text.addTextChangedListener(new TextWatcher()
	 {

	    @Override
	    public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
	    {

	    }

	    @Override
	    public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
	    {

	    }

	    @Override
	    public void afterTextChanged(Editable p1)
	    {
	       completionAdapter.getFilter().filter(p1.toString());
	    }

	 });

      CheckBox classesJava = new CheckBox(MainActivity.this);
      classesJava.setChecked(false);
      classesJava.setText("Java API");

      classesJava.setOnCheckedChangeListener(new OnCheckedChangeListener()
	 {

	    @Override
	    public void onCheckedChanged(CompoundButton p1, boolean p2)
	    {
	       if (p2)
	       {
		  completionAdapter.addAll(javaApi.getAllClasses());
	       }
	       else
	       {
		  completionAdapter.clear();
		  if (allFunctions.isChecked())
		     completionAdapter.addAll(modpe.getAllFunctions());
		  else
		     completionAdapter.addAll(modpe.getGlobalFunctions());
	       }
	    }

	 });

      LinearLayout ll = new LinearLayout(MainActivity.this);

      LinearLayout ll2 = new LinearLayout(MainActivity.this);
      ll2.setOrientation(LinearLayout.HORIZONTAL);
      ll2.addView(allFunctions);
      ll2.addView(classesJava);

      ll.setOrientation(LinearLayout.VERTICAL);
      ll.addView(text);
      ll.addView(ll2);
      ll.addView(completion);

      AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
      ad.setView(ll);
      ad.setCancelable(true);
      ad.setOnDismissListener(new DialogInterface.OnDismissListener()
	 {

	    @Override
	    public void onDismiss(DialogInterface p1)
	    {
	       completionAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, 
							    modpe.getGlobalFunctions());

	       completion.setAdapter(completionAdapter);
	    }

	 });
      ad.show();
   }

   private void showCompletionAPI()
   {
      AndroidAPI droidApi = new AndroidAPI();

      final ArrayAdapter<String> apiAndroid = new ArrayAdapter<String>(this, 
								       android.R.layout.simple_list_item_1, droidApi.getWidget());

      LinearLayout completion = new LinearLayout(this);
      completion.setOrientation(LinearLayout.VERTICAL);

      EditText text = new EditText(this);

      text.addTextChangedListener(new TextWatcher()
	 {

	    @Override
	    public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
	    {

	    }

	    @Override
	    public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
	    {

	    }

	    @Override
	    public void afterTextChanged(Editable p1)
	    {
	       apiAndroid.getFilter().filter(p1.toString());
	    }

	 });

      ListView listCompletion = new ListView(this);
      listCompletion.setAdapter(apiAndroid);

      completion.addView(text);
      completion.addView(listCompletion);

      AlertDialog.Builder dialogCompletion = new AlertDialog.Builder(MainActivity.this);
      dialogCompletion.setView(completion);
      dialogCompletion.show();
   }

   private void selectFile()
   {
      Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
      intent.setType("*/*");
      startActivityForResult(intent, 0);
   }

   public static EditorCode getCodeEditor()
   {
      return MainActivity.code;
   }

   public static String getPath(Context context, Uri uri) throws URISyntaxException
   {
      if ("content".equalsIgnoreCase(uri.getScheme()))
      {
	 String[] projection = { "_data" };
	 Cursor cursor = null;

	 try
	 {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow("_data");
            if (cursor.moveToFirst())
	    {
	       return cursor.getString(column_index);
            }
	 }
	 catch (Exception e)
	 {

	 }
      }
      else if ("file".equalsIgnoreCase(uri.getScheme()))
      {
	 return uri.getPath();
      }

      return null;
   } 

   private void openScript(String path)
   {
      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;
      StringBuilder sb = new StringBuilder();
      String nextLine = "";

      try
      {
	 is = new FileInputStream(path);
	 isr = new InputStreamReader(is);
	 br = new BufferedReader(isr);

	 while ((nextLine = br.readLine()) != null)
	 {
	    sb.append(nextLine);
	    sb.append("\n");
	 }

	 br.close();

	 code.setText(sb.toString());
      }
      catch (IOException e)
      {
	 Toast.makeText(getApplicationContext(), "Arquivo inválido!", Toast.LENGTH_LONG).show();
      }
   }

   private String getChangelong()
   {
      String changelong = "";
      try
      {
	 InputStream is = getAssets().open("changelongs/1.0Beta.txt");
	 InputStreamReader isr = new InputStreamReader(is);
	 BufferedReader br = new BufferedReader(isr);
	 StringBuilder sb = new StringBuilder();

	 while ((changelong = br.readLine()) != null)
	 {
	    sb.append(changelong);
	    sb.append("\n");
	 }

	 br.close();

	 changelong = sb.toString();
      }
      catch (IOException e)
      {}

      return changelong;
   }

   private void showDialogSave()
   {
      final EditText nameOfMod = new EditText(this);
      nameOfMod.setHint("Name of mod");

      AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
      dialogo.setTitle("Enter the name of mod");
      dialogo.setView(nameOfMod);
      dialogo.setPositiveButton("Save", new DialogInterface.OnClickListener()
	 {

	    @Override
	    public void onClick(DialogInterface p1, int p2)
	    {
	       saveMod(nameOfMod.getText().toString());
	    }

	 });
      dialogo.setNegativeButton("Cancel", null);
      dialogo.show();
   }

   private static String[] itens = {"Food", "Item", "Mob"};
}
