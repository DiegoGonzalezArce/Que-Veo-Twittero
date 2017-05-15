/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author nikonegima
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nikonegima
 */

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

import facade.CategoriaFacade;
import model.Categoria;


import facade.ProgramaFacade;
import model.Programa;


@Path("/categorias")
public class CategoriaService {
	

	@EJB 
	CategoriaFacade categoriaFacadeEJB;

	
	@EJB 
	ProgramaFacade programaFacadeEJB;
	
	Logger logger = Logger.getLogger(CanalService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Categoria> findAll(){
		return categoriaFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Categoria find(@PathParam("id") Integer id) {
        return categoriaFacadeEJB.find(id);
    }

    

	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Categoria entity) {
        categoriaFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Categoria entity) {
    	entity.setCategoriaId(id.intValue());
        categoriaFacadeEJB.edit(entity);
    }
	

}

