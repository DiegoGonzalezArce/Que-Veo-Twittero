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


@Entity
@Table(name="Programa_Categoria")
@NamedQuery(name="Programa_Categoria.findAll", query="SELECT f FROM Programa_Categoria f")
public class Programa_Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_programa_categoria", unique=true, nullable=false)
	private int programacategoriaId;
        
        @Id
        @Column(name="programa_id", nullable=false)
        private int programaId;
        
        @Id
        @Column(name="categoria_id", nullable=false)
        private int categoriaId;
        
        public Programa_Categoria() {
	}

	public int getProgramaCategoriaId() {
		return this.programacategoriaId;
	}

	public void setProgramaCategoriaId(int Id) {
		this.programacategoriaId = Id;
	}

	public int getProgramaId() {
		return this.programaId;
	}

	public void setProgramaId(int Id) {
		this.programaId = Id;
	}

	public int getCategoriaId(){
		return this.categoriaId;
	}

	public void setCategoriaId(int Id){
		this.categoriaId = Id;
	}
}
