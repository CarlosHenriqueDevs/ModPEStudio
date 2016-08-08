var activity = com.mojang.minecraftpe.MainActivity.currentMainActivity.get();
  //A variable we'll use later to add the button to the current MCPE activity            
  activity.runOnUiThread(new java.lang.Runnable(){ run: function() {
  //This will allow our button to run on the current UI thread of MCPE  
        try { //Try to create our button
          var buttonWindow = new android.widget.PopupWindow();
          //Make our variable a usable window
          var layout = new android.widget.RelativeLayout(activity);
          //A layout to put into our window
          var button = new android.widget.Button(activity);
          //A button to put in our layout
          button.setText("Press Me!");
          //Write some text upon our button
          button.setOnClickListener(new android.view.View.OnClickListener({
                //When we press our button
                onClick: function(viewarg) { //This lets our button run something
                //The function() comes from activity.runOnUiThread()
                  Level.explode(Player.getX(), Player.getY(), Player.getZ(), 3.0);
                  clientMessage("This is our button!");
                }
          }));
          layout.addView(button);//Add our button to our layout
          buttonWindow.setContentView(layout);//Add our layout to our window
          buttonWindow.setWidth(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
          buttonWindow.setHeight(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
          //Make our window the same size as the button within
          buttonWindow.setBackgroundDrawable(new
android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
          //The outline of our button's(The window) color
          buttonWindow.showAtLocation(activity.getWindow().getDecorView(), android.view.Gravity.RIGHT | android.view.Gravity.BOTTOM, 0, 0);
          //The location of our button on the screen, BOTTOM RIGHT
          //The 0, 0 is the margin size, use this to push it around along those locations
        }catch(problem){
          print("Button could not be displayed: " + problem); //Print our error if we failed to make the button
        }
  }}));
}

