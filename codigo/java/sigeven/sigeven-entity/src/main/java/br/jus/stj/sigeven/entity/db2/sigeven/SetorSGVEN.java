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
@Table(schema = "SIGEVEN", name = "SETOR")
public class SetorSGVEN extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6199176386512210345L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SQ_SETOR")
	private Long id;//PK
	
	@Column(name = "SG_SETOR")
	private String sgCor;
	
	@Column(name = "NM_SETOR")
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
	 * @return the sgCor
	 */
	public String getSgCor() {
		return sgCor;
	}

	/**
	 * @param sgCor the sgCor to set
	 */
	public void setSgCor(String sgCor) {
		this.sgCor = sgCor;
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
	
	@Override
	public boolean equals(Object other) {
	    return (other instanceof SetorSGVEN) && (id != null)
	        ? id.equals(((SetorSGVEN) other).id)
	        : (other == this);
	}

	@Override
	public int hashCode() {
	    return (id != null)
	        ? (this.getClass().hashCode() + id.hashCode())
	        : super.hashCode();
	}
}