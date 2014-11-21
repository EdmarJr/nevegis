package br.jus.stj.sigeven.entity.db2.sigeven;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "SIGEVEN", name = "TIPO_EVENTO")
public class TipoEventoSGVEN extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1388003050515737429L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SQ_TIPO_EVENTO")
	private Long id;//PK
	
	@Column(name = "NM_TIPO_EVENTO")
	private String nome;
	
	@Column(name = "DS_TIPO_EVENTO")
	private String descricao;
	
	@Column(name = "ST_ATIVO")
	private char ativo = 'S';

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the ativo
	 */
	public char getAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(char ativo) {
		this.ativo = ativo;
	}

	public String getStatusAtivoInativo(){
		if(ativo == 'S'){
			return "Ativo";			
		}else{
			return "Inativo";			
		}
	}
		
}