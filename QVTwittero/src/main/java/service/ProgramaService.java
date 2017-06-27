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

import Lucene42.src.cl.qvt.nlp.NLPTools;
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
import facade.Programa_RegionFacade;
import facade.RegionFacade;
import facade.TweetFacade;
import facade.Tweet_KeywordFacade;
import java.util.Iterator;
import model.GTorta;
import model.GTortaElem;
import model.Grafo;
import model.Link;
import model.Links;
import model.Node;
import model.Nodes;
import model.Programa_Keyword;
import model.Programa_Region;
import model.Tweet;
import model.Tweet_Keyword;

@Path("/programas")
public class ProgramaService {

    @EJB
    ProgramaFacade programaFacadeEJB;

    @EJB
    RegionFacade regionFacadeEJB;
    
    @EJB
    Programa_RegionFacade Programa_RegionFacadeEJB;
    
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
    @GET
    @Path("/date/{id}")
    public String date(@PathParam("id") Integer id) {
        Tweet tweet=tweetsPrograma(id).get(0);
        NLPTools tools=new NLPTools();
        System.out.println(tools.getDate(tweet.getDate()));
        return tools.getDate(tweet.getDate()).toString();
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
    @Produces({"application/xml", "application/json"})
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
                resultado=resultado+tweet.getMenciones();
            }
        }
        return resultado;
    }

    @GET
    @Path("/negativo/{id}")
    @Produces({"application/xml", "application/json"})
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
                resultado=resultado+tweet.getMenciones();
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
    @Produces({"application/xml", "application/json"})
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
    @Path("/graficoTorta/{id}")
    @Produces("application/json")
    public GTorta grafTorta(@PathParam("id") Integer id){
        Programa programa=programaFacadeEJB.find(id);
        List<GTortaElem> resultados=new ArrayList<GTortaElem>();
        GTortaElem temp=new GTortaElem("Neutrales",(programa.getMenciones()-(programa.getMencionesPositivas()+programa.getMencionesNegativas())));
        resultados.add(temp);
        temp=new GTortaElem("Positivos",programa.getMencionesPositivas());
        resultados.add(temp);
        temp=new GTortaElem("Negativos",programa.getMencionesNegativas());
        resultados.add(temp);
        return new GTorta(resultados);
        
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
    @GET
    @Path("/geo/{id}")
    public List<String> geoLoc(@PathParam("id") Integer id){
        List<Programa_Region> programas=Programa_RegionFacadeEJB.Order(id);
        List<String> resultado=new ArrayList<>();
        for(Programa_Region programa : programas){
            resultado.add(programaFacadeEJB.find(programa.getPrograma_id3()).getNombre());
        }
        return resultado;
      
    }
    @GET
    @Path("/geo/{id}/{id2}")
    public int geoMen(@PathParam("id") Integer id,@PathParam("id2") Integer id2){
        return geoMencionesPrograma(id2,id);
      
    }
    private int geoMencionesPrograma(Integer idPrograma,Integer idRegion){
        int resultado =0;
        List<Tweet> tweets = tweetsPrograma(idPrograma);
        if(tweets.isEmpty()){
            return resultado;
        }
        
        for(Tweet tweet : tweets){
            String region=regionFacadeEJB.findRegion(tweet.getLongitud(), tweet.getLatitud());
            if(region.indexOf(""+idRegion)!=-1){
                resultado=resultado+(tweet.getMenciones());
            }
        }
        return resultado;
        
    }
    @GET
    @Path("/geo/mencionesCreate")
    public String geoMenCreate(){
        List<Programa> programs = programaFacadeEJB.findAll();
        int i=0;
        for(Programa program : programs){
            for(int j=1;j<16;j++){
                i++;
                Programa_Region programa_region=new Programa_Region(i,program.getProgramaId(),j);
                Programa_RegionFacadeEJB.create(programa_region);
            }
        }
        return "logrado";
      
    }
    
    @GET
    @Path("/geo/mencionesUpdate")
    public String geoMenAll(){
        List<Programa_Region> programa_regions = Programa_RegionFacadeEJB.findAll();
        for(Programa_Region programa_region : programa_regions){
            programa_region.setMenciones(geoMencionesPrograma(programa_region.getPrograma_id3(),programa_region.getRegion_id()));
            Programa_RegionFacadeEJB.edit(programa_region);
        }
        return "logrado";
      
    }
    private int geoMenciones(Integer idPrograma,Integer idRegion){
        int resultado =0;
        List<Tweet> tweets = tweetsPrograma(idPrograma);
        if(tweets.isEmpty()){
            return resultado;
        }
        
        for(Tweet tweet : tweets){
            String region=regionFacadeEJB.findRegion(tweet.getLongitud(), tweet.getLatitud());
            if(region.indexOf(""+idRegion)!=-1){
                resultado=resultado+(tweet.getMenciones());
            }
        }
        return resultado;
        
    }
    
    private List<String> geoRanking(Integer idRegion){
        List<String> resultado=new ArrayList<>();
        List<Programa> orden=new ArrayList<>();
        List<Programa> programs = programaFacadeEJB.findAll();
        if(programs.isEmpty()){
            return resultado;
        }
        for(Programa programa : programs){
           int mencion=geoMencionesPrograma(programa.getProgramaId(),idRegion);
           if(orden.isEmpty()){
               orden.add(programa);
           }
           else{
               boolean insertado=false;
               for(int i=orden.size()-1;i>=0;i--){
                   if(geoMencionesPrograma(orden.get(i).getProgramaId(),idRegion)>mencion){
                       if(i==orden.size()-1)orden.add(programa);
                       else orden.add(i,programa);
                       insertado=true;
                       break;
                   }
               }
               if(!insertado){
                   orden.add(0,programa);
               }
           }
           
        }
        for(Programa ordenado : orden){
            String nombre=ordenado.getNombre();
            resultado.add(nombre);
        }
        return resultado;
        
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
    @Produces({"application/xml", "application/json"})
    public Nodes nodes(){
        return decodeNodes();
    }
    @GET
    @Path("/neo4jlinks")
    @Produces({"application/xml", "application/json"})
    public Links links(){
        return decodeLinks();
    }
    @GET
    @Path("/neo4jg")
    @Produces({"application/xml", "application/json"})
    public Grafo grafo(){
        Links liks = decodeLinks();
        Nodes nods = decodeNodes();
        Grafo grafito = new Grafo(liks.getLinks(), nods.getNodes());
        System.out.println("123456");
        return grafito;    
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



