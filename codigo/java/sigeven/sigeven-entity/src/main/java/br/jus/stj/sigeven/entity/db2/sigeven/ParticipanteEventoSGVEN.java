package br.jus.stj.sigeven.entity.db2.sigeven;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import br.jus.stj.sigeven.entity.db2.maladir.DestinatarioMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;

/**
 * @author zainer.silva
 * 
 */
@Entity
@Table(schema = "SIGEVEN", name = "PARTICIPANTE_EVENTO")
public class ParticipanteEventoSGVEN extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5564453115520351201L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SQ_PARTICIPANTE_EVENTO")
	private Long id;// PK

	@ManyToOne
	@JoinColumn(name = "SQ_EVENTO")
	private EventoSGVEN evento;// FK

	@ManyToOne
	@JoinColumn(name = "SQ_SETOR")
	private SetorSGVEN setor;// FK

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SEQ_DESTINATARIO")
	private DestinatarioMDIR destinatario;// FK

	@ManyToOne
	@JoinColumn(name = "SEQ_TRATAMENTO")
	private TratamentoMDIR tratamento;// FK

	@Column(name = "NM_PARTICIPANTE")
	private String nome;

	@Column(name = "NM_TITULO_PREFERENCIAL")
	private String titulo;

	@Column(name = "ST_APOSENTADO")
	private Character situacao = 'S';

	@Column(name = "BI_IMAGEM_FOTO")
	private byte[] foto;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_NASCIMENTO")
	private Date dataNascimento;

	@Column(name = "NM_CONJUGE")
	private String nomeConjuge;

	@Column(name = "ST_CONJUGE_AUTORIDADE")
	private Character conjugeAutoridade = 'N';

	@Column(name = "NM_CARGO_CONJUGE")
	private String nomeCargoConjuge;

	@Column(name = "NM_ORGAO_CONJUGE")
	private String nomeOrgaoConjuge;

	@Column(name = "NM_ENDERECO")
	private String endereco;

	@Column(name = "DS_COMPLEMENTO")
	private String complementoEndereco;

	@Column(name = "NM_BAIRRO")
	private String bairro;

	@Column(name = "NM_CIDADE")
	private String cidade;

	@Column(name = "SG_UF")
	private String uf;

	@Column(name = "NR_CEP")
	private String cep;

	@Column(name = "NR_TELEFONE_RESIDENCIAL")
	private String telefoneResidencial;

	@Column(name = "NR_CELULAR")
	private String telefoneCelular;

	@Column(name = "NM_URL_REDE_SOCIAL")
	private String redeSocial;

	@Column(name = "NM_EMAIL")
	private String email;

	@Column(name = "DS_OBSERVACAO")
	private String observacao;

	@Column(name = "NM_TITULO_PREFERENCIAL_CONJUGE")
	private String tituloConjuge;

	@Column(name = "TP_SEXO")
	private Character sexo = null;

	@Column(name = "CD_FONETICO")
	private String codigoFonetico;

	@OneToMany(mappedBy = "participanteEvento" , cascade = CascadeType.ALL)
	private List<OcupacaoParticipanteEventoSGVEN> listaOcupacaoParticipacao;

	@OneToMany(mappedBy = "participanteEvento" )
	private List<GrupoParticipanteEventoSGVEN> grupoParticipante;

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
	 * @return the evento
	 */
	public EventoSGVEN getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(EventoSGVEN evento) {
		this.evento = evento;
	}

	/**
	 * @return the setor
	 */
	public SetorSGVEN getSetor() {
		return setor;
	}

	/**
	 * @param setor the setor to set
	 */
	public void setSetor(SetorSGVEN setor) {
		this.setor = setor;
	}

	/**
	 * @return the destinatario
	 */
	public DestinatarioMDIR getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(DestinatarioMDIR destinatario) {
		this.destinatario = destinatario;
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
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the situacao
	 */
	public Character getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(Character situacao) {
		this.situacao = situacao;
	}
	
	/**
	 * @return the foto
	 */
	public byte[] getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	/**
	 * @return the dataNascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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
	 * @return the conjugeAutoridade
	 */
	public Character getConjugeAutoridade() {
		return conjugeAutoridade;
	}

	/**
	 * @param conjugeAutoridade the conjugeAutoridade to set
	 */
	public void setConjugeAutoridade(Character conjugeAutoridade) {
		this.conjugeAutoridade = conjugeAutoridade;
	}

	/**
	 * @return the nomeCargoConjuge
	 */
	public String getNomeCargoConjuge() {
		return nomeCargoConjuge;
	}

	/**
	 * @param nomeCargoConjuge the nomeCargoConjuge to set
	 */
	public void setNomeCargoConjuge(String nomeCargoConjuge) {
		this.nomeCargoConjuge = nomeCargoConjuge;
	}

	/**
	 * @return the nomeOrgaoConjuge
	 */
	public String getNomeOrgaoConjuge() {
		return nomeOrgaoConjuge;
	}

	/**
	 * @param nomeOrgaoConjuge the nomeOrgaoConjuge to set
	 */
	public void setNomeOrgaoConjuge(String nomeOrgaoConjuge) {
		this.nomeOrgaoConjuge = nomeOrgaoConjuge;
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
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
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
	 * @return the telefoneResidencial
	 */
	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	/**
	 * @param telefoneResidencial the telefoneResidencial to set
	 */
	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	/**
	 * @return the telefoneCelular
	 */
	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	/**
	 * @param telefoneCelular the telefoneCelular to set
	 */
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	/**
	 * @return the redeSocial
	 */
	public String getRedeSocial() {
		return redeSocial;
	}

	/**
	 * @param redeSocial the redeSocial to set
	 */
	public void setRedeSocial(String redeSocial) {
		this.redeSocial = redeSocial;
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
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the tituloConjuge
	 */
	public String getTituloConjuge() {
		return tituloConjuge;
	}

	/**
	 * @param tituloConjuge the tituloConjuge to set
	 */
	public void setTituloConjuge(String tituloConjuge) {
		this.tituloConjuge = tituloConjuge;
	}

	/**
	 * @return the sexo
	 */
	public Character getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}


	/**
	 * @return the codigoFonetico
	 */
	public String getCodigoFonetico() {
		return codigoFonetico;
	}

	/**
	 * @param codigoFonetico the codigoFonetico to set
	 */
	public void setCodigoFonetico(String codigoFonetico) {
		this.codigoFonetico = codigoFonetico;
	}


	/**
	 * @return the grupoParticipante
	 */
	public List<GrupoParticipanteEventoSGVEN> getGrupoParticipante() {
		return grupoParticipante;
	}

	/**
	 * @param grupoParticipante the grupoParticipante to set
	 */
	public void setGrupoParticipante(
			List<GrupoParticipanteEventoSGVEN> grupoParticipante) {
		this.grupoParticipante = grupoParticipante;
	}

	public List<OcupacaoParticipanteEventoSGVEN> getListaOcupacaoParticipacao() {
		return listaOcupacaoParticipacao;
	}

	public void setListaOcupacaoParticipacao(
			List<OcupacaoParticipanteEventoSGVEN> listaOcupacaoParticipacao) {
		this.listaOcupacaoParticipacao = listaOcupacaoParticipacao;
	}
	
	
	
}