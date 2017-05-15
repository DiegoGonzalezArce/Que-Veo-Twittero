/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author nikonegima
 */

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="Categoria")
@NamedQuery(name="Categoria.findAll", query="SELECT f FROM Canal f")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_categoria", unique=true, nullable=false)
	private int categoriaId;
        
        @Column(name="nombre", nullable=false)
        private String nombre;
        
        @Column(name="descripcion", nullable=false)
        private String descripcion;
        
        public Categoria() {
	}

	public int getCategoriaId() {
		return this.categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion(){
		return this.descripcion;
	}

	public void setDescription(String descripcion){
		this.descripcion = descripcion;
	}
}
