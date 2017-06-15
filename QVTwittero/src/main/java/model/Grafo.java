/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author nikonegima
 */
public class Grafo {

        
    private List<Node> nodes;
    private List<Link> links;
    
    public Grafo(){};
    
    
    public Grafo(List<Link> linke, List<Node> nodo){
        this.nodes = nodo;
        this.links = linke;
    }
    
    
    /**
     * @return the nodos
     */
    public List<Node> getNodos() {
        return nodes;
    }

    /**
     * @param nodos the nodos to set
     */
    public void setNodos(List<Node> nodos) {
        this.nodes = nodos;
    }

    /**
     * @return the links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

   

}
