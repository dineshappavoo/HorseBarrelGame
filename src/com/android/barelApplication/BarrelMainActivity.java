package com.android.barelApplication;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



@SuppressLint("DrawAllocation")
public class BarrelMainActivity extends Activity implements SensorEventListener, OnClickListener {
	private SensorManager sensorManager; 
	private Sensor accelerometer;
	private Canvas c;
    private int width;
    private int height;
	float x, y;
	long diff;
	long elapsedrealtime;
	long pauseelapsed;
	long startpausetime;
	int barrelRadius, movingCircleRadius;
	boolean status = false;
	boolean circle1=false, circle2 = false, circle3 = false;
	boolean resetcircle1c = false, resetcircle1a = false;
	int c1q1c = 0, c1q2c = 0, c1q3c = 0, c1q4c=0, c1q1a = 0, c1q2a = 0, c1q3a = 0, c1q4a=0, c2q1c = 0, c2q2c = 0, c2q3c = 0, c2q4c=0, c2q1a = 0, c2q2a = 0, c2q3a = 0, c2q4a=0,c3q1c = 0, c3q2c = 0, c3q3c = 0, c3q4c=0,  c3q1a = 0, c3q2a = 0, c3q3a = 0, c3q4a=0;
	int c1cqc =0, c1pqc =0,c1cqa =0, c1pqa =0, c1cc = 0, c1ca=0, c2cqc =0, c2pqc =0,c2cqa =0, c2pqa =0, c2cc = 0, c2ca=0, c3cqc =0, c3pqc =0,c3cqa =0, c3pqa =0, c3cc = 0, c3ca=0;
	BarrelGameView gameView = new BarrelGameView();
	float c1sx, c1sy;
    EditText timer;
    Chronometer mChronometer;
    float alpha = 0.8f;
    float[] gravity = {0.0f,0.0f,0.0f};
    float[] linear_acceleration = {0.0f,0.0f,0.0f};
  @Override
  public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_barrel_main);
        
		 mChronometer = (Chronometer) findViewById(R.id.chronometer1);
		 
		 final Button start = (Button) findViewById(R.id.button1);
         start.setOnClickListener(this);
         final Button stop = (Button) findViewById(R.id.button2);
         stop.setOnClickListener(this);
         //final Button endgame = (Button) findViewById(R.id.button3);
         //endgame.setOnClickListener(this);
         //setContentView(new myView(this));
         sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
 		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
 		//mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		Display display = getWindowManager().getDefaultDisplay();
		
	    
	    gameView.initialize(this);
	    gameView.setBitmap();
	    gameView.setCanvas();
	    gameView.drawStart();


  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.barrel_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_start:
	        	elapsedrealtime = SystemClock.elapsedRealtime();
	      	  startC(elapsedrealtime);
	      	  Button Start = (Button) findViewById(R.id.button1);
	      	  Start.setEnabled(false);
	      	  status = true;
	            return true;
	        case R.id.action_exit:
	        	finish();
	        	return true;
	        
	        case R.id.action_scores:
	        	showscores();
	        	return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
 private void showscores() {
		// TODO Auto- method stub
	 Intent showScoreIntent = new Intent(BarrelMainActivity.this,ShowScoreActivity.class);
	 startActivity(showScoreIntent);
	 
	}

/* Start timer */
  public void startC(long basetime)
  {
	  diff = 0;
	  mChronometer.setBase(basetime);
	  mChronometer.start();
	  mChronometer.setOnChronometerTickListener(
		         new Chronometer.OnChronometerTickListener(){
		     @Override
		     public void onChronometerTick(Chronometer chronometer) {
		      // TODO Auto-generated method stub
		      long myElapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
		      pauseelapsed = myElapsedMillis;
		      String strElapsedMillis = (((myElapsedMillis/1000)/3600)%60+":"+((myElapsedMillis/1000)/60)%60 + ":" + (int) ((myElapsedMillis/1000)%60));
		      
		     }}
		       );
	  sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
  }
  /* Handle clicks */
  @Override
  public void onClick(View v)
  {

		switch(v.getId()) {
      case R.id.button1:
    	  
    	  elapsedrealtime = SystemClock.elapsedRealtime();
    	  startC(elapsedrealtime);
    	  Button Start = (Button) findViewById(R.id.button1);
    	  Start.setEnabled(false);
    	  status = true;
        break;
    
      case R.id.button2:
    	  
    	  Button Pause = (Button) findViewById(R.id.button2);
    	  if (Pause.getText().toString().toLowerCase().equals("pause"))
    	  {
    		  startpausetime = SystemClock.elapsedRealtime();
    		  mChronometer.stop();
    		  sensorManager.unregisterListener(this, accelerometer);
    	  Pause.setText("Resume");
    	  status = false;
    	  }
    	  else
    	  {
    		  
    		  startC(mChronometer.getBase() + (SystemClock.elapsedRealtime()-startpausetime));
    		  Pause.setText("Pause");
    		  status = true;
    	  }
        break;

    }
		
 	
  }
	public void draw (float xa, float ya)
	{
		Paint pblack = new Paint();
    	pblack.setColor(Color.WHITE);
		//penality = 0;
		gameView.setBitmap();
		gameView.setCanvas();
		if (!circle1 && !circle2 && !circle3)
		{
			gameView.drawBarrels();
		}
		else
		{
			if (circle1)
			{
				gameView.drawBarrel1(gameView.Pgreen);
			}
			else
			{
				gameView.drawBarrel1(gameView.Pblack);
			}
			if (circle2)
			{
				gameView.drawBarrel2(gameView.Pgreen);
			}
			else
			{
				gameView.drawBarrel2(gameView.Pblack);
			}
			if (circle3)
			{
				gameView.drawBarrel3(gameView.Pgreen);
			}
			else
			{
				gameView.drawBarrel3(gameView.Pblack);
			}
		}
	  
        boolean clash1 = false, clash2 = false, clash3 = false, clash4= false;
               if (gameView.c1clash(gameView, xa, ya))
        {                             
        	clash1 = true;
        }
        else if (gameView.c2clash(gameView, xa, ya))
        {
        	
        	clash2 = true;
        
        }
        else if (gameView.c3clash(gameView, xa, ya))
        {
       
        	clash3 = true;
        }
        else
        {
        	
        	
        gameView.setxy(gameView, xa, ya);
        	
        }
        if (gameView.boundaryclash(gameView, xa, ya))
        {
	    	 diff = diff + (5*1000);
	    	 mChronometer.setBase(elapsedrealtime-diff);
	    	 
        }
        gameView.drawAdhoc(gameView.x, gameView.y, gameView.movingCircleRadius, gameView.Pblack);
        
	    if (clash1)
	    {
	    	gameView.drawAdhoc(gameView.circle1width, gameView.circle1height,gameView.barrelRadius, gameView.Pred);
	    }
	    if (clash2)
	    {
	    	gameView.drawAdhoc(gameView.circle2width, gameView.circle2height, gameView.barrelRadius, gameView.Pred);
	    }
	    if (clash3)
	    {
	    	gameView.drawAdhoc(gameView.circle3width, gameView.circle3height, gameView.barrelRadius, gameView.Pred);
	    }
	    if (!clash1 && !clash2 && !clash3)
	    {
	    if (!circle1)
        {
	    	if (gameView.determineCircle1revolution(gameView))
	    	{
	    		circle1 = true;
	    		gameView.drawAdhoc(gameView.circle1width, gameView.circle1height, gameView.barrelRadius, gameView.Pgreen);
	    	}
        }
	    	        if (!circle2)
	            {
	    	        	if (gameView.determineCircle2revolution(gameView))
	    		    	{
	    	        		circle2 = true;
	    		    		gameView.drawAdhoc(gameView.circle2width, gameView.circle2height, gameView.barrelRadius, gameView.Pgreen);
	    		    	}
	       	    }
	            if (!circle3)
	            {
	            	if (gameView.determineCircle3revolution(gameView))
	    	    	{
	            		circle3 = true;
	    	    		gameView.drawAdhoc(gameView.circle3width, gameView.circle3height, gameView.barrelRadius, gameView.Pgreen);
	    	    	}
	       	        }
        
       
	    wincondition();
	    }
	    else
	    {
	    	//draw(0,0);
	    	mChronometer.stop();
	    	sensorManager.unregisterListener(this, accelerometer);
	      	status = false;
	      	
	    }
	   
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        
        imageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	if (!status){
            	
          	  Button Start = (Button) findViewById(R.id.button1);
          	  //
          	  if (Start.isEnabled()) { 
          	startC(SystemClock.elapsedRealtime());
          	  {
          		Button Pause = (Button) findViewById(R.id.button2);
          	startC(mChronometer.getBase() + (SystemClock.elapsedRealtime()-startpausetime));
  		  Pause.setText("Pause");
          	  }
          	status = true;
            	}
            	return true;
            }
            
 return false;
            }

			
        });
        
		 imageView.setImageBitmap(gameView.getBitmap());
         
}
	
	
	public void wincondition()
	{
		Log.d("Heigh and win condition1", String.valueOf(gameView.y ));
		Log.d("Heigh and win condition2", String.valueOf(gameView.height ));
		if (circle1 && circle2 && circle3 && gameView.y+gameView.movingCircleRadius >= gameView.height)
		{
			
				
			mChronometer.stop();
			
			saveName();
			
			sensorManager.unregisterListener(this, accelerometer);
      	status = false;
		}
	}
	
	private void saveName() {
		// TODO Auto-generated method stub
		LayoutInflater li = LayoutInflater.from(BarrelMainActivity.this);
		final View promptsView = li.inflate(R.layout.notepad_save_as_dialog_layout, null);
		AlertDialog.Builder saveAlert = new AlertDialog.Builder(BarrelMainActivity.this);
		saveAlert.setView(promptsView);
		
	
		saveAlert.setTitle("High Score")
		.setMessage("Enter the user name")
		.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				EditText fname = (EditText) promptsView.findViewById(R.id.saveText1);
				String timer = (String) mChronometer.getText();
				saveFile(fname.getText().toString(),timer);
			}

	})
	.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
			BarrelMainActivity.this.finish();
			
			}
			
	}).create().show();
		
}

	
	private void saveFile(String userName, String timer) {
		// TODO Auto-generated method stub
		try {
			
			
	        if (isSdReadable()) {
	            File fullPath = Environment.getExternalStorageDirectory();
	            File fullPath1 = new File(fullPath,"/userSaveLocation");
	            if(!fullPath1.exists()){
	            	fullPath1.mkdirs();}
	            
	            
	          
	            //Toast.makeText(getApplicationContext(), FileName, Toast.LENGTH_SHORT).show();
	            //Toast.makeText(getApplicationContext(), text.getText().toString(), Toast.LENGTH_LONG).show();
	            File myFile = new File(fullPath1,"UserScores.txt");
	             
	            if (!myFile.exists()) 
	            {    
	                myFile.createNewFile();
	            } 
	            
	            FileOutputStream fOut = new FileOutputStream(myFile,true);	
	            fOut.write((userName+"\t"+timer+"\n").getBytes());
	            Toast.makeText(getApplicationContext(), "File Saved", Toast.LENGTH_SHORT).show();
	            fOut.close();
	           
	            //Toast.makeText(getBaseContext(), "File Saved in External SD", Toast.LENGTH_SHORT).show();
	        }
		}
		
		catch (Exception e) {
	        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
	                .show();
	    }
	}
	
	
	public boolean isSdReadable() {

	    boolean mExternalStorageAvailable = false;
	    try {
	        String state = Environment.getExternalStorageState();

	        if (Environment.MEDIA_MOUNTED.equals(state)) {
	            // We can read and write the media
	            mExternalStorageAvailable = true;
	            Log.i("isSdReadable", "External storage card is readable.");
	        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	            // We can only read the media
	            Log.i("isSdReadable", "External storage card is readable.");
	            mExternalStorageAvailable = true;
	        } else {
	            // Something else is wrong. It may be one of many other
	            // states, but all we need to know is we can neither read nor
	            // write
	            mExternalStorageAvailable = false;
	        }
	    } catch (Exception ex) {

	    }
	    return mExternalStorageAvailable;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		   //setContentView(R.layout.activity_main);
	float x = event.values[0];
	float y = event.values[1];
	float z = event.values[2];
    gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
    gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
    gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
    linear_acceleration[0] = event.values[0] - gravity[0];
    linear_acceleration[1] = event.values[1] - gravity[1];
    linear_acceleration[2] = event.values[2] - gravity[2];
	Log.d("coordinates", Float.toString(x) + Float.toString(y));
	draw(linear_acceleration[0], linear_acceleration[1]);
	
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	
	
	
	
}
