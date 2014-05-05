package com.android.barelApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class ShowScoreActivity extends ListActivity {

	FileInputStream fileReader;
	BufferedReader br;
	ArrayList<String> fileArrayList;
	String line;
	Button Close;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filelist);
		try {
			Close = (Button) findViewById(R.id.Close);
			fileArrayList = new ArrayList<String>();
			fileReader = new FileInputStream(Environment.getExternalStorageDirectory()+"/userSaveLocation"+"/UserScores.txt");
			 br = new BufferedReader(new InputStreamReader(fileReader));
			 try {
				while((line = br.readLine())!=null)
				 {
					 fileArrayList.add(line);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayAdapter<String> fileList =	new ArrayAdapter<String>(this, R.layout.row, fileArrayList);
		setListAdapter(fileList);
		
		Close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnScreen = new Intent(ShowScoreActivity.this,BarrelMainActivity.class);
				startActivity(returnScreen);
			}
		});
		
		
		//Set<String> UsertimerString = new LinkedHashSet<String>();
		
		
		
	}
	
}
