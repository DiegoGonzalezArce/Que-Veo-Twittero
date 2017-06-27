/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 *
 * @author yolo
 */

@Entity
@Table(name = "Region")
@NamedQuery(name = "Region.findAll", query = "SELECT f FROM Region f")
public class Region {
    @Id
    @Column(name = "idRegion", unique = true, nullable = false)
    private int idRegion;
    
    @Column(name = "descripcion", nullable = false, length = 60)
    private String descripcion;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "georef", nullable = false)
    private String georef;

    public Region() {
    }

    public long getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGeoref() {
        return georef;
    }

    public void setGeoref(String georef) {
        this.georef = georef;
    }
    
}
