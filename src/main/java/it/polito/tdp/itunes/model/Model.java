package it.polito.tdp.itunes.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Track, DefaultEdge> grafo;
	private Map<Integer, Genre> genreMap;
	private List<Genre> genreList;
	private Map<Integer, Track> trackMap;
	private List<Track> trackList;
	
	public Model() {
		this.dao = new ItunesDAO();
		
		this.genreMap=new HashMap<>();
		this.dao = new ItunesDAO();
		this.genreList = this.dao.getAllGenres();
		for(Genre g : genreList) {
			this.genreMap.put(g.getGenreId(), g);
		}
		
		this.trackMap=new HashMap<>();
		this.dao = new ItunesDAO();
		this.trackList = this.dao.getAllTracks();
		for(Track g : trackList) {
			this.trackMap.put(g.getTrackId(), g);
		}
	}
	
	public List<String> getNameGenre(){
		
		List<String> result = new LinkedList<>();
		
		for(Genre g : genreList) {
			result.add(g.getName());
		}
		
		return result;
	}

	public void creaGrafo(String genere, double durataMin, double durataMax) {
		// TODO Auto-generated method stub

	this.grafo = new SimpleGraph<Track, DefaultEdge>(DefaultEdge.class);
		
	// Aggiunta VERTICI 
//	List<Integer> vertici = this.dao.getVertici(nome);
//	Graphs.addAllVertices(this.grafo, vertici);
	
	int genereId=0;
	
	for(Genre g : this.genreList) {
		if(g.getName().equals(genere)) {
			genereId=g.getGenreId();
		}
	}
	
	List<Track> vertici=new LinkedList<>();
	
	for(Track t : trackList) {
		if(t.getGenreId()==genereId && t.getMilliseconds()>durataMin && t.getMilliseconds()<durataMax) {
			vertici.add(t);
		}
	}
	
	Graphs.addAllVertices(this.grafo, vertici);
	
	// Aggiunta ARCHI
	for (Track v1 : vertici) {
		for (Track v2 : vertici) {
			if(this.dao.getNPlaylistFromTrack(v1.getTrackId())==this.dao.getNPlaylistFromTrack(v2.getTrackId()) && v1.getTrackId()<v2.getTrackId()) {
		this.grafo.addEdge(v1,v2);
			}
	}
	}
	
	}

public int nVertici() {
	return this.grafo.vertexSet().size();
}

public int nArchi() {
	return this.grafo.edgeSet().size();
}

public Set<Track> getVertici(){
	
	Set<Track> vertici=this.grafo.vertexSet();
	
	return vertici;
}

public List<Set<Track>> getComponente() {
	ConnectivityInspector<Track, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo) ;
	return ci.connectedSets() ;
}

public Integer getNPlaylistFromTrack(Integer trackId){
	return this.dao.getNPlaylistFromTrack(trackId);
}

public boolean controllaIlMin(String genere,Double min) {
	
	int m=0;
	
int genereId=0;
	
	for(Genre g : this.genreList) {
		if(g.getName().equals(genere)) {
			genereId=g.getGenreId();
		}
	}
	
	for(Track t : trackList) {
		if(t.getGenreId()==genereId) {
			if(t.getMilliseconds()<m) {
				m=t.getMilliseconds();
			}
		}
	}
	
	
	return min>m;
}

	
}
