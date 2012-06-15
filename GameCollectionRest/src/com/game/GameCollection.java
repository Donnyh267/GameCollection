package com.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/Games")
public class GameCollection {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Return the list of todos to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Game> getGamesBrowser() {
		List<Game> games = new ArrayList<Game>();
		//games.addAll(GameDAO.instance.games());
		games.addAll( GameDAO.instance.getModel().values() );
		return games; 
	}
	
	// Return the list of todos in json
	@GET
	@Path("/json")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Game> getGamesJ() {
		List<Game> games = new ArrayList<Game>();
		//games.addAll(GameDAO.instance.games());
		games.addAll( GameDAO.instance.getModel().values() );
		return games; 
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = GameDAO.instance.getModel().size();
		return String.valueOf(count);
	}
	
	@DELETE
	@Path("/{ID}")
	public void deleteGame(@PathParam("ID") String ID) {
		/*
		List<Game> games = GameDAO.instance.games();
		Game rGame = new Game();
		Long gID = Long.valueOf(ID);
		for (Game g : games) {
			if (g.getID() == gID ) {
				rGame = g;
				break;
			}
		}
		if (rGame == null) {
			throw new RuntimeException("Delete: Game with " + ID + " not found");
		}
		else {
			rGame.setVisible(false);
			GameDAO.instance.add(rGame);
			//GameDAO.instance.getModel().put(ID, g);
			//GameDAO.instance.update(ID);
		}
		*/
		Game g = GameDAO.instance.getModel().get(Long.valueOf(ID));
		g.setVisible(false);
		GameDAO.instance.getModel().put(Long.valueOf(ID), g);
	}
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newGame(Game g) throws IOException {
		Game newGame = new Game();
		newGame.setGenre(g.getGenre());
		newGame.setPlatform(g.getPlatform());
		newGame.setTitle(g.getTitle());
		Long id = (long) GameDAO.instance.getModel().size();
		do
		{
			newGame.setID((long) id);
			// if key is in the keySet already
			if (GameDAO.instance.getModel().keySet().contains(newGame.getID()))
			{
				newGame.setID(0);	
				id += 1;
			}
			else
			{
				GameDAO.instance.getModel().put(newGame.getID(), newGame);
			}
		} while (newGame.getID() == 0);
		GameDAO.instance.getModel().put(newGame.getID(), newGame);
		//GameDAO.instance.add(newGame);
		return Response.status(201).entity(newGame).build();
	}
	
	@POST
	@Path("/overwrite/{ID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response overWrite(Game newGame, @PathParam("ID") String ID) throws IOException {
		newGame.setID(Long.valueOf(ID));
		//GameDAO.instance.add(newGame);
		GameDAO.instance.getModel().put(Long.valueOf(ID), newGame);
		//GameDAO.instance.update(newGame.getID());
		return Response.status(201).entity(newGame).build();
	}
}
