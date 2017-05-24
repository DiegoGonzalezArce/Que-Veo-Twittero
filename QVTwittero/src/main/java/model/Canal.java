/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nikonegima
 */
package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Canal")
@NamedQuery(name = "Canal.findAll", query = "SELECT f FROM Canal f")
public class Canal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_canal", unique = true, nullable = false)
    private int canalId;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public Canal() {
    }

    public int getCanalId() {
        return this.canalId;
    }

    public void setCanalId(int canalId) {
        this.canalId = canalId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescription(String descripcion) {
        this.descripcion = descripcion;
    }
}
