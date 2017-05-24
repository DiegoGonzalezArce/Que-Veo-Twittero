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

import facade.Programa_CategoriaFacade;
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

import model.Programa_Categoria;

@Path("/programascategorias")
public class Programa_CategoriaService {

    @EJB
    Programa_CategoriaFacade programaCategoriaFacadeEJB;

    Logger logger = Logger.getLogger(Programa_CategoriaService.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Programa_Categoria> findAll() {
        return programaCategoriaFacadeEJB.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Programa_Categoria find(@PathParam("id") Integer id) {
        return programaCategoriaFacadeEJB.find(id);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Programa_Categoria entity) {
        programaCategoriaFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Programa_Categoria entity) {
        entity.setProgramaCategoriaId(id.intValue());
        programaCategoriaFacadeEJB.edit(entity);
    }
}
