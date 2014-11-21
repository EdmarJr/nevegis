package br.jus.stj.sigeven.view.controller.participantes.cargosorgaos;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.cargo.CargoBean;
import br.jus.stj.sigeven.business.orgao.OrgaoBean;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

public abstract class CargoOrgaoController extends ParametersRequestController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO = "participanteOrgaoCargoEventoParam";

	@EJB
	protected CargoBean cargoBean;
	@EJB
	protected OrgaoBean orgaoBean;

	private String mensagemSucesso;

	private List<OrgaoMDIR> orgaosDisponiveis;
	private List<CargoMDIR> cargosDisponiveis;

	public boolean exibeMensagem;
	
	private OrgaoMDIR orgao;

	private OcupacaoParticipanteEventoSGVEN participanteOrgaoCargoEvento;

	@PostConstruct
	public void init() {

		definirParticipanteOrgaoCargoEvento();

		definirCargosDisponiveis();
		definirOrgaosDisponiveis();

	}

	private void definirParticipanteOrgaoCargoEvento() {
		Object objetoRequest = obterObjetoEmRequestSession(PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO);
		if (objetoRequest != null) {
			participanteOrgaoCargoEvento = (OcupacaoParticipanteEventoSGVEN) objetoRequest;
			participanteOrgaoCargoEvento.setAtivo(true);
		}
	}

	public String comandoManterOrgaos() {
		adicionarObjetoEmRequestSession(
				PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO,
				participanteOrgaoCargoEvento);
		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequestMap()
				.put("eventoView",
						participanteOrgaoCargoEvento.getParticipanteEvento()
								.getEvento());
		return "../manterOrgao/consultarOrgao.xhtml";
	}

	public String comandoManterCargos() {
		adicionarObjetoEmRequestSession(
				PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO,
				participanteOrgaoCargoEvento);
		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequestMap()
				.put("eventoView",
						participanteOrgaoCargoEvento.getParticipanteEvento()
								.getEvento());
		return "../manterCargo/consultarCargo.xhtml";
	}

	public void resetarValores() {
		participanteOrgaoCargoEvento.setCargo(null);
		participanteOrgaoCargoEvento.setOrgao(null);
	}

	public abstract void antesDeConcluir();

	public abstract String concluir();

	public abstract Boolean camposSomenteLeitura();

	public OcupacaoParticipanteEventoSGVEN getParticipanteOrgaoCargoEvento() {
		return participanteOrgaoCargoEvento;
	}

	public void setParticipanteOrgaoCargoEvento(
			OcupacaoParticipanteEventoSGVEN participanteOrgaoCargoEvento) {
		this.participanteOrgaoCargoEvento = participanteOrgaoCargoEvento;
	}

	protected void definirParametrosMapaRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("participanteEventoParam",
				getParticipanteOrgaoCargoEvento().getParticipanteEvento());

	}

	private void definirCargosDisponiveis() {
		cargosDisponiveis = cargoBean.recuperarTodos();
	}

	private void definirOrgaosDisponiveis() {
		orgaosDisponiveis = orgaoBean.obterOrgaosDisponiveisInclusao();
	}

	public List<CargoMDIR> obterCargosDisponiveis() {
		return cargosDisponiveis;
	}

	public List<OrgaoMDIR> obterOrgaosDisponiveis() {
		return orgaosDisponiveis;
	}

	public String getMensagemSucesso() {
		return mensagemSucesso;
	}

	public void setMensagemSucesso(String mensagemSucesso) {
		this.mensagemSucesso = mensagemSucesso;
	}

	public boolean isExibeMensagem() {
		return exibeMensagem;
	}

	public void setExibeMensagem(boolean exibeMensagem) {
		this.exibeMensagem = exibeMensagem;
	}

	public OrgaoMDIR getOrgao() {
		return orgao;
	}

	public void setOrgao(OrgaoMDIR orgao) {
		this.orgao = orgao;
	}

}
