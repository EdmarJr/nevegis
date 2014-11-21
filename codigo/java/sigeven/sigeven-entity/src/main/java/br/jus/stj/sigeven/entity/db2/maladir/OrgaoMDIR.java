package br.jus.stj.sigeven.entity.db2.maladir;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.jus.stj.sigeven.entity.EntidadeBase;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "MALADIR", name = "ORGAO")
public class OrgaoMDIR extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4579509918913182368L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_ORGAO_MALA")
	private Long id;//PK
	
	@Column(name = "DESC_ORGAO_MALA")
	private String nome;
	
	@Column(name = "SG_ORGAO")
	private String sigla;
	
	@Column(name = "DESC_ENDER_ORGAO")
	private String endereco;
	
	@Column(name = "DESC_COMPL_ORGAO")
	private String complementoEndereco;
	
	@Column(name = "DESC_BAIRRO_ORGAO")
	private String bairro;
	
	@Column(name = "COD_CEP_ORGAO")
	private String cep;
	
	@Column(name = "DESC_CIDADE_ORGAO")
	private String cidade;
	
	@Column(name = "SG_UF_ORGAO")
	private Character uf;
	
	@Column(name = "NUM_TEL_ORGAO")
	private String telefoneComercial;
		
	@Column(name = "NUM_FAX_ORGAO")
	private String fax;
	
	@Column(name = "DESC_EMAIL_ORGAO")
	private String email;
	
	@Column(name = "DESC_URL_ORGAO")
	private String paginaInternet;
	
	@Column(name = "DT_ORGAO")
	@Temporal(TemporalType.DATE)
	private Date dtOrgao;
	
	@Column(name = "DESC_DT_ORGAO")
	private String descDtOrgao;
	
	@Column(name = "IND_PUBLICO")
	private String indPublico;
	
	@Column(name = "SEQ_LOCAL")
	private Integer seqLocal;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_PODER_MALA")
	private PoderMalaMDIR poderArea;//FK

	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_ORGAO_MALA")
	private TipoOrgaoMDIR tipoOrgao;//FK	

	@ManyToOne
	@JoinColumn(name = "SEQ_ESFERA_MALA")
	private EsferaMDIR esfera;//FK
	
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
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the complementoEndereco
	 */
	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	/**
	 * @param complementoEndereco the complementoEndereco to set
	 */
	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the uf
	 */
	public Character getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(Character uf) {
		this.uf = uf;
	}

	/**
	 * @return the telefoneComercial
	 */
	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	/**
	 * @param telefoneComercial the telefoneComercial to set
	 */
	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the paginaInternet
	 */
	public String getPaginaInternet() {
		return paginaInternet;
	}

	/**
	 * @param paginaInternet the paginaInternet to set
	 */
	public void setPaginaInternet(String paginaInternet) {
		this.paginaInternet = paginaInternet;
	}

	/**
	 * @return the dtOrgao
	 */
	public Date getDtOrgao() {
		return dtOrgao;
	}

	/**
	 * @param dtOrgao the dtOrgao to set
	 */
	public void setDtOrgao(Date dtOrgao) {
		this.dtOrgao = dtOrgao;
	}

	/**
	 * @return the descDtOrgao
	 */
	public String getDescDtOrgao() {
		return descDtOrgao;
	}

	/**
	 * @param descDtOrgao the descDtOrgao to set
	 */
	public void setDescDtOrgao(String descDtOrgao) {
		this.descDtOrgao = descDtOrgao;
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
	 * @return the poderArea
	 */
	public PoderMalaMDIR getPoderArea() {
		return poderArea;
	}

	/**
	 * @param poderArea the poderArea to set
	 */
	public void setPoderArea(PoderMalaMDIR poderArea) {
		this.poderArea = poderArea;
	}

	/**
	 * @return the tipoOrgao
	 */
	public TipoOrgaoMDIR getTipoOrgao() {
		return tipoOrgao;
	}

	/**
	 * @param tipoOrgao the tipoOrgao to set
	 */
	public void setTipoOrgao(TipoOrgaoMDIR tipoOrgao) {
		this.tipoOrgao = tipoOrgao;
	}

	/**
	 * @return the esfera
	 */
	public EsferaMDIR getEsfera() {
		return esfera;
	}

	/**
	 * @param esfera the esfera to set
	 */
	public void setEsfera(EsferaMDIR esfera) {
		this.esfera = esfera;
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