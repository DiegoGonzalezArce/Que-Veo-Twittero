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

import model.Categoria;

//Facade de la tabla Canal
@Local
public interface CategoriaFacade {

    public void create(Categoria entity);

    public void edit(Categoria entity);

    public void remove(Categoria entity);

    public Categoria find(Object id);

    public List<Categoria> findAll();

    public List<Categoria> findRange(int[] range);

    public int count();
}
