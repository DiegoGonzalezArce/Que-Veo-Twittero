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
import java.sql.Timestamp;

/**
 * The persistent class for the Programa database table.
 *
 */
@Entity
@Table(name = "Programa")
@NamedQuery(name = "Programa.findAll", query = "SELECT a FROM Programa a")
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_programa", unique = true, nullable = false)
    private int programaId;

    @Column(name = "canal_id", nullable = false)
    private int canalId;

    @Column(name = "usuario_id", nullable = false)
    private int usuarioId;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "descripcion", nullable = true, length = 4000)
    private String descripcion;

    @Column(name = "inicio", nullable = false)
    private Timestamp inicio;

    @Column(name = "termino", nullable = false)
    private Timestamp termino;

    @Column(name = "menciones", nullable = false)
    private int menciones;

    @Column(name = "mencionesPositivas", nullable = false)
    private int mencionesPositivas;

    @Column(name = "mencionesNegativas", nullable = false)
    private int mencionesNegativas;

    public Programa() {
    }

    public int getProgramaId() {
        return programaId;
    }

    public void setProgramaId(int programaId) {
        this.programaId = programaId;
    }

    public int getCanalId() {
        return canalId;
    }

    public void setCanalId(int canalId) {
        this.canalId = canalId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public Timestamp getTermino() {
        return termino;
    }

    public void setTermino(Timestamp termino) {
        this.termino = termino;
    }

    public int getMenciones() {
        return menciones;
    }

    public void setMenciones(int menciones) {
        this.menciones = menciones;
    }

    public int getMencionesPositivas() {
        return mencionesPositivas;
    }

    public void setMencionesPositivas(int mencionesPositivas) {
        this.mencionesPositivas = mencionesPositivas;
    }

    public int getMencionesNegativas() {
        return mencionesNegativas;
    }

    public void setMencionesNegativas(int mencionesNegativas) {
        this.mencionesNegativas = mencionesNegativas;
    }
}
