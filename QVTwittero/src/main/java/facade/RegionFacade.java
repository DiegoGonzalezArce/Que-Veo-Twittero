/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Local;
import model.Region;

/**
 *
 * @author yolo
 */
@Local
public interface RegionFacade {
    public void create(Region entity);

    public void edit(Region entity);

    public void remove(Region entity);

    public Region find(Object id);

    public List<Region> findAll();

    public String findRegion(double Long,double Lat);

    public int count();
}
