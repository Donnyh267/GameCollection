package com.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum GameDAO {
	instance;
	
	/*
	public List<Game> games() {
		EntityManager em = EMF.get().createEntityManager();
		// Read the existing entries
		Query q = em.createQuery("select g from Game m");
		List<Game> results = q.getResultList();
		return results;
	}
	
	public void add(Game newGame) {
		synchronized (this) {
			EntityManager em = EMF.get().createEntityManager();
			em.persist(newGame);
			em.close();
		}
	}
	
	
	public void update(String ID, Game newVer) {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		trans.begin();
		Game modGame = em.find(Game.class, ID);
		
		em.persist(newVer);
		trans.commit();
	}
	*/

	private Map<Long, Game> contentProvider = new HashMap<Long, Game>();
	
	private GameDAO() {
		Game game = new Game();
		contentProvider.put(game.getID(), game);
	}
	
	public Map<Long, Game> getModel() {
		return contentProvider;
	}
}
