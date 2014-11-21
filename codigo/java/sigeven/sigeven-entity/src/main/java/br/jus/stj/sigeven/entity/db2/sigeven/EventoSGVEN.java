package br.jus.stj.sigeven.entity.db2.sigeven;

import java.text.SimpleDateFormat;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.jus.stj.sigeven.entity.EntidadeBase;
import br.jus.stj.sigeven.entity.db2.db2sa.Usuario;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "SIGEVEN", name = "EVENTO")
public class EventoSGVEN extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1728941724025130342L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SQ_EVENTO")
	private Long id;//PK
	
	@OneToMany(mappedBy = "eventoSGVEN", cascade=CascadeType.ALL)
	private List<ServicoPrestadoEventoSGVEN> servicoPrestadoEventoSGVEN;
	
	@OneToOne
	@JoinColumn( name = "SEQ_USUARIO_RESPONSAVEL" )
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn( name = "SQ_MESTRE_CERIMONIA")
	private MestreCerimoniaSGVEN mestreCerimoniaSGVEN;
	
	@ManyToOne
	@JoinColumn(name = "SQ_TIPO_EVENTO")
	private TipoEventoSGVEN tipoEvento;//FK
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SQ_LOCAL_EVENTO")
	private LocalEventoSGVEN local;//FK
	
	@Column(name = "NM_EVENTO" )
	private String nome;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_INICIO_EVENTO")
	private Date dataInicio;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_FIM_EVENTO")
	private Date dataFim;
	
	@Column(name = "ST_ATIVO")
	private Character status = 'S';
	
	@Temporal(value = TemporalType.TIME)
	@Column(name = "HR_EVENTO")
	private Date hora;
	
	@Column(name = "ST_INTERPRETE_LIBRAS")
	private Character traducaoLibra = 'N';
	
	@Column(name = "ST_TAQUIGRAFO")
	private Character taquigrafia = 'N';
	
	@Column(name = "VR_CUSTO_FINAL_EVENTO")
	private double custoFinal;
	
	@Column(name = "DS_OBSERVACAO")
	private String observacao;
	
	@OneToMany(mappedBy="evento", cascade=CascadeType.REMOVE)
	private List<ParticipanteEventoSGVEN> participantes;

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
	 * @return the tipoEvento
	 */
	public TipoEventoSGVEN getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(TipoEventoSGVEN tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	/**
	 * @return the local
	 */
	public LocalEventoSGVEN getLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(LocalEventoSGVEN local) {
		this.local = local;
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
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the status
	 */
	public Character getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Character status) {
		this.status = status;
	}

	/**
	 * @return the hora
	 */
	public Date getHora() {
		return hora;
	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(Date hora) {
		this.hora = hora;
	}

	/**
	 * @return the traducaoLibra
	 */
	public Character getTraducaoLibra() {
		return traducaoLibra;
	}

	/**
	 * @param traducaoLibra the traducaoLibra to set
	 */
	public void setTraducaoLibra(Character traducaoLibra) {
		this.traducaoLibra = traducaoLibra;
	}

	/**
	 * @return the taquigrafia
	 */
	public Character getTaquigrafia() {
		return taquigrafia;
	}

	/**
	 * @param taquigrafia the taquigrafia to set
	 */
	public void setTaquigrafia(Character taquigrafia) {
		this.taquigrafia = taquigrafia;
	}


	/**
	 * @return the custoFinal
	 */
	public double getCustoFinal() {		
		return custoFinal;
	}

	/**
	 * @param custoFinal the custoFinal to set
	 */
	public void setCustoFinal(double custoFinal) {
		this.custoFinal = custoFinal;
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
	 * @return the participantes
	 */
	public List<ParticipanteEventoSGVEN> getParticipantes() {
		return participantes;
	}

	/**
	 * @param participantes the participantes to set
	 */
	public void setParticipantes(List<ParticipanteEventoSGVEN> participantes) {
		this.participantes = participantes;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<ServicoPrestadoEventoSGVEN> getServicoPrestadoEventoSGVEN() {
		return servicoPrestadoEventoSGVEN;
	}

	public void setServicoPrestadoEventoSGVEN(
			List<ServicoPrestadoEventoSGVEN> servicoPrestadoEventoSGVEN) {
		this.servicoPrestadoEventoSGVEN = servicoPrestadoEventoSGVEN;
	}

	public MestreCerimoniaSGVEN getMestreCerimoniaSGVEN() {
		return mestreCerimoniaSGVEN;
	}

	public void setMestreCerimoniaSGVEN(MestreCerimoniaSGVEN mestreCerimoniaSGVEN) {
		this.mestreCerimoniaSGVEN = mestreCerimoniaSGVEN;
	}

	@Transient
	public String getDataInicioFormatada() {
		if (dataInicio != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(getDataInicio());
		} else {
			return "";
		}
	}

	@Transient
	public String getDataFimFormatada() {
		String a = "a ";
		if (dataFim != null) {
			return a + new SimpleDateFormat("dd/MM/yyyy").format(getDataFim());
		} else {
			return "";
		}
	}
	
	public String getStatusAtivoInativo(){
		if(status == 'S'){
			return "Ativo";			
		}else{
			return "Inativo";			
		}
	}
				
}