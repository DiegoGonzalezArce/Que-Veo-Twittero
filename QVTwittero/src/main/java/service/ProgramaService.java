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

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

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
import model.Link;
import model.Links;
import model.Node;
import model.Nodes;
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
    /*
    @GET
    @Path("/neo4jProgramas")
    @Consumes({"application/xml", "application/json"})
    public List<List<String>> getProgramasNeo4j(){
        return decodeResult(getNodoNeo4j("Programa"),"nombre");
    }
    @GET
    @Path("/neo4jProgramas/{name}")
    @Consumes({"application/xml", "application/json"})
    public StatementResult getProgramasNeo4j(@PathParam("name") String name){
        return getNodoNeo4j("Programa","nombre",name);
    }
    @GET
    @Path("/neo4jUsuarios")
    @Consumes({"application/xml", "application/json"})
    public StatementResult getUsuariosNeo4j(){
        return getNodoNeo4j("User");
    }
    @GET
    @Path("/neo4jUsuarios/{name}")
    @Consumes({"application/xml", "application/json"})
    public StatementResult getUsuariosNeo4j(@PathParam("name") String name){
        return getNodoNeo4j("User","username",name);
    }
    @GET
    @Path("/neo4jTweets")
    @Consumes({"application/xml", "application/json"})
    public List<List<String>> getTweetsNeo4j(){
        return decodeResult(getRelacionNeo4j("Tweets"),"tweet");
    }
    @GET
    @Path("/neo4jTweets1/{programa}")
    @Consumes({"application/xml", "application/json"})
    public StatementResult getTweets1Neo4j(@PathParam("programa") String programa){
        return getRelacionNeo4j("Tweets","Programa","nombre",programa);
    }
    @GET
    @Path("/neo4jTweets2/{programa}")
    @Consumes({"application/xml", "application/json"})
    public StatementResult getTweets2Neo4j(@PathParam("programa") String programa){
        return getRelacionNeo4j("Tweets","nombre",programa);
    }
    */
    private List<List<String>> decodeResult(StatementResult result,String label){
        List<List<String>> list=new ArrayList<List<String>>();
        while ( result.hasNext() )
        {
            List<String> strings=new ArrayList<String>();
            Record record = result.next();
            //System.out.println(record.get(0).asPath());
            //System.out.println(record.get(0).asPath().start().get(label));
            //System.out.println(record.get(0).asPath().end().asMap());
            //System.out.println(record.get(0).asPath().relationships().iterator().next().asMap());
            /*strings.add(record.get(0).asNode().get(label).asString());
            list.add(strings);*/
        }
        return list;
    }
    @GET
    @Path("/neo4jnodes")
    @Consumes({"application/xml", "application/json"})
    public Nodes nodes(){
        return decodeNodes();
    }
    @GET
    @Path("/neo4jlinks")
    @Consumes({"application/xml", "application/json"})
    public Links links(){
        return decodeLinks();
    }
    private Nodes decodeNodes(){
        StatementResult result=getNodoNeo4j("Programa");
        List<Node> nodos=new ArrayList<Node>();
        while ( result.hasNext() )
        {
            Record record = result.next();
            Node nodo=new Node(record.get(0).asNode().get("nombre").asString(),1);
            nodos.add(nodo);
        }
        result=getNodoNeo4j("User");
        while ( result.hasNext() )
        {
            Record record = result.next();
            Node nodo=new Node(record.get(0).asNode().get("username").asString(),2);
            nodos.add(nodo);
        }
        Nodes nodes=new Nodes(nodos);
        return nodes;
    }
    private Links decodeLinks(){
        StatementResult result=getRelacionNeo4j("Tweets");
        List<Link> links=new ArrayList<Link>();
        while ( result.hasNext() )
        {
            Record record = result.next();
            Link link=new Link(record.get(0).asPath().end().get("nombre").asString(),record.get(0).asPath().start().get("username").asString(),record.get(0).asPath().relationships().iterator().next().get("analisis").asString());
            links.add(link);
        }
        Links results=new Links(links);
        return results;
    }
    private StatementResult getNodosNeo4j(){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH (n) RETURN n");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getRelacionesNeo4j(){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH p=()-->() RETURN p");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getNodoNeo4j(String nodo){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH (n:"+nodo+") RETURN n");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getNodoNeo4j(String nodo,String label,String value){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH (n:"+nodo+") WHERE n."+label+"="+value+" RETURN n");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getRelacionNeo4j(String relacion,String nodo1,String label1,String value1){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH p=()-[r:"+relacion+"]->(b:"+nodo1+") WHERE b."+label1+"="+value1+" RETURN p");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getRelacionNeo4j(String relacion,String label1,String value1){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH p=(a)-[r:"+relacion+"]->(b) WHERE b."+label1+"="+value1+" RETURN p");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getRelacionNeo4j(String relacion,String nodo1,String nodo2,String label1,String value1,String label2,String value2){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH p=(a"+nodo2+")-[r:"+relacion+"]->(b"+nodo1+") WHERE a."+label1+"="+value1+" AND b."+label2+"="+value2+"  RETURN p");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getRelacionNeo4j(String relacion,String label1,String value1,String label2,String value2){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH p=(a)-[r:"+relacion+"]->(b) WHERE a."+label1+"="+value1+" AND b."+label2+"="+value2+"  RETURN p");
        session.close();
        driver.close();
        return result;
    }
    private StatementResult getRelacionNeo4j(String relacion){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        StatementResult result=session.run("MATCH p=()-[r:"+relacion+"]->() RETURN p");
        session.close();
        driver.close();
        return result;
    }
    @GET
    @Path("/neo4jupdate")
    public String poblarNeo4j(){
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
        Session session = driver.session();
        session.run("match (a)-[r]->(b) delete r");
        session.run("match (n) delete n");
        List<String> usuarios = TweetFacadeEJB.findUsers();
        for(String user : usuarios){
            user=user.replaceAll("[^A-Za-z0-9.\\-\\_\\+\\# ]", "");
            session.run( "CREATE (a:User {username:'"+user+"'})");
        }
        List<Programa> programs = programaFacadeEJB.findAll();
        if(programs.isEmpty()){
            return "error";
        }
        for(Programa program : programs){
            String nombre=program.getNombre().replaceAll("[^A-Za-z0-9.\\-\\_\\+\\# ]", "");
            session.run( "CREATE (a:Programa {nombre:'"+nombre+"'})");
            for(Tweet tweet : tweetsPrograma(program.getProgramaId())){
                int menciones=tweet.getMenciones();
                int analisis=tweet.getAnalisis();
                String comentario=tweet.getComment().replaceAll("[^A-Za-z0-9.\\-\\_\\+\\# ]", "");
                String usuario=tweet.getUsername().replaceAll("[^A-Za-z0-9.\\-\\_\\+\\# ]", "");
                session.run("match (a:User) where a.username='"+usuario+"' "
                + "  match (b:Programa) where b.nombre='"+nombre+"' "
                + "  create (a)-[r:Tweets{tweet:'"+comentario+"', menciones:'"+menciones+"', analisis:'"+analisis+"'}]->(b)");
                if(tweet.getAnalisis()<0)session.run("match (a:User) where a.username='"+usuario+"' "
                + "  match (b:Programa) where b.nombre='"+nombre+"' "
                + "  create (a)-[r:TweetNegativo{tweet:'"+comentario+"', menciones:'"+menciones+"'}]->(b)");
                else{
                    if(tweet.getAnalisis()==0)session.run("match (a:User) where a.username='"+usuario+"' "
                + "  match (b:Programa) where b.nombre='"+nombre+"' "
                + "  create (a)-[r:TweetNeutral{tweet:'"+comentario+"', menciones:'"+menciones+"'}]->(b)");
                    else session.run("match (a:User) where a.username='"+usuario+"' "
                + "  match (b:Programa) where b.nombre='"+nombre+"' "
                + "  create (a)-[r:TweetPositivo{tweet:'"+comentario+"', menciones:'"+menciones+"'}]->(b)");
                }
            }
        }
        
        session.close();
        driver.close();
        return "logrado";
        
    }
}



