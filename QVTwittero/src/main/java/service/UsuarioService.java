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

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import facade.ProgramaFacade;
import facade.UsuarioFacade;
import model.Usuario;

@Path("/usuarios")
public class UsuarioService {

    @EJB
    UsuarioFacade usuarioFacadeEJB;

    @EJB
    ProgramaFacade programaFacadeEJB;

    Logger logger = Logger.getLogger(UsuarioService.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Usuario> findAll() {
        return usuarioFacadeEJB.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Usuario find(@PathParam("id") Integer id) {
        return usuarioFacadeEJB.find(id);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Usuario entity) {
        usuarioFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
        entity.setUsuarioId(id.intValue());
        usuarioFacadeEJB.edit(entity);
    }
}
