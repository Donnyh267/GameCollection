package com.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditGamesActivity extends Activity{
	
	private Button submit;
	private Button delete;
	private EditText title;
	private EditText platform;
	private EditText genre;
	private ObjectMapper mapper;
	
	private String ID;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlayout);
        
        Bundle extras = getIntent().getExtras();
        
        mapper = new ObjectMapper();
        title = (EditText) findViewById(R.id.title);
        platform = (EditText) findViewById(R.id.platform);
        genre = (EditText) findViewById(R.id.genre);
        
        title.setText(extras.getString("title"));
        platform.setText(extras.getString("platform"));
        genre.setText(extras.getString("genre"));
        ID = extras.getString("ID");
        
        //Submit button
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		
				new PostJson().execute(ID);
				finish();
			}
		});
        
        //Delete button
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					String sURL = "http://www.cs480a2.appspot.com/rest/Games/"+ID;
	    			URL rURL = new URL(sURL);
	    			HttpURLConnection conn = (HttpURLConnection)rURL.openConnection();
	    			conn.setDoOutput(true);
	    			conn.setRequestMethod("DELETE");
	    			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
	    			conn.connect();
	    			conn.getInputStream();
	    			
	    			title.setText("");
	    			platform.setText("");
	    			genre.setText("");
	    			title.requestFocus();
	    			finish();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
    }
    
	private class PostJson extends AsyncTask<String, Void, String> {

		public void execute(String param) {
			this.doInBackground(param);
		}
		
		protected String doInBackground(String... params) {

			String t = title.getText().toString();
			String p = platform.getText().toString();
			String g = genre.getText().toString();
			Game newGame = new Game(t, p, g);
			newGame.setID(Long.valueOf(params[0]));
			
			String input = "";
			HttpURLConnection conn = null;
			try {
				input = mapper.writeValueAsString(newGame);
				String sURL = "http://www.cs480a2.appspot.com/rest/Games/overwrite/"+params[0];
    			URL rURL = new URL(sURL);
    			conn = (HttpURLConnection)rURL.openConnection();
    			conn.setRequestMethod("POST");
    			conn.setRequestProperty("Content-Type", "application/json");
    			conn.setRequestProperty("Accept", "application/json");
    			conn.setRequestProperty("Content-Length", Integer.toString(input.length()));
    			conn.setDoInput(true);
    			conn.setDoOutput(true);
    			conn.getOutputStream().write(input.getBytes());
    			conn.getOutputStream().flush();
    			conn.connect();
    			conn.getResponseMessage();
    			
    			title.setText("");
    			platform.setText("");
    			genre.setText("");
    			title.requestFocus();
    			
			} catch (JsonGenerationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				conn.disconnect();
			}
			return null;
			
		}

	}
}