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

import facade.KeywordFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import model.Keyword;

@Path("/keywords")
public class KeywordService {

    @EJB
    KeywordFacade keywordFacadeEJB;

    Logger logger = Logger.getLogger(KeywordService.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Keyword> findAll() {
        return keywordFacadeEJB.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Keyword find(@PathParam("id") Integer id) {
        return keywordFacadeEJB.find(id);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Keyword entity) {
        keywordFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Keyword entity) {
        entity.setKeywordId(id.intValue());
        keywordFacadeEJB.edit(entity);
    }

    @GET
    @Path("/export")
    public String export() {
        try {
            PrintWriter writer = new PrintWriter("QVT/words.dat", "UTF-8");
            List<Keyword> keywords = keywordFacadeEJB.findAll();
            for (Keyword keyword : keywords) {
                writer.println(keyword.getKeyword());
            }
            writer.close();
        } catch (IOException e) {
            // do something
            return "error de IO";
        }
        return "logrado";
    }
}
