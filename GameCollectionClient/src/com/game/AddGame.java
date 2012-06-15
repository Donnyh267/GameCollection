package com.game;

import java.io.IOException;
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

public class AddGame extends Activity{
	
	private Button submit;
	private EditText title;
	private EditText platform;
	private EditText genre;
	private ObjectMapper mapper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlayout);
        
        mapper = new ObjectMapper();
        title = (EditText) findViewById(R.id.title);
        platform = (EditText) findViewById(R.id.platform);
        genre = (EditText) findViewById(R.id.genre);
        
        //Submit button
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new PostJson().execute();
				finish();
			}
		});
    }
    
	private class PostJson extends AsyncTask<Void, Void, String> {

		public void execute() {
			this.doInBackground();
		}
		
		protected String doInBackground(Void... params) {

			String t = title.getText().toString();
			String p = platform.getText().toString();
			String g = genre.getText().toString();
			Game newGame = new Game(t, p, g);
			
			String input = "";
			HttpURLConnection conn = null;
			try {
				input = mapper.writeValueAsString(newGame);
				String sURL = "http://www.cs480a2.appspot.com/rest/Games/post";
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
