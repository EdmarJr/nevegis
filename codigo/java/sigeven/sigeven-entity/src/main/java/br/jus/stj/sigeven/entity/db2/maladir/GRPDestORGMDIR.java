package br.jus.stj.sigeven.entity.db2.maladir;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "MALADIR", name = "GRP_DEST_ORG")
public class GRPDestORGMDIR extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3386833573141601985L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_DEST_ORG")
	private Long seqDestORG;//PK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_GRUPO")
	private GrupoMDIR grupoMDIR;//FK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_DESTINATARIO")
	private DestinatarioMDIR destinatarioMDIR;//FK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_ORGAO_MALA")
	private OrgaoMDIR orgaoMDIR;//FK
	
	@Column(name = "SEQ_LOCAL")
	private Long seqLocal;

	/**
	 * @return the seqDestORG
	 */
	public Long getSeqDestORG() {
		return seqDestORG;
	}

	/**
	 * @param seqDestORG the seqDestORG to set
	 */
	public void setSeqDestORG(Long seqDestORG) {
		this.seqDestORG = seqDestORG;
	}

	/**
	 * @return the grupoMDIR
	 */
	public GrupoMDIR getGrupoMDIR() {
		return grupoMDIR;
	}

	/**
	 * @param grupoMDIR the grupoMDIR to set
	 */
	public void setGrupoMDIR(GrupoMDIR grupoMDIR) {
		this.grupoMDIR = grupoMDIR;
	}

	/**
	 * @return the destinatarioMDIR
	 */
	public DestinatarioMDIR getDestinatarioMDIR() {
		return destinatarioMDIR;
	}

	/**
	 * @param destinatarioMDIR the destinatarioMDIR to set
	 */
	public void setDestinatarioMDIR(DestinatarioMDIR destinatarioMDIR) {
		this.destinatarioMDIR = destinatarioMDIR;
	}

	/**
	 * @return the orgaoMDIR
	 */
	public OrgaoMDIR getOrgaoMDIR() {
		return orgaoMDIR;
	}

	/**
	 * @param orgaoMDIR the orgaoMDIR to set
	 */
	public void setOrgaoMDIR(OrgaoMDIR orgaoMDIR) {
		this.orgaoMDIR = orgaoMDIR;
	}

	/**
	 * @return the seqLocal
	 */
	public Long getSeqLocal() {
		return seqLocal;
	}

	/**
	 * @param seqLocal the seqLocal to set
	 */
	public void setSeqLocal(Long seqLocal) {
		this.seqLocal = seqLocal;
	}

	@Override
	public Long getId() {
		return getSeqDestORG();
	}
		
}