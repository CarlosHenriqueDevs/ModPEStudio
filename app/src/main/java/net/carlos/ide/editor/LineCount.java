package net.carlos.ide.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.TextView;
import net.carlos.ide.Configuration;

public class LineCount extends TextView
{
   public LineCount(Context ctx)
   {
      super(ctx);

      setTextSize(Configuration.FONTE_SIZE);
      setTextColor(Color.BLUE);
   }

   @Override
   protected void onDraw(Canvas canvas)
   {
      super.onDraw(canvas);

      if (getTextSize() != Configuration.FONTE_SIZE)
      {
	 setTextSize(Configuration.FONTE_SIZE);
      }
   }

   public void loadLineCount(EditorCode editor)
   {
      StringBuilder sb = new StringBuilder();

      for (int i = 1; i <= editor.getLineCount(); i++)
      {
	 sb.append(i + "\n");

	 setText(sb.toString());
      }

   }
   
   public void addNumber(int number)
   {
      setText(getText().toString() + "\n" + number);
   }
   
   public void removeNumber(int number)
   {
      setText(getText().toString().replace("\n" + number, ""));
   }
}
