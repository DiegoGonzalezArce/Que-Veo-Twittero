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

import facade.ProgramaFacade;
import model.Programa;

import facade.CanalFacade;
import model.Canal;

@Path("/programas")
public class ProgramaService {
	
	
	@EJB 
	ProgramaFacade programaFacadeEJB;
	
	@EJB 
	CanalFacade canalFacadeEJB;

	Logger logger = Logger.getLogger(ProgramaService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Programa> findAll(){
		return programaFacadeEJB.findAll();
	}

	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Programa find(@PathParam("id") Integer id) {
        return programaFacadeEJB.find(id);
    }


	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Programa entity) {
        programaFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Programa entity) {
    	entity.setProgramaId(id.intValue());
        programaFacadeEJB.edit(entity);
    }
	

}

