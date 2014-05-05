package com.android.barelApplication;

/**
 * =============================================================================
 *                       BARREL GAME VIEW
 * =============================================================================
 * 
 * CLASS    :BarrelGameView
 * VER      : 1.0
 * RELEASE  : APR 2014
 * AUTHOR/S :Dinesh Appavoo
 * 			 Srivatsan Varadharajan
 * 			 Basant Khati 
 * 
 * =============================================================================
 */


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Chronometer;
import android.widget.Toast;
import android.os.Vibrator;

	public class BarrelGameView {
	
		
	    int width;
	    int height;
	    int barrelRadius, movingCircleRadius;
	    float x, y;
	    float circle1width, circle1height, circle2width, circle2height, circle3width, circle3height, distance1, distance2, distance3;
	    float acc = (float) 9.81;
	    Bitmap bitmap;
	    private Canvas g;
	    Paint paint = new Paint();
	    Paint Pblack = new Paint();
	    Paint Pred = new Paint();
	    Paint Pgreen = new Paint();
	    Paint Pgray = new Paint();
	    
	    
	    //Winner Identifier variables
	    Vibrator v1;
		int circle1q1c = 0, circle1q2c = 0, circle1q3c = 0, circle1q4c=0;
		int circle1q1a = 0, circle1q2a = 0, circle1q3a = 0, circle1q4a=0;
		int circle2q1c = 0, circle2q2c = 0, circle2q3c = 0, circle2q4c=0;
		int circle2q1a = 0, circle2q2a = 0, circle2q3a = 0, circle2q4a=0;
		int circle3q1c = 0, circle3q2c = 0, circle3q3c = 0, circle3q4c=0;
		int circle3q1a = 0, circle3q2a = 0, circle3q3a = 0, circle3q4a=0;
		int c1Currentquadrantc =0, c1Previousquadrantc =0,c1Currentquadranta =0, c1Previousquadranta =0, Circle1counterc = 0, Circle1countera=0;
		int c2Currentquadrantc =0, c2Previousquadrantc =0,c2Currentquadranta =0, c2Previousquadranta =0, Circle2counterc = 0, Circle2countera=0;
		int c3Currentquadrantc =0, c3Previousquadrantc =0,c3Currentquadranta =0, c3Previousquadranta =0, Circle3counterc = 0, Circle3countera=0;
	
	    
		public void initialize(Activity C)
		{
			Display display = ((Activity) C).getWindowManager().getDefaultDisplay();
			if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){               
		    	Point size = new Point();
		        display.getSize(size);
		        setWidth(size.x);
		        setHeight((int) (size.y*((float)6.5/10)));
		    }else{  
		    	setHeight((int) (display.getHeight()*((float)6/10)));
			    setWidth(display.getWidth());
		    	 /*Point size = new Point();
			        display.getSize(size);
			        width = size.x;
			        height = size.y;*/
		    	
		    }
			setStaticcircleradius(width/15);
			setDynamiccircleradius(width/25);
			x = width/2;
			y = height-movingCircleRadius;
		
		    
		    paint.setColor(Color.DKGRAY);
		    
		    Pred.setColor(Color.RED);
		    
		    Pgreen.setColor(Color.BLUE);
		    Pblack.setColor(Color.WHITE);
		    Pgray.setColor(Color.GRAY);
		    circle1width = getWidth()*((float) 1/2);
		    circle1height = getHeight() *((float) 1/3);
		    circle2width = getWidth()*((float) 1/4);
		    circle2height = getHeight() * ((float) 2/3);
		    circle3width = getWidth()*((float) 3/4);
		    circle3height = getHeight() * ((float) 2/3);
		}
		
		public void setWidth(int w)
		{
			width = w;
			
		}
		
		public int getWidth()
		{
			return width;
		}
		
		public void setHeight(int h)
		{
			height = h;
			
		}
		
		public int getHeight()
		{
			return height;
		}
		
		public void setStaticcircleradius(int str)
		{
			barrelRadius = str;
			
		}
		
		public int getStaticcircleradius()
		{
			return barrelRadius;
		}
		
		public void setDynamiccircleradius(int dyr)
		{
			movingCircleRadius = dyr;
			
		}
		
		public int getDynamiccircleradius()
		{
			return movingCircleRadius;
		}
		
		public void drawBarrels()
		{
		    g.drawCircle(circle1width ,circle1height , barrelRadius, Pblack);
	        g.drawCircle(circle2width , circle2height , barrelRadius, Pblack);
	        g.drawCircle(circle3width, circle3height , barrelRadius, Pblack);	
		}
		
		public void drawBarrel1(Paint pdyn)
		{
			g.drawCircle(circle1width ,circle1height , barrelRadius, pdyn);
		}
		public void drawBarrel2(Paint pdyn)
		{
			g.drawCircle(circle2width ,circle2height , barrelRadius, pdyn);
		}
		public void drawBarrel3(Paint pdyn)
		{
			g.drawCircle(circle3width ,circle3height , barrelRadius, pdyn);
		}
		public void drawBall(float xa, float ya, Paint pdyn)
		{
			x = x - (xa*acc);
		    y = y + (ya*acc);
			g.drawCircle(x, y, movingCircleRadius, pdyn);
		}
		
		public void drawStart()
		{
			g.drawCircle(getWidth()/2, getHeight()-movingCircleRadius, movingCircleRadius, Pblack);
		}
		public void drawAdhoc(float xa, float ya,int r, Paint pdyn)
		{
			g.drawCircle(xa, ya, r, pdyn);
		}
		
		public void setBitmap()
		{
			bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.RGB_565);
		}
		public Bitmap getBitmap()
		{
			return bitmap;
		}
		public void setCanvas()
		{
			g = new Canvas(bitmap);
		    g.drawColor(Color.DKGRAY);
		}
		
		
		
		
		
		
		
		//Winner identifier
	
		
			public boolean c1clash(BarrelGameView uiobj, float xa, float ya)
			{
				float distance1 = (float) Math.sqrt((uiobj.circle1width-(uiobj.x - (xa*uiobj.acc)))*(uiobj.circle1width-(uiobj.x - (xa*uiobj.acc)))+(uiobj.circle1height-(uiobj.y + (ya*uiobj.acc)))*(uiobj.circle1height-(uiobj.y + (ya*uiobj.acc))));
			    
			    if ((distance1 <= uiobj.barrelRadius + uiobj.movingCircleRadius))
			    {                             
			
			    return true;
			    }
				return false;
			}
			
			public boolean c2clash(BarrelGameView uiobj, float xa, float ya)
			{
			    
				float distance2 = (float) Math.sqrt((uiobj.circle2width-(uiobj.x - (xa*uiobj.acc)))*(uiobj.circle2width-(uiobj.x - (xa*uiobj.acc)))+(uiobj.circle2height-(uiobj.y + (ya*uiobj.acc)))*(uiobj.circle2height-(uiobj.y + (ya*uiobj.acc))));
			    
			    if ((distance2 <= uiobj.barrelRadius + uiobj.movingCircleRadius))
			    {                             
			
			    return true;
			    }
				return false;
			}
			
			public boolean c3clash(BarrelGameView uiobj, float xa, float ya)
			{
			    
				float distance3 = (float) Math.sqrt((uiobj.circle3width-(uiobj.x - (xa*uiobj.acc)))*(uiobj.circle3width-(uiobj.x - (xa*uiobj.acc)))+(uiobj.circle3height-(uiobj.y + (ya*uiobj.acc)))*(uiobj.circle3height-(uiobj.y + (ya*uiobj.acc))));
			    
			    if ((distance3 <= uiobj.barrelRadius + uiobj.movingCircleRadius))
			    {                             
			
			    return true;
			    }
				return false;
			}
			
			public void setxy(BarrelGameView uiobj, float x, float y)
			{
				uiobj.x = uiobj.x - (x*uiobj.acc);
				uiobj.y = uiobj.y + (y*uiobj.acc);
			}
			public boolean boundaryclash(BarrelGameView uiobj, float xa, float ya)
			{
			    
				boolean ret=false;
			    if ((uiobj.x- (xa*uiobj.acc) >uiobj.getWidth()-uiobj.movingCircleRadius))
			    {                             
			    ret = true;
			    uiobj.x = uiobj.getWidth()-uiobj.movingCircleRadius;
			    }
			    if ((uiobj.y >uiobj.getHeight()-uiobj.movingCircleRadius))
			    {
			    	ret = true;
			        uiobj.y = uiobj.getHeight()-uiobj.movingCircleRadius;
			    }
			    if (uiobj.x <uiobj.movingCircleRadius)
			    {
			    	ret = true;
			    	uiobj.x = uiobj.movingCircleRadius;
			    }
			    if (uiobj.y <uiobj.movingCircleRadius)
			    {
			    	ret = true;
			    	uiobj.y = uiobj.movingCircleRadius;
			    }
				return ret;
			}
			
			public boolean determineCircle1revolution(BarrelGameView uiobj)
			{
			   	c1Previousquadrantc = c1Currentquadrantc;
			    if ((uiobj.x >= uiobj.circle1width) && (uiobj.y <= uiobj.circle1height + uiobj.barrelRadius))
			    {
			    	circle1q1c = 1;
			    	c1Currentquadrantc = 1;
			    	Log.d("Debug c Q1", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
			    	
			    }
			    if ((uiobj.x >= uiobj.circle1width + uiobj.barrelRadius) && (uiobj.y >= uiobj.circle1height))
			    {
			    	circle1q2c = 1;
			    	c1Currentquadrantc = 2;
			    	Log.d("Debug c Q2", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
			    }
			    if ((uiobj.x <= uiobj.circle1width) && (uiobj.y >= uiobj.circle1height + uiobj.barrelRadius))
			    {
			    	circle1q3c = 1;
			    	c1Currentquadrantc= 3;
			    	Log.d("Debug c Q3", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
			    }
			    if ((uiobj.x >= uiobj.circle1width - uiobj.barrelRadius) && (uiobj.y <= uiobj.circle1height))
			    {
			    	
			    	circle1q4c = 1;
			    	c1Currentquadrantc = 4;
			    	Log.d("Debug c Q4", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
			    }
			    if (c1Previousquadrantc != c1Currentquadrantc)
			    {
			    //	clockwise = true;
			    	Circle1counterc = Circle1counterc + 1;
			    }
			    		c1Previousquadranta = c1Currentquadranta;
			            if ((uiobj.x <= uiobj.circle1width) && (uiobj.y <= uiobj.circle1height - uiobj.barrelRadius))
			            {
			            	
			            	circle1q1a = 1;
			            	c1Currentquadranta = 1;
			            	
			            }
			            if ((uiobj.x >= uiobj.circle1width + uiobj.barrelRadius) && (uiobj.y <= uiobj.circle1height))
			            {
			            	
			            	circle1q2a = 1;
			            	c1Currentquadranta = 2;
			            }
			            if ((uiobj.x >= uiobj.circle1width) && (uiobj.y <= uiobj.circle1height + uiobj.barrelRadius) && (uiobj.y >= uiobj.circle1height))
			            {
			            	
			            	circle1q3a = 1;
			            	c1Currentquadranta = 3;
			            }
			            if ((uiobj.x <= uiobj.circle1width - uiobj.barrelRadius) && (uiobj.y >= uiobj.circle1height))
			            {
			            	
			            	circle1q4a = 1;
			            	c1Currentquadranta = 4;
			            }	    
			            if (c1Previousquadranta != c1Currentquadranta)
			            {
			            	Circle1countera = Circle1countera + 1;
			            }
				
			            if (Circle1counterc ==5 || Circle1countera ==5)
			            {
			            	Circle1counterc = 0;
			            	Circle1countera = 0;
			            	Circle2counterc = 0;
			            	Circle2countera = 0;
			            	Circle3counterc = 0;
			            	Circle3countera = 0;
			            	return true;
			            }
				return false;
			}
			
			public boolean determineCircle2revolution(BarrelGameView uiobj)
			{
			   	c2Previousquadrantc = c2Currentquadrantc;
			    if ((uiobj.x >= uiobj.circle2width) && (uiobj.y <= uiobj.circle2height + uiobj.barrelRadius))
			    {
			    	circle2q1c = 1;
			    	c2Currentquadrantc = 1;
			    	Log.d("Debug c2 Q1", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
			    	
			    }
			    if ((uiobj.x >= uiobj.circle2width + uiobj.barrelRadius) && (uiobj.y >= uiobj.circle2height))
			    {
			    	circle2q2c = 1;
			    	c2Currentquadrantc = 2;
			    	Log.d("Debug c2 Q2", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
			    }
			    if ((uiobj.x <= uiobj.circle2width) && (uiobj.y >= uiobj.circle2height + uiobj.barrelRadius))
			    {
			    	circle2q3c = 1;
			    	c2Currentquadrantc= 3;
			    	Log.d("Debug c2 Q3", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
			    }
			    if ((uiobj.x >= uiobj.circle2width - uiobj.barrelRadius) && (uiobj.y <= uiobj.circle2height))
			    {
			    	
			    	circle2q4c = 1;
			    	c2Currentquadrantc = 4;
			    	Log.d("Debug c2 Q4", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
			    }
			    if (c2Previousquadrantc != c2Currentquadrantc)
			    {
			    //	clockwise = true;
			    	Circle2counterc = Circle2counterc + 1;
			    } 
			c2Previousquadranta = c2Currentquadranta;
			            if ((uiobj.x <= uiobj.circle2width) && (uiobj.y <= uiobj.circle2height - uiobj.barrelRadius))
			            {
			            	
			            	circle2q1a = 1;
			            	c2Currentquadranta = 1;
			            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
			            	
			            }
			            if ((uiobj.x >= uiobj.circle2width + uiobj.barrelRadius) && (uiobj.y <= uiobj.circle2height))
			            {
			            	
			            	circle2q2a = 1;
			            	c2Currentquadranta = 2;
			            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
			            }
			            if ((uiobj.x >= uiobj.circle2width) && (uiobj.y <= uiobj.circle2height + uiobj.barrelRadius) && (uiobj.y >= uiobj.circle2height))
			            {
			            	
			            	circle2q3a = 1;
			            	c2Currentquadranta = 3;
			            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
			            }
			            if ((uiobj.x <= uiobj.circle2width - uiobj.barrelRadius) && (uiobj.y >= uiobj.circle2height))
			            {
			            	
			            	circle2q4a = 1;
			            	c2Currentquadranta = 4;
			            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
			            }	    
			            if (c2Previousquadranta != c2Currentquadranta)
			            {
			            	Circle2countera = Circle2countera + 1;
			            }
				if (Circle2counterc ==5 || Circle2countera ==5)
			    {
			      	Circle1counterc = 0;
			    	Circle1countera = 0;
			    	Circle2counterc = 0;
			    	Circle2countera = 0;
			    	Circle3counterc = 0;
			    	Circle3countera = 0;
			    	return true;
			    }
				return false;
			}
			public boolean determineCircle3revolution(BarrelGameView uiobj)
			{
				c3Previousquadrantc = c3Currentquadrantc;
			    if ((uiobj.x >= uiobj.circle3width) && (uiobj.y <= uiobj.circle3height + uiobj.barrelRadius))
			    {
			    	circle3q1c = 1;
			    	c3Currentquadrantc = 1;
			    	
			    }
			    if ((uiobj.x >= uiobj.circle3width + uiobj.barrelRadius) && (uiobj.y >= uiobj.circle3height))
			    {
			    	circle3q2c = 1;
			    	c3Currentquadrantc = 2;
			    }
			    if ((uiobj.x <= uiobj.circle3width) && (uiobj.y >= uiobj.circle3height + uiobj.barrelRadius))
			    {
			    	circle3q3c = 1;
			    	c3Currentquadrantc= 3;
			    }
			    if ((uiobj.x >= uiobj.circle3width - uiobj.barrelRadius) && (uiobj.y <= uiobj.circle3height))
			    {
			    	
			    	circle3q4c = 1;
			    	c3Currentquadrantc = 4;
			    }
			    if (c3Previousquadrantc != c3Currentquadrantc)
			    {
			    	Circle3counterc = Circle3counterc + 1;
			    } 
			c3Previousquadranta = c3Currentquadranta;
			            if ((uiobj.x <= uiobj.circle3width) && (uiobj.y <= uiobj.circle3height - uiobj.barrelRadius))
			            {
			            	
			            	circle3q1a = 1;
			            	c3Currentquadranta = 1;
			            	
			            }
			            if ((uiobj.x >= uiobj.circle3width + uiobj.barrelRadius) && (uiobj.y <= uiobj.circle3height))
			            {
			            	
			            	circle3q2a = 1;
			            	c3Currentquadranta = 2;
			            }
			            if ((uiobj.x >= uiobj.circle3width) && (uiobj.y <= uiobj.circle3height + uiobj.barrelRadius) && (uiobj.y >= uiobj.circle3height))
			            {
			            	
			            	circle3q3a = 1;
			            	c3Currentquadranta = 3;
			            }
			            if ((uiobj.x <= uiobj.circle3width - uiobj.barrelRadius) && (uiobj.y >= uiobj.circle3height))
			            {
			            	
			            	circle3q4a = 1;
			            	c3Currentquadranta = 4;
			            }	    
			            if (c3Previousquadranta != c3Currentquadranta)
			            {
			            	Circle3countera = Circle3countera + 1;
			            }
			if (Circle3counterc ==5 || Circle3countera ==5)
			{
			  	Circle1counterc = 0;
				Circle1countera = 0;
				Circle2counterc = 0;
				Circle2countera = 0;
				Circle3counterc = 0;
				Circle3countera = 0;
				return true;
			}
			return false;
			}
	
		
		
		
		
		
		
		
		
		
}
