package net.carlos.ide.editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.carlos.ide.Configuration;
import net.carlos.ide.MainActivity;

public class EditorCode extends EditText implements ActionMode.Callback, TextWatcher
{
   private List<String> keyWords = new ArrayList<>();
   private int current = 0;
   public static boolean loaded = false;
   private List<ForegroundColorSpan> colors = new ArrayList<>();
   private Activity ctx;
   private SharedPreferences prefs;

   private CharSequence text;
   private int start;

   private CodeCompletion codeCompletion;
   private LineCount lineCount;
   
   private int previousLine = 1;

   private PopupWindow window;

   public EditorCode(Activity ctx, LineCount lineCount)
   {
      super(ctx);

      this.codeCompletion = new CodeCompletion(ctx);
      this.lineCount = lineCount;

      prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

      loadKeyWords();

      this.ctx = ctx;
      this.window = new PopupWindow();
      this.window.setContentView(codeCompletion);

      setTypeface(Typeface.MONOSPACE);

      ArrayList<String> hooks = MainActivity.modpe.getHooks();

      setHint(hooks.get(new Random().nextInt(hooks.size())) + "\n{\n\n}");
      setHorizontallyScrolling(true);
      setCustomSelectionActionModeCallback(this);
      setSelectAllOnFocus(true);
      setBackgroundColor(Color.WHITE);
      setTextSize(Configuration.FONTE_SIZE);

      this.loaded = true;

      setGravity(Gravity.TOP);
      addTextChangedListener(this);
      setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
      setSingleLine(false);
      setWidth(900);
      setHeight(1300);
      setLines(300);
   }

   @Override
   public boolean onCreateActionMode(ActionMode mode, Menu menu)
   {
      menu.add(Menu.NONE, 999999, Menu.NONE, "Rename");

      return true;
   }

   @Override
   public boolean onPrepareActionMode(ActionMode mode, Menu menu)
   {

      return false;
   }

   @Override
   public boolean onActionItemClicked(ActionMode mode, MenuItem item)
   {
      switch (item.getItemId())
      {
	 case 999999:
	    final EditText newName = new EditText(MainActivity.APP_CONTEXT);
	    newName.setTextColor(Color.BLACK);
	    newName.setCursorVisible(true);
	    newName.setHint("Name");

	    AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.APP_CONTEXT);
	    d.setTitle("Rename");
	    d.setView(newName);
	    d.setPositiveButton("Ok", new DialogInterface.OnClickListener()
	       {

		  @Override
		  public void onClick(DialogInterface p1, int p2)
		  {
		     getText().replace(getSelectionStart(), getSelectionEnd(), newName.getText().toString());
		  }

	       });
	    d.setNeutralButton("Cancel", null);
	    d.show();

	    return true;
      }
      return false;
   }

   @Override
   public void onDestroyActionMode(ActionMode mode)
   {
      
   }

   @Override
   public void draw(Canvas canvas)
   {
      super.draw(canvas);

      int fonteSize = Integer.valueOf(prefs.getString("edFonteSize", "16"));

      if (getTextSize() != fonteSize)
      {
	 setTextSize(fonteSize);
      }

      if (prefs.getBoolean("cbDestaque", true))
      {
	 containsTextIndex(keyWords.get(current));

	 containsTextLast(keyWords.get(current));

	 current ++;

	 if (current >= keyWords.size())
	 {
	    current = 0;
	 }
      }
   }

   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after)
   {
      
   }

   @Override
   public void onTextChanged(CharSequence text, int start, int before, int count)
   {
      this.text = text;
      this.start = start;

      if (EditorCode.loaded);
      // window.showAtLocation(MainActivity.APP_CONTEXT.getWindow().getDecorView(), Gravity.CENTER | Gravity.BOTTOM, 0, 0);
   }

   @Override
   public void afterTextChanged(Editable s)
   {
      if (EditorCode.loaded && prefs.getBoolean("modoTest", false))
      {
	 simpleCompletion(text, start);
      }
   }

   private void simpleCompletion(CharSequence text, int start)
   {
      text = getText().toString();

      if (text.toString().substring(start).contains("{"))
      {
	 getText().insert(getSelectionStart(), "}");
	 setSelection(getSelectionStart() - 1);
      }

      if (text.toString().substring(start).contains("("))
      {
	 getText().insert(getSelectionStart(), ")");
	 setSelection(getSelectionStart() - 1);
      }

   }

   private void loadKeyWords()
   {
      this.keyWords.add("var");
      this.keyWords.add("this");
      this.keyWords.add("if");
      this.keyWords.add("else");
      this.keyWords.add("switch");
      this.keyWords.add("return");
      this.keyWords.add("for");
      this.keyWords.add("while");
      this.keyWords.add("do");
      this.keyWords.add("function");
      this.keyWords.add("new");
      this.keyWords.add("this");
      this.keyWords.add("case");
      this.keyWords.add("break");
      this.keyWords.add("import");
      this.keyWords.add("try");
      this.keyWords.add("catch");
   }

   private boolean containsTextIndex(String palavra)
   {
      if (getText().toString().contains(palavra))
      {
	 int start = getText().toString().indexOf(palavra);
	 int fim = start + palavra.length();

	 applyColor(Color.BLUE, start, fim);

	 return true;
      }

      return false;
   }

   private boolean containsTextLast(String palavra)
   {
      if (getText().toString().contains(palavra))
      {
	 int inicio = getText().toString().lastIndexOf(palavra);
	 int fim = inicio + palavra.length();

	 applyColor(Color.BLUE, inicio, fim);

	 return true;
      }

      return false;
   }

   private void applyColor(int color, int start, int end)
   {
      this.colors.add(new ForegroundColorSpan(color));

      getText().setSpan(colors.get(colors.size() - 1), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
   }

   public String getStringFromLine(int line)
   {
      String[] linhas = getText().toString().split("\n");

      return linhas[line];
   }

   public StringBuilder getLines()
   {
      String[] lines = getText().toString().split("\n");
      StringBuilder contentLines = new StringBuilder();

      for (String s : lines)
      {
	 contentLines.append(s);
	 contentLines.append("\n");
      }

      return contentLines;
   }

   public void moveToLine(int line)
   {
      String[] lines = getText().toString().split("\n");

      String selected = lines[line - 1];

      int start = getText().toString().lastIndexOf(selected);

      setSelection(start);
   }

   private void showMessage(String text, int duration)
   {
      Toast.makeText(getContext(), text, duration).show();
   }

}
