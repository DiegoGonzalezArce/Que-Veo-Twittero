/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import facade.AbstractFacade;
import facade.RegionFacade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Region;
/**
 *
 * @author yolo
 */
@Stateless
public class RegionFacadeEJB extends AbstractFacade<Region> implements RegionFacade{
    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public RegionFacadeEJB() {
        super(Region.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    @Override
    public String findRegion(double Long, double Lat){
        String result="0";
        try
        {
            // Step 1: "Load" the JDBC driver
            Class.forName("com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource"); 
            // Step 2: Establish the connection to the database 
            String url = "jdbc:mysql://:3306/qvt-db"; 
            Connection conn = DriverManager.getConnection(url,"root","root"); 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select ST_Contains(Region.georef, ST_GeomFromText('POINT("+Long+" "+Lat+")')), idRegion From `qvt-db`.Region;");            
            while (rs.next()) {
                if(rs.getString(1).indexOf("1")!=-1){
                    result = rs.getString("idRegion");
                }
            }
        }
        catch (Exception e)
        {
          result="0";
        
          System.err.println("D'oh! Got an exception!"); 
          System.err.println(e.getMessage()); 
        } 
        return result;
    }
}
