package br.jus.stj.sigeven.view.controller.participantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.cargo.CargoBean;
import br.jus.stj.sigeven.business.participanteevento.ParticipanteEventoBean;
import br.jus.stj.sigeven.business.tratamento.TratamentoBean;
import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class ParticipanteController extends ParametersRequestController {

	private static final String VINCULAR_ORGAOS_CARGOS_XHTML = "vincularOrgaosCargos.xhtml";
	private static final String EDITAR_ORGAOS_CARGOS_XHTML = "alterarOrgaosCargos.xhtml";
	private static final String EXCLUIR_ORGAOS_CARGOS_XHTML = "excluirOrgaosCargos.xhtml";
	private static final String VISUALIZAR_ORGAOS_CARGOS_XHTML = "visualizarOrgaosCargos.xhtml";
	private static final String EVENTO_PARAMETRO = "eventoView";

	private static final String PARTICIPANTE_EVENTO = "participanteEventoParam";
	private static final String PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO = "participanteOrgaoCargoEventoParam";

	private static final long serialVersionUID = -8456280180533412904L;

	private ParticipanteEventoSGVEN participanteEvento;
	
	
	@EJB
	protected CargoBean cargoBean;
	@EJB
	protected TratamentoBean tratamentoBean;
	@EJB
	protected ParticipanteEventoBean participanteEventoBean;

	private Boolean renderizarOpcoesConjuge;

	private Boolean modalSucessoVisivel;

	private EventoSGVEN evento;
	private List<TratamentoMDIR> tratamentosDisponiveis;

	@PostConstruct
	public void init() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		
		if(requestMap.get(PARTICIPANTE_EVENTO) == null) {
			if (requestMap.get(EVENTO_PARAMETRO) != null) {
				evento = (EventoSGVEN) requestMap.get(EVENTO_PARAMETRO);
			}
			participanteEvento = new ParticipanteEventoSGVEN();
			participanteEvento.setEvento(evento);
			participanteEvento.setListaOcupacaoParticipacao(new ArrayList<OcupacaoParticipanteEventoSGVEN>());
			setRenderizarOpcoesConjuge(Boolean.FALSE);
			return;
		} else {
			participanteEvento = (ParticipanteEventoSGVEN) requestMap.get(PARTICIPANTE_EVENTO);
			evento = participanteEvento.getEvento();
			definirRenderizacaoOpcoesConjuge(participanteEvento.getConjugeAutoridade());
		}

		
		
		
		definirTratamentosDisponiveis();
	}

	private void definirTratamentosDisponiveis() {
		tratamentosDisponiveis = tratamentoBean
				.obterTratamentosVinculadosAoEvento(evento);
	}

	public List<TratamentoMDIR> obterTratamentosDisponiveis() {
		return tratamentosDisponiveis;
	}

	public void definirRenderizacaoOpcoesConjuge(char conjugeAutoridade) {
		renderizarOpcoesConjuge = Boolean.FALSE;
		if (new String(new char[] { conjugeAutoridade })
				.equals("s")) {
			renderizarOpcoesConjuge = Boolean.TRUE;
		}
	}
	
	public String comandoLimpar() {
		colocarEventoNoRequest();
		return getPaginaAtual();
	}
	
	public String comandoVincularCargoOrgao(){
		OcupacaoParticipanteEventoSGVEN parametro = new OcupacaoParticipanteEventoSGVEN();
		parametro.setParticipanteEvento(getParticipanteEvento());

		colocarParticipanteCargoOrgaoNoRequest(parametro);

		return VINCULAR_ORGAOS_CARGOS_XHTML;
	}
	
	public String comandoAlterarCargoOrgao(OcupacaoParticipanteEventoSGVEN orgaoCargo){
		
		colocarParticipanteCargoOrgaoNoRequest(orgaoCargo);
		
		return EDITAR_ORGAOS_CARGOS_XHTML;
	}

	public String comandoVisualizarCargoOrgao(OcupacaoParticipanteEventoSGVEN orgaoCargo){
		
		colocarParticipanteCargoOrgaoNoRequest(orgaoCargo);
		
		return VISUALIZAR_ORGAOS_CARGOS_XHTML;
	}
	public String comandoExcluirCargoOrgao(OcupacaoParticipanteEventoSGVEN orgaoCargo){
		
		colocarParticipanteCargoOrgaoNoRequest(orgaoCargo);
		
		return EXCLUIR_ORGAOS_CARGOS_XHTML;
	}

	protected void colocarParticipanteCargoOrgaoNoRequest(
			OcupacaoParticipanteEventoSGVEN orgaoCargo) {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put(PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO, orgaoCargo);
	}
	
	protected void colocarEventoNoRequest() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put(EVENTO_PARAMETRO, evento);
	}

	public List<OcupacaoParticipanteEventoSGVEN> obterVinculosCargoOrgao() {
		List<OcupacaoParticipanteEventoSGVEN> retorno = new ArrayList<>();
		if(getParticipanteEvento() == null || getParticipanteEvento().getListaOcupacaoParticipacao() == null) {
			return retorno;
		}
		List<OcupacaoParticipanteEventoSGVEN> orgaosCargosEventos = getParticipanteEvento().getListaOcupacaoParticipacao();
		for(OcupacaoParticipanteEventoSGVEN p : orgaosCargosEventos) {
			if(p.getAtivo() == null || p.getAtivo()) {
				p.setAtivo(true);
				retorno.add(p);
			}
		}
		return retorno;
	}

	protected void removerMascarasParticipanteEvento(
			ParticipanteEventoSGVEN participanteEvento) {
		participanteEvento.setTelefoneResidencial(removerMascaras(participanteEvento.getTelefoneResidencial()));
		participanteEvento
				.setTelefoneCelular(removerMascaras(participanteEvento
						.getTelefoneCelular()));
		participanteEvento.setCep(removerMascaras(participanteEvento.getCep()));
	}
	
	private String removerMascaras(String valor) {
		valor = valor.replaceAll("\\(", "");
		valor = valor.replaceAll(" ", "");
		valor = valor.replaceAll("\\)", "");
		valor = valor.replaceAll("\\-", "");
		valor = valor.replaceAll("\\.", "");
		valor = valor.replaceAll("\\,", "");
		return valor;
	}

	// ----------------------------GETTERS E SETTERS --------
	// ----------------------

	public ParticipanteEventoSGVEN getParticipanteEvento() {
		return participanteEvento;
	}

	public void setParticipanteEvento(ParticipanteEventoSGVEN participanteEvento) {
		this.participanteEvento = participanteEvento;
	}

	public Boolean getRenderizarOpcoesConjuge() {
		return renderizarOpcoesConjuge;
	}

	public void setRenderizarOpcoesConjuge(Boolean renderizarOpcoesConjuge) {
		this.renderizarOpcoesConjuge = renderizarOpcoesConjuge;
	}

	public String getEnderecoFoto() {
		if (participanteEvento.getFoto() == null) {
			return null;
		}
		return new String(participanteEvento.getFoto());
	}

	public void setEnderecoFoto(String enderecoFoto) {
		participanteEvento.setFoto(enderecoFoto.getBytes());
	}

	public Boolean getModalSucessoVisivel() {
		return modalSucessoVisivel;
	}

	public void setModalSucessoVisivel(Boolean modalSucessoVisivel) {
		this.modalSucessoVisivel = modalSucessoVisivel;
	}

}
