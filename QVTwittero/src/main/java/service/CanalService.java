/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nikonegima
 */
package service;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.CanalFacade;
import model.Canal;


import facade.ProgramaFacade;


@Path("/canales")
public class CanalService {
	

	@EJB 
	CanalFacade canalFacadeEJB;

	
	@EJB 
	ProgramaFacade programaFacadeEJB;
	
	Logger logger = Logger.getLogger(CanalService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Canal> findAll(){
		return canalFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Canal find(@PathParam("id") Integer id) {
        return canalFacadeEJB.find(id);
    }

    

	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Canal entity) {
        canalFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Canal entity) {
    	entity.setCanalId(id.intValue());
        canalFacadeEJB.edit(entity);
    }
	

}
