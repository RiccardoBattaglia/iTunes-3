/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.itunes.model.Model;
import it.polito.tdp.itunes.model.Track;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPlaylist"
    private Button btnPlaylist; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGenere"
    private ComboBox<String> cmbGenere; // Value injected by FXMLLoader

    @FXML // fx:id="txtDTOT"
    private TextField txtDTOT; // Value injected by FXMLLoader

    @FXML // fx:id="txtMax"
    private TextField txtMax; // Value injected by FXMLLoader

    @FXML // fx:id="txtMin"
    private TextField txtMin; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPlaylist(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String genere = cmbGenere.getValue();
    	
//    	controlli sull'input
    	if (genere==null) {
    		this.txtResult.setText("Inersire un genere.\n");
    		return;
    	}
    	
        String min = txtMin.getText() ;
    	
    	if(min.equals("")) {
    		txtResult.setText("Inserire una durata minima.\n");
    		return ;
    	}
    	
    	double durataMin = 0.0 ;

    	try {
	    	durataMin = Double.parseDouble(min) ;
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero come durata minima.\n");
    		return ;
    	}
    	
    	
        String max = txtMax.getText() ;
    	
    	if(max.equals("")) {
    		txtResult.setText("Inserire una durata massima.\n");
    		return ;
    	}
    	
    	double durataMax = 0.0 ;

    	try {
	    	durataMax = Double.parseDouble(max) ;
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero come durata massima.\n");
    		return ;
    	}
    	
    	if(durataMax<=durataMin) {
    		txtResult.setText("Inserire una durata massima maggiore della durata minima.\n");
    		return ;
    	}
    	
    	double dmin=durataMin*1000;
    	double dmax=durataMax*1000;
    	
    	
//    	creazione grafo
    	this.model.creaGrafo(genere, dmin, dmax);
    	
    	
//    	stampa grafo
    	this.txtResult.setText("Grafo creato.\n");
    	this.txtResult.appendText("Ci sono " + this.model.nVertici() + " vertici\n");
    	this.txtResult.appendText("Ci sono " + this.model.nArchi() + " archi\n\n");
    
    	
        List<Set<Track>> connesse = model.getComponente() ;
        
       
        
        for(Set<Track> s : connesse) {
        	 int flag = 0;
        	 int n =0;
        	for(Track t1 : this.model.getVertici()) {
        			if (s.contains(t1) && flag==0) {
        				 n=this.model.getNPlaylistFromTrack(t1.getTrackId());
        			}

        	}
        	this.model.getVertici();
    	txtResult.appendText("Componente connessa con "+ s.size()+" vertici, inseriti in "+n+" playlist.\n\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPlaylist != null : "fx:id=\"btnPlaylist\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGenere != null : "fx:id=\"cmbGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDTOT != null : "fx:id=\"txtDTOT\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMax != null : "fx:id=\"txtMax\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMin != null : "fx:id=\"txtMin\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	List<String> generi = new LinkedList<>();
    	generi=this.model.getNameGenre();
    	Collections.sort(generi);
    	
    	cmbGenere.getItems().addAll(generi);
    }

}
