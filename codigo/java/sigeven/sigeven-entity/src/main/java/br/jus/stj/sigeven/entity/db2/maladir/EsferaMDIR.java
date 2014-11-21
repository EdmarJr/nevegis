package br.jus.stj.sigeven.entity.db2.maladir;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "MALADIR", name = "ESFERA")
public class EsferaMDIR extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1245513475196897733L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_ESFERA_MALA")
	private Long id;//PK
	
	@Column(name = "DESC_ESFERA_MALA")
	private String nome;

	@OneToMany(mappedBy = "orgao")
	private List<OcupacaoParticipanteEventoSGVEN> relacionamentosParticipanteOrgaoCargo;

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
	 * @return the relacionamentosParticipanteOrgaoCargo
	 */
	public List<OcupacaoParticipanteEventoSGVEN> getRelacionamentosParticipanteOrgaoCargo() {
		return relacionamentosParticipanteOrgaoCargo;
	}

	/**
	 * @param relacionamentosParticipanteOrgaoCargo the relacionamentosParticipanteOrgaoCargo to set
	 */
	public void setRelacionamentosParticipanteOrgaoCargo(
			List<OcupacaoParticipanteEventoSGVEN> relacionamentosParticipanteOrgaoCargo) {
		this.relacionamentosParticipanteOrgaoCargo = relacionamentosParticipanteOrgaoCargo;
	}
	
}