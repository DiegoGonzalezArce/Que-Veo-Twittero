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
import java.util.List;


/**
 * The persistent class for the Programa database table.
 * 
 */
@Entity
@Table(name="Programa")
@NamedQuery(name="Programa.findAll", query="SELECT a FROM Programa a")
public class Programa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_programa", unique=true, nullable=false)
	private int programaId;

	@Id
	@Column(name="canal_id",nullable=false)
	private int canalId;

	@Id
	@Column(name="usuario_id",nullable=false)
	private int usuarioId;

	@Column(name="nombre", nullable=false, length=45)
	private String nombre;

	@Column(name="descripcion", nullable=true)
	private String descripcion;

	@Column(name="inicio", nullable=false)
	private Timestamp inicio;

	@Column(name="termino", nullable=false)
	private Timestamp termino;

	public Programa() {
	}

	public int getProgramaId() {
		return this.programaId;
	}

	public void setProgramaId(int programaId) {
		this.programaId = programaId;
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

	public void setLastName(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getInicio() {
		return this.inicio;
	}

	public void setInicio(Timestamp inicio) {
		this.inicio = inicio;
	}

	public Timestamp getTermino() {
		return this.termino;
	}

	public void setTermino(Timestamp termino) {
		this.termino = termino;
	}

}
