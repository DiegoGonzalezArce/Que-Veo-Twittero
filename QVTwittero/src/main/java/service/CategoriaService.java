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

import facade.Programa_CategoriaFacade;
import model.Programa_Categoria;

@Path("/categorias")
public class CategoriaService {

    @EJB
    CategoriaFacade categoriaFacadeEJB;

    @EJB
    ProgramaFacade programaFacadeEJB;

    @EJB
    Programa_CategoriaFacade programaCategoriaFacadeEJB;

    Logger logger = Logger.getLogger(CategoriaService.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Categoria> findAll() {
        return categoriaFacadeEJB.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Categoria find(@PathParam("id") Integer id) {
        return categoriaFacadeEJB.find(id);
    }

    //Entrega los programas de la categoria {id}.
    @GET
    @Path("{id}/programas")
    @Produces({"application/xml", "application/json"})
    public List<Programa> ProgramaPorCategoria(@PathParam("id") Integer id) {
        List<Programa_Categoria> x = programaCategoriaFacadeEJB.findAll();
        List<Programa> y = programaFacadeEJB.findAll();
        List<Programa> z = new ArrayList<>();

        for (Programa_Categoria elem : x) {
            if (elem.getCategoriaId() == id) {
                int a = elem.getProgramaId();
                for (Programa p : y) {
                    if (p.getProgramaId() == a) {
                        z.add(p);
                    }
                }
            }
        }
        return z;
    }
    @GET
    @Path("/update")
    public String updateCategoria(){
        List<Categoria> categorias = categoriaFacadeEJB.findAll();
        for(Categoria cat : categorias){
            List<Programa> p = ProgramaPorCategoria(cat.getCategoriaId());
            int suma = 0;
            for(Programa pro : p){
                suma = suma + pro.getMenciones();
            }
            cat.setMenciones(suma);
            categoriaFacadeEJB.edit(cat);
        }
        return "logrado";
        
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
