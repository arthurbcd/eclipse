package fr.ubdx.net.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import fr.ubdx.net.web.Commande;


public class GestionCommandes {

	private HashMap<String,Commande> listeCommande;
	private int idCommande;
	
	public GestionCommandes()
    {
		listeCommande = new HashMap<String, Commande>();
		idCommande = 0;
		if ( INSTANCE == null) {
			INSTANCE = this;
		}
    }
 
    /** Instance unique pr�-initialis�e */
   // private static GestionCommandes INSTANCE = new GestionCommandes();
	 private static GestionCommandes INSTANCE;
     
    /** Point d'acc�s pour l'instance unique du singleton */
    public static GestionCommandes getInstance()
    {  
    	if ( INSTANCE == null) {
    		INSTANCE = new GestionCommandes();
    	}
    	return INSTANCE;
    }
    
    public void addCommande(Commande c) {
    	c.setId(idCommande++);
    	listeCommande.put(c.getId()+"", c);
    }
  

    public List getListeCommande() {
    	ArrayList ret = new ArrayList<>();
    	ret.addAll(listeCommande.values());
    			
    	return ret;
     }
    
    public void delCommande(Commande c) {
    	listeCommande.remove(""+c.getId());
    
    }
    
    public Commande getCommande( String id) {
    	return listeCommande.get(id);
    }
    
}
