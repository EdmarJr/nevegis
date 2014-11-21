package br.jus.stj.sigeven.entity.db2.maladir;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "MALADIR", name = "DEST_OBS")
public class DestOBSMDIR extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3384948301828057850L;

	@Id
	@OneToOne
	@JoinColumn(name = "SEQ_DESTINATARIO", referencedColumnName = "SEQ_DESTINATARIO")
	private DestinatarioMDIR destinatarioMDIR;//PKFK
		
	@Column(name = "SEQ_LOCAL")
	private Long seqLocal;
	
	@Column(name = "DESC_OBS_MALA")
	private String descOBSMala;
	
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

	/**
	 * @return the descOBSMala
	 */
	public String getDescOBSMala() {
		return descOBSMala;
	}

	/**
	 * @param descOBSMala the descOBSMala to set
	 */
	public void setDescOBSMala(String descOBSMala) {
		this.descOBSMala = descOBSMala;
	}

	@Override
	public Long getId() {
		return getDestinatarioMDIR().getSeqDestinatario();
	}
	
}