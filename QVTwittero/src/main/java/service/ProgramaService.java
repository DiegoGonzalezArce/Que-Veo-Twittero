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

import facade.KeywordFacade;
import model.Keyword;

import facade.Programa_KeywordFacade;
import facade.TweetFacade;
import facade.Tweet_KeywordFacade;
import java.util.Iterator;
import model.Programa_Keyword;
import model.Tweet;
import model.Tweet_Keyword;

@Path("/programas")
public class ProgramaService {

    @EJB
    ProgramaFacade programaFacadeEJB;

    @EJB
    CanalFacade canalFacadeEJB;

    @EJB
    KeywordFacade keywordFacadeEJB;

    @EJB
    Tweet_KeywordFacade Tweet_KeywordFacadeEJB;

    @EJB
    TweetFacade TweetFacadeEJB;

    @EJB
    Programa_KeywordFacade programaKeywordFacadeEJB;

    Logger logger = Logger.getLogger(ProgramaService.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Programa> findAll() {
        return programaFacadeEJB.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Programa find(@PathParam("id") Integer id) {
        return programaFacadeEJB.find(id);
    }

    @GET
    @Path("{id}/keywords")
    @Produces({"application/xml", "application/json"})
    public List<Keyword> KPP(@PathParam("id") Integer id) {
        return KeywordPorPrograma(id);
    }
    
    private List<Keyword> KeywordPorPrograma(Integer id) {
        List<Programa_Keyword> x = programaKeywordFacadeEJB.findAll();
        List<Keyword> y = keywordFacadeEJB.findAll();
        List<Keyword> z = new ArrayList<>();
        for (Programa_Keyword elem : x) {
            if (elem.getProgramaId() == id) {
                int a = elem.getKeywordId();
                for (Keyword k : y) {
                    if (k.getKeywordId() == a) {
                        z.add(k);
                    }
                }
            }
        }
        return z;
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

    @GET
    @Path("/positivo/{id}")
    @Consumes({"application/xml", "application/json"})
    public int pP(@PathParam("id") Integer id) {
        return positivosPrograma(id);
    }

    private int positivosPrograma(int idPrograma) {
        int resultado = 0;
        List<Tweet> tweets = tweetsPrograma(idPrograma);
        if (tweets.isEmpty()) {
            return resultado;
        }
        for (Tweet tweet : tweets) {
            if (tweet.getAnalisis() > 0) {
                resultado++;
            }
        }
        return resultado;
    }

    @GET
    @Path("/negativo/{id}")
    @Consumes({"application/xml", "application/json"})
    public int nP(@PathParam("id") Integer id) {
        return negativosPrograma(id);
    }

    private int negativosPrograma(int idPrograma) {
        int resultado = 0;
        List<Tweet> tweets = tweetsPrograma(idPrograma);
        if (tweets.isEmpty()) {
            return resultado;
        }
        for (Tweet tweet : tweets) {
            if (tweet.getAnalisis() < 0) {
                resultado++;
            }

        }
        return resultado;
    }
    @GET
    @Path("/menciones/{id}")
    @Consumes({"application/xml", "application/json"})
    public int mP(@PathParam("id") Integer id) {
        return mencionesPrograma(id);
    }
    private int mencionesPrograma(int idPrograma) {
        int resultado = 0;
        List<Tweet> tweets = tweetsPrograma(idPrograma);
        if (tweets.isEmpty()) {
            return resultado;
        }
        for (Tweet tweet : tweets) {
            resultado = resultado + tweet.getMenciones();
        }
        return resultado;
    }

    @GET
    @Path("/tweetMencionado/{id}")
    @Consumes({"application/xml", "application/json"})
    public Tweet tM(@PathParam("id") Integer id) {
        return tweetMencionado(id);
    }

    private Tweet tweetMencionado(int idPrograma) {
        Tweet resultado = null;
        List<Tweet> tweets = tweetsPrograma(idPrograma);
        if (tweets.isEmpty()) {
            return resultado;
        }
        for (Tweet tweet : tweets) {
            if(resultado==null)resultado = tweet;
            if(tweet.getMenciones()>resultado.getMenciones())resultado = tweet;
        }
        return resultado;
    }

    private List<Tweet> tweetsPrograma(int idPrograma) {
        List<Tweet> resultado = new ArrayList<Tweet>();
        List<Keyword> keywords = KeywordPorPrograma(idPrograma);
        List<Tweet_Keyword> tweet_keywords = Tweet_KeywordFacadeEJB.findAll();
        if (keywords.isEmpty()) {
            return resultado;
        }
        for (Keyword keyword : keywords) {
            for (Tweet_Keyword tweet_keyword : tweet_keywords) {
                if (tweet_keyword.getKeyword_id() == keyword.getKeywordId()) {
                    Tweet tweet = TweetFacadeEJB.find(tweet_keyword.getTweet_id());
                    resultado.add(tweet);
                }
            }
        }
        return resultado;
    }
    
    
    @GET
    @Path("/mencionesupdate")
    public String updatemenciones(){
        List<Programa> programs = programaFacadeEJB.findAll();
        if(programs.isEmpty()){
            return "error";
        }
        
        for(Programa program : programs){
            program.setMenciones(mencionesPrograma(program.getProgramaId()));
            program.setMencionesPositivas(positivosPrograma(program.getProgramaId()));
            program.setMencionesNegativas(negativosPrograma(program.getProgramaId()));
            programaFacadeEJB.edit(program);
        }
        return "logrado";
        
    }
    @GET
    @Path("/positivosupdate")
    public String updatepositivos(){
        List<Programa> programs = programaFacadeEJB.findAll();
        if(programs.isEmpty()){
            return "error";
        }
        
        for(Programa program : programs){
            program.setMencionesPositivas(positivosPrograma(program.getProgramaId()));
            programaFacadeEJB.edit(program);
        }
        return "logrado";
        
    }
    
    @GET
    @Path("/negativosupdate")
    public String updatenegativos(){
        List<Programa> programs = programaFacadeEJB.findAll();
        if(programs.isEmpty()){
            return "error";
        }
        
        for(Programa program : programs){
            program.setMencionesNegativas(negativosPrograma(program.getProgramaId()));
            programaFacadeEJB.edit(program);
        }
        return "logrado";
        
    }
}



