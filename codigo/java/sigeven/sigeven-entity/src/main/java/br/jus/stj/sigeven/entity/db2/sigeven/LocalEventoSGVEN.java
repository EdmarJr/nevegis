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
@Table(schema = "SIGEVEN", name = "LOCAL_EVENTO")
public class LocalEventoSGVEN extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9114536697777212261L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SQ_LOCAL_EVENTO")
	private Long id;//PK
	
	@Column(name = "NM_LOCAL_EVENTO")
	private String nome;

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
		
}