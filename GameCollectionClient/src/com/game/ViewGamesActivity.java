package com.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Node;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ViewGamesActivity extends ListActivity{
	
	private String[] tArray;
	ArrayList<Game> games = new ArrayList<Game>();;
	private Context itemContext;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		itemContext = this;
	}
	
	public void onResume() {
		super.onResume();
		new DownloadJsonString().execute();
		
		//Since this is a ListActivity we do not need a ListView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tArray);
		setListAdapter(adapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				Intent editActivity = new Intent(itemContext, EditGamesActivity.class);
				editActivity.putExtra("title", games.get(pos).getTitle());
				editActivity.putExtra("ID", String.valueOf(games.get(pos).getID()));
				editActivity.putExtra("platform", games.get(pos).getPlatform());
				editActivity.putExtra("genre", games.get(pos).getGenre());
				startActivity(editActivity);
		     }
					
		});
	}
	
	//Basically the same as from the Reddit App
		private class DownloadJsonString extends AsyncTask<Void, Void, String> {
	    	
	    	ArrayList<String> titles = new ArrayList<String>();
	    	ArrayList<Game> gameArray = new ArrayList<Game>();
	    	String data = "";
	    	
	    	public void execute() {
				data = this.doInBackground();
				this.onPostExecute(data);
	        		
				String resultT [] = {};
				tArray = titles.toArray(resultT);
				
				games = gameArray;
			}

			@Override
			protected String doInBackground(Void... arg0) {
				try {
					String sURL = "http://www.cs480a2.appspot.com/rest/Games/json";
	    			URL rURL = new URL(sURL);
	    			HttpURLConnection conn = (HttpURLConnection)rURL.openConnection();
	    			conn.setRequestMethod("GET");
	    			conn.setDoInput(true);
	    			conn.setUseCaches(false);
	    			conn.connect();
	    			
	    			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    			String line = "";
	    			while((line = br.readLine()) != null)
	    			{
	    				data += line + "\n";
	    			}
	    			
	    			br.close();
	    			conn.disconnect();
	    			
	    			return data;
	    			
	    		} catch (MalformedURLException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        	return data;
			}
			
			protected void onPostExecute(String data) {
				
				try {
					JsonNode root = this.readJson(data);
					
					if (root.path("game").has("title")) {
						JsonNode node = root.path("game");
						if (node.path("visible").textValue().equals("true")) {
							Game newGame = new Game(node.path("title").textValue(), node.path("platform").textValue(), node.path("genre").textValue());
							newGame.setID(Long.valueOf(node.path("ID").textValue()));
							gameArray.add(newGame);
							titles.add(node.path("title").textValue());
						}
					}
					else {
						for(JsonNode node : root.path("game")) {
							if (node.path("visible").textValue().equals("true")) {
								Game newGame = new Game(node.path("title").textValue(), node.path("platform").textValue(), node.path("genre").textValue());
								newGame.setID(Long.valueOf(node.path("ID").textValue()));
								gameArray.add(newGame);
								titles.add(node.path("title").textValue());
							}
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public JsonNode readJson(String json) throws Exception {
				ObjectMapper mapper = new ObjectMapper();
				
				JsonNode rootNode = mapper.readValue(json, JsonNode.class);
				JsonParser jp = mapper.getJsonFactory().createJsonParser(json);
				rootNode = mapper.readTree(jp);
				return rootNode;
		    }
	    	
	    }

}
