package net.carlos.ide.editor;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class CodeCompletion extends ListView
{
   private String[] defaultC = {"public", "private", "void"};
   
   public CodeCompletion(Context ctx, ArrayList<String> arrayCompletion)
   {
      super(ctx);

      ArrayAdapter<String> adapter = new ArrayAdapter<String>
      (ctx, android.R.layout.simple_list_item_1, arrayCompletion);

      setAdapter(adapter);
   }
   
   public CodeCompletion(Context ctx)
   {
      super (ctx);
      
      ArrayAdapter<String> adapter = new ArrayAdapter<String>
      (ctx, android.R.layout.simple_list_item_1, defaultC);
      
      setAdapter(adapter);
   }
}
