package br.jus.stj.sigeven.view.controller.registros;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.evento.EventoBean;
import br.jus.stj.sigeven.business.participanteevento.ParticipanteEventoBean;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class VerificarRegistrosDuplicadosController extends
		ParametersRequestController {

	private static final String PARTICIPANTE_EVENTO = "participanteEventoParam";
	private static final String NOME_PARAMETRO_EVENTO_REQUEST = "eventoView";

	@EJB
	private ParticipanteEventoBean participanteEventoBean;
	@EJB
	private EventoBean eventoBean;
	private EventoSGVEN evento;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7203649614498507897L;

	private List<ParticipanteEventoSGVEN> registrosDuplicados;

	@PostConstruct
	public void inicializarMBean() {

		evento = obterEventoRequest();
//		registrosDuplicados = obterRegistrosDuplicados(evento);
	}

	private EventoSGVEN obterEventoRequest() {
		EventoSGVEN eventoRetorno = null;
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		if (requestMap.get(NOME_PARAMETRO_EVENTO_REQUEST) != null) {
			eventoRetorno = eventoBean
					.recuperarEventoComParticipantes((EventoSGVEN) requestMap
							.get(NOME_PARAMETRO_EVENTO_REQUEST));
		}

		return eventoRetorno;
	}


	public String comandoVoltar() {
		colocarEventoNoRequest(evento);
		return getPaginaAnterior();
	}

	public String comandoAlterar(ParticipanteEventoSGVEN participante) {
		colocarParticipanteEventoNoRequest(participante);
		return "../manterListaConvidado/alterarParticipante.xhtml";
	}

	public String comandoExcluir(ParticipanteEventoSGVEN participante) {
		colocarParticipanteEventoNoRequest(participante);
		return "../manterListaConvidado/excluirParticipante.xhtml";
	}

	public String comandoVisualizar(ParticipanteEventoSGVEN participante) {
		colocarParticipanteEventoNoRequest(participante);
		return "../manterListaConvidado/visualizarParticipante.xhtml";

	}

	protected void colocarEventoNoRequest(EventoSGVEN evento) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(NOME_PARAMETRO_EVENTO_REQUEST, evento);
	}
	protected void colocarParticipanteEventoNoRequest(ParticipanteEventoSGVEN participanteEvento) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(PARTICIPANTE_EVENTO, participanteEvento);
	}

	public List<ParticipanteEventoSGVEN> obterRegistrosDuplicados(EventoSGVEN evento) {
		return participanteEventoBean
				.obterParticipantesComNomesFoneticamenteIguais(evento
						.getParticipantes());

	}

	public CargoMDIR obterCargoMaiorPrecedencia(
			ParticipanteEventoSGVEN participanteEvento) {
		if (participanteEvento.getListaOcupacaoParticipacao() != null
				&& participanteEvento.getListaOcupacaoParticipacao().size() != 0) {

			return participanteEvento.getListaOcupacaoParticipacao().get(0)
					.getCargo();
		}
		return null;
	}

	public OrgaoMDIR obterOrgaoMaiorPrecedencia(
			ParticipanteEventoSGVEN participanteEvento) {
		if (participanteEvento.getListaOcupacaoParticipacao() != null
				&& participanteEvento.getListaOcupacaoParticipacao().size() != 0) {
			return participanteEvento.getListaOcupacaoParticipacao().get(0)
					.getOrgao();
		}
		return null;
	}

	// ------------------------GETTERS / SETTERS--------------------------------

	public List<ParticipanteEventoSGVEN> getRegistrosDuplicados() {
		return registrosDuplicados;
	}

	public void setRegistrosDuplicados(
			List<ParticipanteEventoSGVEN> registrosDuplicados) {
		this.registrosDuplicados = registrosDuplicados;
	}

}
