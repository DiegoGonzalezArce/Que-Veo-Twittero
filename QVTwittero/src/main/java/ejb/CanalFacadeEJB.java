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
import facade.CanalFacade;
import model.Canal;

//EJB de la Tabla Canals
@Stateless
public class CanalFacadeEJB extends AbstractFacade<Canal> implements CanalFacade {

    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public CanalFacadeEJB() {
        super(Canal.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
