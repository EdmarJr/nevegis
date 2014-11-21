package br.jus.stj.sigeven.view.controller.participantes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.commons.view.enums.AtivoAposentadoEnum;
import br.jus.stj.commons.view.enums.SexoMasculinoFemininoEnum;
import br.jus.stj.commons.view.enums.SimNaoEnum;
import br.jus.stj.sigeven.business.evento.EventoBean;
import br.jus.stj.sigeven.business.participanteevento.ParticipanteEventoBean;
import br.jus.stj.sigeven.business.to.ParametrosDisponiveisTO;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.to.FiltroPesquisaParticipanteEventoTO;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;
import br.jus.stj.sigeven.view.controller.participantes.utils.FiltroUtils;

@ManagedBean
@ViewScoped
public class ConsultaConvidadoController extends ParametersRequestController {

	private static final String PARAM_PARTICIPANTES_INVISIVEIS = "participantesInvisiveis";
	@EJB
	private EventoBean eventoBean;
	@EJB
	private ParticipanteEventoBean participanteEventoBean;

	private ParametrosDisponiveisTO parametros;
	private FiltroPesquisaParticipanteEventoTO filtro;
	private List<ParticipanteEventoSGVEN> listaResultadoPesquisa;

	private static final String INCLUIR_PARTICIPANTE_XHTML = "incluirParticipante.xhtml";
	private static final String VINCULAR_SETOR_GRUPO_XHTML = "vincularSetorGrupo.xhtml";
	private static final String PESQUISAR_PARTICIPANTE_AVANCADO_XHTML = "pesquisarParticipanteAvancado.xhtml";

	private static final long serialVersionUID = -8456280180533412904L;

	private Boolean modalVisivel;
	private String modalMensagem;
	private EventoSGVEN evento;
	private char sexo;
	private Collection<ParticipanteEventoSGVEN> participantesInvisiveis;

	@PostConstruct
	public void init() {
		participantesInvisiveis = new ArrayList<>();
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		definirEvento(requestMap);
		parametros = participanteEventoBean
				.obterParametrosDisponiveisPesquisa(getEvento());
		filtro = new FiltroPesquisaParticipanteEventoTO();
		if (obterObjetoEmRequestSession("filtroParametroResquest") != null) {
			filtro = (FiltroPesquisaParticipanteEventoTO) obterObjetoEmRequestSession("filtroParametroResquest");
			pesquisar();
		}

		definirVisiveis();

	}

	@SuppressWarnings("unchecked")
	private void definirVisiveis() {
		if (obterObjetoEmRequestSession(PARAM_PARTICIPANTES_INVISIVEIS) != null) {
			participantesInvisiveis = (Collection<ParticipanteEventoSGVEN>) obterObjetoEmRequestSession(PARAM_PARTICIPANTES_INVISIVEIS);
			ArrayList<ParticipanteEventoSGVEN> listaTemp = new ArrayList<ParticipanteEventoSGVEN>(getListaResultadoPesquisa());
			for (ParticipanteEventoSGVEN p : participantesInvisiveis) {
				for (ParticipanteEventoSGVEN participanteLista : listaTemp) {
					if (p.getId().toString()
							.equals(participanteLista.getId().toString())) {
						getListaResultadoPesquisa().remove(participanteLista);
					}
				}
			}
		}
	}

	private void definirEvento(Map<String, Object> requestMap) {
		if (obterObjetoEmRequestSession("eventoView") != null) {
			evento = (EventoSGVEN) obterObjetoEmRequestSession("eventoView");
			evento = eventoBean.recuperarEventoComParticipantes(evento);
			listaResultadoPesquisa = evento.getParticipantes();
			populaObjTela();
		}
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
			return participanteEvento.getListaOcupacaoParticipacao().get(0).getOrgao();
		}
		return null;
	}

	private void populaObjTela() {
		sexo = getEvento().getStatus();
	}

	public void comandoIncluir() {

	}

	public String comandoCancelar() {
		filtro = null;
		return getPaginaAnterior();
	}

	public void comandoLimpar() {
		filtro = new FiltroPesquisaParticipanteEventoTO();
		listaResultadoPesquisa = evento.getParticipantes();
	}

	@Override
	protected void antesDeColocarOsParametrosNoRequest() {
		adicionarObjetoEmRequestSession("filtroParametroResquest", filtro);
		adicionarObjetoEmRequestSession("eventoView", evento);
		adicionarObjetoEmRequestSession(PARAM_PARTICIPANTES_INVISIVEIS,
				participantesInvisiveis);
	}

	public void comandoPesquisar() {
		if (FiltroUtils.isTotalmenteVazio(filtro)) {
			setModalMensagem(getMensagem("msg",
					"msg.msg029.parametronaoinformado"));
			setModalVisivel(Boolean.TRUE);
			return;
		}
		setModalVisivel(false);
		pesquisar();
	}

	private void pesquisar() {
		listaResultadoPesquisa = participanteEventoBean
				.obterParticipantesPorParametro(filtro, evento);
	}

	public String comandoVerificarRegistroDuplicado() {
		definirEventoNoRequest();
		return "../verificarRegistrosDuplicados/verificarRegistrosDuplicados.jsf";
	}

	public String comandoVisualizar(ParticipanteEventoSGVEN participanteEvento) {
		definirParametro(participanteEvento);
		return "visualizarParticipante.xhtml";
	}

	public String comandoAlterar(ParticipanteEventoSGVEN participanteEvento) {
		definirParametro(participanteEvento);
		return "alterarParticipante.xhtml";
	}

	public String comandoExcluir(ParticipanteEventoSGVEN participanteEvento) {
		definirParametro(participanteEvento);
		return "excluirParticipante.xhtml";
	}

	public String comandoManterPoderArea() {
		return "../manterPoderArea/consultarPoderArea.xhtml";
	}

	public void comandoOcultarParticipante(ParticipanteEventoSGVEN participanteEvento) {
		getListaResultadoPesquisa().remove(participanteEvento);
		participantesInvisiveis.add(participanteEvento);
	}

	public void comandoMostrarTodos() {
		getListaResultadoPesquisa().addAll(participantesInvisiveis);
		participantesInvisiveis = new ArrayList<>();
	}

	public List<SexoMasculinoFemininoEnum> getSexoMasculinoFemininoEnum() {
		return Arrays.asList(SexoMasculinoFemininoEnum.values());
	}

	public List<AtivoAposentadoEnum> getAtivoAposentadoEnum() {
		return Arrays.asList(AtivoAposentadoEnum.values());
	}
	
	public List<SimNaoEnum> getSimNaoEnum() {
		return Arrays.asList(SimNaoEnum.values());
	}
	
	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public EventoSGVEN getEvento() {
		return evento;
	}

	public void setEvento(EventoSGVEN evento) {
		this.evento = evento;
	}

	private void definirParametro(ParticipanteEventoSGVEN participanteEvento) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		requestMap.put("participanteEventoParam", participanteEvento);
	}

	private void definirEventoNoRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("eventoView", getEvento());
	}

	public String incluirParticipanteNaoCadastrado() {
		definirEventoNoRequest();
		return INCLUIR_PARTICIPANTE_XHTML;
	}

	public String vincularSetorGrupo() {
		definirEventoNoRequest();
		return VINCULAR_SETOR_GRUPO_XHTML;
	}
	
	public String pesquisarParticipanteAvancado(){
		return PESQUISAR_PARTICIPANTE_AVANCADO_XHTML;
	}
	
	public ParametrosDisponiveisTO getParametros() {
		return parametros;
	}

	public void setParametros(
			ParametrosDisponiveisTO parametros) {
		this.parametros = parametros;
	}

	public FiltroPesquisaParticipanteEventoTO getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroPesquisaParticipanteEventoTO filtro) {
		this.filtro = filtro;
	}

	public List<ParticipanteEventoSGVEN> getListaResultadoPesquisa() {
		return listaResultadoPesquisa;
	}

	public void setListaResultadoPesquisa(
			List<ParticipanteEventoSGVEN> listaResultadoPesquisa) {
		this.listaResultadoPesquisa = listaResultadoPesquisa;
	}

	public Boolean getModalVisivel() {
		return modalVisivel;
	}

	public void setModalVisivel(Boolean modalVisivel) {
		this.modalVisivel = modalVisivel;
	}

	public String getModalMensagem() {
		return modalMensagem;
	}

	public void setModalMensagem(String modalMensagem) {
		this.modalMensagem = modalMensagem;
	}

	@Override
	public String getPaginaAtual() {
		// TODO Auto-generated method stub
		return super.getPaginaAtual();
	}

	public String obterSituacao(char situacao) {
		if (situacao == 'N') {
			return "Aposentado";
		}
		if (situacao == 'S') {
			return "Ativo";
		}
		return "";

	}

}
