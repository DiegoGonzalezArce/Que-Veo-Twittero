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
@Table(name = "Programa_Region")
@NamedQuery(name = "Programa_Region.findAll", query = "SELECT f FROM Programa_Region f")
public class Programa_Region {
    @Id
    @Column(name = "idPrograma_Region", unique = true, nullable = false)
    private int idPrograma_Region;
    
    @Column(name = "programa_id3", nullable = false)
    private int programa_id3;

    @Column(name = "region_id", nullable = false)
    private int region_id;

    @Column(name = "menciones", nullable = false)
    private int menciones;

    public Programa_Region() {
    }

    public Programa_Region(int idPrograma_Region, int programa_id3, int region_id) {
        this.idPrograma_Region = idPrograma_Region;
        this.programa_id3 = programa_id3;
        this.region_id = region_id;
        menciones=0;
    }

    public int getIdPrograma_Region() {
        return idPrograma_Region;
    }

    public void setIdPrograma_Region(int idPrograma_Region) {
        this.idPrograma_Region = idPrograma_Region;
    }

    public int getPrograma_id3() {
        return programa_id3;
    }

    public void setPrograma_id3(int programa_id3) {
        this.programa_id3 = programa_id3;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getMenciones() {
        return menciones;
    }

    public void setMenciones(int menciones) {
        this.menciones = menciones;
    }

    
}
