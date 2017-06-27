/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import facade.AbstractFacade;
import facade.Programa_RegionFacade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Programa_Region;
/**
 *
 * @author yolo
 */
@Stateless
public class Programa_RegionFacadeEJB extends AbstractFacade<Programa_Region> implements Programa_RegionFacade{
    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public Programa_RegionFacadeEJB() {
        super(Programa_Region.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    public List<Programa_Region> Order(int idRegion) {
        return em.createQuery(
                "SELECT pr FROM Programa_Region pr WHERE pr.region_id=" + idRegion + " ORDER BY menciones DESC")
                .getResultList();
    }
}
