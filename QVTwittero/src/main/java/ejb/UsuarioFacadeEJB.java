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
import facade.UsuarioFacade;
import model.Usuario;

//EJB de la Tabla Usuario
@Stateless
public class UsuarioFacadeEJB extends AbstractFacade<Usuario> implements UsuarioFacade {

    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public UsuarioFacadeEJB() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}