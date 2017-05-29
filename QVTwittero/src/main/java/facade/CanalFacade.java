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

import model.Canal;

//Facade de la tabla Canal
@Local
public interface CanalFacade {

    public void create(Canal entity);

    public void edit(Canal entity);

    public void remove(Canal entity);

    public Canal find(Object id);

    public List<Canal> findAll();

    public List<Canal> findRange(int[] range);

    public int count();
}
