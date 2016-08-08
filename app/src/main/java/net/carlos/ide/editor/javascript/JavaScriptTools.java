package net.carlos.ide.editor.javascript;

import net.carlos.ide.editor.EditorCode;
import java.util.Scanner;
import net.carlos.ide.MainActivity;

public class JavaScriptTools
{
   private EditorCode editor;

   public JavaScriptTools(EditorCode editor)
   {
      this.editor = editor;
   }

   public void addTryCatch()
   {
      EditorCode.loaded = false;
      
      int cursorStart = editor.getSelectionStart();

      editor.getText().insert(cursorStart, 
			      "try\n{\n\n}\ncatch (exception)\n{\n}");
			      
      EditorCode.loaded = true;
   }

   public void addVariable(String varName, boolean isGlobal, String value)
   {
      int start = editor.getSelectionStart();

      if (!isGlobal)
	 editor.getText().insert(start, "var " + varName + " = " + value);
      else
	 editor.getText().insert(0, "var " + varName + " = " + value);
   }

   public void addVariable(String varName, boolean isGlobal)
   {
      int start = editor.getSelectionStart();

      if (!isGlobal)
	 editor.getText().insert(start, "var " + varName);
      else
	 editor.getText().insert(0, "var " + varName);
   }

   public void createGettersAndSetters()
   {
      int inicio = editor.getSelectionStart();
      int fim = editor.getSelectionEnd();
      String palavra = editor.getText().toString().substring(inicio, fim);

      editor.getText().append("function get" 
			      + palavra 
			      + "()\n{\nreturn null;\n}\n\n");

      editor.getText().append("function set" 
			      + palavra + "(p)\n{\n" 
			      + palavra + " = p; \n");
   }

   public String renameVariable(String newVariable)
   {
      int start = editor.getSelectionStart();
      int fim = editor.getSelectionEnd();

      String variable = editor.getText().toString().substring(start, fim);

      editor.getText().replace(start, fim, newVariable);

      return variable;
   }

   public void commentCode()
   {
      int start = editor.getSelectionStart();

      editor.getText().insert(start, "//");
   }

   public void outcommentCode()
   {
      String replacedCode = editor.getText().toString().replace("//", "");

      editor.setText(replacedCode);
   }

   public void createFunction(String functionName)
   {
      editor.getText().append("function " + functionName + "()\n{\n\n}");
   }

   public void replaceAll(String palavra, String newPalavra)
   {
      editor.getText().toString().replace(palavra, newPalavra);
   }

   public boolean findPalavra(String palavra)
   {
      if (editor.getText().toString().contains(palavra))
      {
	 int start = editor.getText().toString().lastIndexOf(palavra);
	 int end = start + palavra.length();

	 editor.setSelection(start, end);

	 return true;
      }

      return false;
   }

   public void indentCode(int numberOfSpaces)
   {
      Scanner out = new Scanner(editor.getText().toString());
      String codeIndented = "";
      
      while (out.hasNext())
      {
	 String line = "   " + out.next();
	 codeIndented += line + "\n";
      }
      
      editor.setText(codeIndented);
   }
   
}
