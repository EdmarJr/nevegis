package br.jus.stj.sigeven.entity.db2.maladir;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.jus.stj.sigeven.entity.EntidadeBase;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "MALADIR", name = "DEST_OCUPACAO")
public class DestOcupacaoMDIR extends EntidadeBase {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 5656857302144729847L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_OCUPACAO")
	private Long seqOcupacao;//PK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_ORGAO_MALA")
	private OrgaoMDIR orgaoMDIR;//FK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_CARGO_MALA")
	private CargoMDIR cargoMDIR;//FK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_DESTINATARIO")
	private DestinatarioMDIR destinatarioMDIR;//FK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_TRATAMENTO")
	private TratamentoMDIR tratamentoMDIR;//FK
		
	@Column(name = "DT_INICIO_GESTAO")
	@Temporal(TemporalType.DATE)
	private Date dtInicioGestao;
	
	@Column(name = "DT_FIM_GESTAO")
	@Temporal(TemporalType.DATE)
	private Date dtFimGestao;
	
	@Column(name = "DESC_LOCAL_OCUP")
	private String descLocalOcup;
	
	@Column(name = "DESC_EMAIL_OCUP")
	private String descEmailOcup;
	
	@Column(name = "NUM_TELEFONE_OCUP")
	private String numTelefoneOcup;
	
	@Column(name = "NUM_FAX_OCUP")
	private String numFAXOcup;
	
	@Column(name = "DESC_CARGOG_ETIQ")
	private String descCargoGEtiq;
	
	@Column(name = "SEQ_LOCAL")
	private Long seqLocal;
	
	/**
	 * @return the seqOcupacao
	 */
	public Long getSeqOcupacao() {
		return seqOcupacao;
	}

	/**
	 * @param seqOcupacao the seqOcupacao to set
	 */
	public void setSeqOcupacao(Long seqOcupacao) {
		this.seqOcupacao = seqOcupacao;
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
	 * @return the cargoMDIR
	 */
	public CargoMDIR getCargoMDIR() {
		return cargoMDIR;
	}

	/**
	 * @param cargoMDIR the cargoMDIR to set
	 */
	public void setCargoMDIR(CargoMDIR cargoMDIR) {
		this.cargoMDIR = cargoMDIR;
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
	 * @return the tratamentoMDIR
	 */
	public TratamentoMDIR getTratamentoMDIR() {
		return tratamentoMDIR;
	}

	/**
	 * @param tratamentoMDIR the tratamentoMDIR to set
	 */
	public void setTratamentoMDIR(TratamentoMDIR tratamentoMDIR) {
		this.tratamentoMDIR = tratamentoMDIR;
	}

	/**
	 * @return the dtInicioGestao
	 */
	public Date getDtInicioGestao() {
		return dtInicioGestao;
	}

	/**
	 * @param dtInicioGestao the dtInicioGestao to set
	 */
	public void setDtInicioGestao(Date dtInicioGestao) {
		this.dtInicioGestao = dtInicioGestao;
	}

	/**
	 * @return the dtFimGestao
	 */
	public Date getDtFimGestao() {
		return dtFimGestao;
	}

	/**
	 * @param dtFimGestao the dtFimGestao to set
	 */
	public void setDtFimGestao(Date dtFimGestao) {
		this.dtFimGestao = dtFimGestao;
	}

	/**
	 * @return the descLocalOcup
	 */
	public String getDescLocalOcup() {
		return descLocalOcup;
	}

	/**
	 * @param descLocalOcup the descLocalOcup to set
	 */
	public void setDescLocalOcup(String descLocalOcup) {
		this.descLocalOcup = descLocalOcup;
	}

	/**
	 * @return the descEmailOcup
	 */
	public String getDescEmailOcup() {
		return descEmailOcup;
	}

	/**
	 * @param descEmailOcup the descEmailOcup to set
	 */
	public void setDescEmailOcup(String descEmailOcup) {
		this.descEmailOcup = descEmailOcup;
	}

	/**
	 * @return the numTelefoneOcup
	 */
	public String getNumTelefoneOcup() {
		return numTelefoneOcup;
	}

	/**
	 * @param numTelefoneOcup the numTelefoneOcup to set
	 */
	public void setNumTelefoneOcup(String numTelefoneOcup) {
		this.numTelefoneOcup = numTelefoneOcup;
	}

	/**
	 * @return the numFAXOcup
	 */
	public String getNumFAXOcup() {
		return numFAXOcup;
	}

	/**
	 * @param numFAXOcup the numFAXOcup to set
	 */
	public void setNumFAXOcup(String numFAXOcup) {
		this.numFAXOcup = numFAXOcup;
	}

	/**
	 * @return the descCargoGEtiq
	 */
	public String getDescCargoGEtiq() {
		return descCargoGEtiq;
	}

	/**
	 * @param descCargoGEtiq the descCargoGEtiq to set
	 */
	public void setDescCargoGEtiq(String descCargoGEtiq) {
		this.descCargoGEtiq = descCargoGEtiq;
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
		return getSeqOcupacao();
	}

}