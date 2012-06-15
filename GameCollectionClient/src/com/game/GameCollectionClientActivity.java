package com.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameCollectionClientActivity extends Activity {
	
	private Button addGame;
	private Button viewEdit;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
        
    	//Add a Game
        addGame = (Button) findViewById(R.id.AddGame);
        addGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent addGameActivity = new Intent(v.getContext(), AddGame.class);
				startActivity(addGameActivity);
			}
		});
        
        //View Games Collection
        viewEdit = (Button) findViewById(R.id.ViewEdit);
        viewEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent ListViewActivity = new Intent(v.getContext(), ViewGamesActivity.class);
				startActivity(ListViewActivity);
			}
		});
    }
}