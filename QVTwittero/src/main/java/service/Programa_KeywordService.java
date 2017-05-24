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

import facade.Programa_KeywordFacade;
import model.Programa_Keyword;

@Path("/programaskeywords")
public class Programa_KeywordService {

    @EJB
    Programa_KeywordFacade programaKeywordFacadeEJB;

    Logger logger = Logger.getLogger(Programa_KeywordService.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Programa_Keyword> findAll() {
        return programaKeywordFacadeEJB.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Programa_Keyword find(@PathParam("id") Integer id) {
        return programaKeywordFacadeEJB.find(id);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Programa_Keyword entity) {
        programaKeywordFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Programa_Keyword entity) {
        entity.setProgramaKeywordId(id.intValue());
        programaKeywordFacadeEJB.edit(entity);
    }
}
