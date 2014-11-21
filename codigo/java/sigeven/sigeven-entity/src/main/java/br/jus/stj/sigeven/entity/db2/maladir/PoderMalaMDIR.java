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
@Table(schema = "MALADIR", name = "PODER_MALA")
public class PoderMalaMDIR extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5678320912785234362L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_PODER_MALA")
	private Long id;//PK
	
	@Column(name = "DESC_PODER")
	private String nome;
	
	@Column(name = "IND_PUBLICO")
	private String indPublico;
	
	@Column(name = "SEQ_LOCAL")
	private Integer seqLocal;

	@OneToMany(mappedBy = "poderArea")
	private List<OrgaoMDIR> orgaos;

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
	 * @return the indPublico
	 */
	public String getIndPublico() {
		return indPublico;
	}

	/**
	 * @param indPublico the indPublico to set
	 */
	public void setIndPublico(String indPublico) {
		this.indPublico = indPublico;
	}

	/**
	 * @return the seqLocal
	 */
	public Integer getSeqLocal() {
		return seqLocal;
	}

	/**
	 * @param seqLocal the seqLocal to set
	 */
	public void setSeqLocal(Integer seqLocal) {
		this.seqLocal = seqLocal;
	}

	/**
	 * @return the orgaos
	 */
	public List<OrgaoMDIR> getOrgaos() {
		return orgaos;
	}

	/**
	 * @param orgaos the orgaos to set
	 */
	public void setOrgaos(List<OrgaoMDIR> orgaos) {
		this.orgaos = orgaos;
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
