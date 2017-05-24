/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import facade.TweetFacade;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Programa;
import model.Tweet;

/**
 *
 * @author yolo
 */

@Path("/tweets")
public class TweetService {
    @EJB 
    TweetFacade TweetFacadeEJB;

    Logger logger = Logger.getLogger(TweetService.class.getName());
	
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Tweet> findAll(){
    	return TweetFacadeEJB.findAll();
    }

	
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Tweet find(@PathParam("id") Integer id) {
        return TweetFacadeEJB.find(id);
    }

    

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Tweet entity) {
        TweetFacadeEJB.create(entity);
    }

}
