package br.jus.stj.sigeven.entity.db2.sigeven;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.jus.stj.sigeven.entity.EntidadeBase;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "SIGEVEN", name = "OCUPACAO_PARTICIPANTE_EVENTO")
public class OcupacaoParticipanteEventoSGVEN extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7319123257731626020L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SQ_OCUPACAO_PARTICIPANTE_EVENT")
	private Long id;//PK
	
	@ManyToOne
	@JoinColumn(name = "SQ_PARTICIPANTE_EVENTO")
	private ParticipanteEventoSGVEN participanteEvento;//FK
	
	@ManyToOne
	@JoinColumn(name = "SEQ_CARGO_MALA")
	private CargoMDIR cargo;//FK
	
	@ManyToOne
	@JoinColumn(name = "SEQ_ORGAO_MALA")
	private OrgaoMDIR orgao;//FK
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TRATAMENTO")
	private TratamentoMDIR tratamento;//FK
	
	@Column(name = "NR_TELEFONE_COMERCIAL")
	private String telefoneOcupacao;
	
	@Column(name = "NR_FAX_COMERCIAL")
	private String faxOcupacao;
		
	@Column(name = "DS_ETIQUETA")
	private String etiqueta;

	@Transient
	private Boolean ativo;
	
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
	 * @return the participanteEvento
	 */
	public ParticipanteEventoSGVEN getParticipanteEvento() {
		return participanteEvento;
	}

	/**
	 * @param participanteEvento the participanteEvento to set
	 */
	public void setParticipanteEvento(ParticipanteEventoSGVEN participanteEvento) {
		this.participanteEvento = participanteEvento;
	}

	/**
	 * @return the cargo
	 */
	public CargoMDIR getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(CargoMDIR cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the orgao
	 */
	public OrgaoMDIR getOrgao() {
		return orgao;
	}

	/**
	 * @param orgao the orgao to set
	 */
	public void setOrgao(OrgaoMDIR orgao) {
		this.orgao = orgao;
	}

	/**
	 * @return the tratamento
	 */
	public TratamentoMDIR getTratamento() {
		return tratamento;
	}

	/**
	 * @param tratamento the tratamento to set
	 */
	public void setTratamento(TratamentoMDIR tratamento) {
		this.tratamento = tratamento;
	}

	/**
	 * @return the telefoneOcupacao
	 */
	public String getTelefoneOcupacao() {
		return telefoneOcupacao;
	}

	/**
	 * @param telefoneOcupacao the telefoneOcupacao to set
	 */
	public void setTelefoneOcupacao(String telefoneOcupacao) {
		this.telefoneOcupacao = telefoneOcupacao;
	}

	/**
	 * @return the faxOcupacao
	 */
	public String getFaxOcupacao() {
		return faxOcupacao;
	}

	/**
	 * @param faxOcupacao the faxOcupacao to set
	 */
	public void setFaxOcupacao(String faxOcupacao) {
		this.faxOcupacao = faxOcupacao;
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the ativo
	 */
	public Boolean getAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
			
}