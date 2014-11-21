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
@Table(schema = "MALADIR", name = "DESTINATARIO")
public class DestinatarioMDIR extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6477771393091888523L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_DESTINATARIO")
	private Long seqDestinatario;//PK
	
	@Column(name = "NOME_DESTINATARIO")
	private String nomeDestinatario;
	
	@Column(name = "DESC_ENDERECO")
	private String descEndereco;
	
	@Column(name = "DESC_CMPLT_ENDER")
	private String descCMPLTEnder;
	
	@Column(name = "DESC_BAIRRO")
	private String descBairro;
	
	@Column(name = "COD_CEP")
	private String codCEP;
	
	@Column(name = "DESC_CIDADE")
	private String descCidade;
	
	@Column(name = "SG_UF")
	private String sgUF;
	
	@Column(name = "NUM_TELEFONE")
	private String numTelefone;
	
	@Column(name = "NOME_CONJUGE")
	private String nomeConjuge;
	
	@Column(name = "DT_ANIVERSARIO")
	@Temporal(TemporalType.DATE)
	private Date dtAniversario;
	
	@Column(name = "DT_ANIV_CONJUGE")
	@Temporal(TemporalType.DATE)
	private Date dtAnivConjuge;
	
	@Column(name = "DT_EVENTO")
	@Temporal(TemporalType.DATE)
	private Date dtEvento;
	
	@Column(name = "DESC_DT_EVENTO")
	private String descDtEvento;
	
	@Column(name = "DESC_EMAIL")
	private String descEmail;
	
	@Column(name = "NUM_FAX_MALA")
	private String numFAXMala;
	
	@Column(name = "DESC_TITULO")
	private String descTitulo;
	
	@Column(name = "COD_VISIBILIDADE")
	private Character codVisibilidade;
	
	@Column(name = "SEQ_LOCAL")
	private Long seqLocal;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_TRATAMENTO")
	private TratamentoMDIR tratamentoMDIR;//FK
	
	@Column(name = "IND_SEXO")
	private Character indSexo;
	
	@Column(name = "IMG_FOTO")
	private byte[] imgFoto;
	
	/**
	 * @return the seqDestinatario
	 */
	public Long getSeqDestinatario() {
		return seqDestinatario;
	}

	/**
	 * @param seqDestinatario the seqDestinatario to set
	 */
	public void setSeqDestinatario(Long seqDestinatario) {
		this.seqDestinatario = seqDestinatario;
	}

	/**
	 * @return the nomeDestinatario
	 */
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	/**
	 * @param nomeDestinatario the nomeDestinatario to set
	 */
	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}

	/**
	 * @return the descEndereco
	 */
	public String getDescEndereco() {
		return descEndereco;
	}

	/**
	 * @param descEndereco the descEndereco to set
	 */
	public void setDescEndereco(String descEndereco) {
		this.descEndereco = descEndereco;
	}

	/**
	 * @return the descCMPLTEnder
	 */
	public String getDescCMPLTEnder() {
		return descCMPLTEnder;
	}

	/**
	 * @param descCMPLTEnder the descCMPLTEnder to set
	 */
	public void setDescCMPLTEnder(String descCMPLTEnder) {
		this.descCMPLTEnder = descCMPLTEnder;
	}

	/**
	 * @return the descBairro
	 */
	public String getDescBairro() {
		return descBairro;
	}

	/**
	 * @param descBairro the descBairro to set
	 */
	public void setDescBairro(String descBairro) {
		this.descBairro = descBairro;
	}

	/**
	 * @return the codCEP
	 */
	public String getCodCEP() {
		return codCEP;
	}

	/**
	 * @param codCEP the codCEP to set
	 */
	public void setCodCEP(String codCEP) {
		this.codCEP = codCEP;
	}

	/**
	 * @return the descCidade
	 */
	public String getDescCidade() {
		return descCidade;
	}

	/**
	 * @param descCidade the descCidade to set
	 */
	public void setDescCidade(String descCidade) {
		this.descCidade = descCidade;
	}

	/**
	 * @return the sgUF
	 */
	public String getSgUF() {
		return sgUF;
	}

	/**
	 * @param sgUF the sgUF to set
	 */
	public void setSgUF(String sgUF) {
		this.sgUF = sgUF;
	}

	/**
	 * @return the numTelefone
	 */
	public String getNumTelefone() {
		return numTelefone;
	}

	/**
	 * @param numTelefone the numTelefone to set
	 */
	public void setNumTelefone(String numTelefone) {
		this.numTelefone = numTelefone;
	}

	/**
	 * @return the nomeConjuge
	 */
	public String getNomeConjuge() {
		return nomeConjuge;
	}

	/**
	 * @param nomeConjuge the nomeConjuge to set
	 */
	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}

	/**
	 * @return the dtAniversario
	 */
	public Date getDtAniversario() {
		return dtAniversario;
	}

	/**
	 * @param dtAniversario the dtAniversario to set
	 */
	public void setDtAniversario(Date dtAniversario) {
		this.dtAniversario = dtAniversario;
	}

	/**
	 * @return the dtAnivConjuge
	 */
	public Date getDtAnivConjuge() {
		return dtAnivConjuge;
	}

	/**
	 * @param dtAnivConjuge the dtAnivConjuge to set
	 */
	public void setDtAnivConjuge(Date dtAnivConjuge) {
		this.dtAnivConjuge = dtAnivConjuge;
	}

	/**
	 * @return the dtEvento
	 */
	public Date getDtEvento() {
		return dtEvento;
	}

	/**
	 * @param dtEvento the dtEvento to set
	 */
	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	/**
	 * @return the descDtEvento
	 */
	public String getDescDtEvento() {
		return descDtEvento;
	}

	/**
	 * @param descDtEvento the descDtEvento to set
	 */
	public void setDescDtEvento(String descDtEvento) {
		this.descDtEvento = descDtEvento;
	}

	/**
	 * @return the descEmail
	 */
	public String getDescEmail() {
		return descEmail;
	}

	/**
	 * @param descEmail the descEmail to set
	 */
	public void setDescEmail(String descEmail) {
		this.descEmail = descEmail;
	}

	/**
	 * @return the numFAXMala
	 */
	public String getNumFAXMala() {
		return numFAXMala;
	}

	/**
	 * @param numFAXMala the numFAXMala to set
	 */
	public void setNumFAXMala(String numFAXMala) {
		this.numFAXMala = numFAXMala;
	}

	/**
	 * @return the descTitulo
	 */
	public String getDescTitulo() {
		return descTitulo;
	}

	/**
	 * @param descTitulo the descTitulo to set
	 */
	public void setDescTitulo(String descTitulo) {
		this.descTitulo = descTitulo;
	}

	/**
	 * @return the codVisibilidade
	 */
	public Character getCodVisibilidade() {
		return codVisibilidade;
	}

	/**
	 * @param codVisibilidade the codVisibilidade to set
	 */
	public void setCodVisibilidade(Character codVisibilidade) {
		this.codVisibilidade = codVisibilidade;
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
	 * @return the indSexo
	 */
	public Character getIndSexo() {
		return indSexo;
	}

	/**
	 * @param indSexo the indSexo to set
	 */
	public void setIndSexo(Character indSexo) {
		this.indSexo = indSexo;
	}

	/**
	 * @return the imgFoto
	 */
	public byte[] getImgFoto() {
		return imgFoto;
	}

	/**
	 * @param imgFoto the imgFoto to set
	 */
	public void setImgFoto(byte[] imgFoto) {
		this.imgFoto = imgFoto;
	}

	@Override
	public Long getId() {
		return getSeqDestinatario();
	}
		
}