/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nikonegima
 */
package facade;

import java.util.List;

import javax.ejb.Local;

import model.Usuario;

//Facade de la tabla Usuario
@Local
public interface UsuarioFacade {

    public void create(Usuario entity);

    public void edit(Usuario entity);

    public void remove(Usuario entity);

    public Usuario find(Object id);

    public List<Usuario> findAll();

    public List<Usuario> findRange(int[] range);

    public int count();
}

