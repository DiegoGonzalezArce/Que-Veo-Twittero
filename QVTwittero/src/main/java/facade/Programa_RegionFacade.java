/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Local;
import model.Programa_Region;
/**
 *
 * @author yolo
 */
@Local
public interface Programa_RegionFacade {
    public void create(Programa_Region entity);

    public void edit(Programa_Region entity);

    public void remove(Programa_Region entity);

    public Programa_Region find(Object id);

    public List<Programa_Region> findAll();

    public List<Programa_Region> Order(int idRegion);
            
    public int count();
}
