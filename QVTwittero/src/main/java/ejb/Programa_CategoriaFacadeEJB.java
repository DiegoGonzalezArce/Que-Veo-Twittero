/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nikonegima
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.Programa_CategoriaFacade;
import model.Programa_Categoria;

@Stateless
public class Programa_CategoriaFacadeEJB extends AbstractFacade<Programa_Categoria> implements Programa_CategoriaFacade {

    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public Programa_CategoriaFacadeEJB() {
        super(Programa_Categoria.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
