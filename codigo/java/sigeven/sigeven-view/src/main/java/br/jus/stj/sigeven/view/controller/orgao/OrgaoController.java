package br.jus.stj.sigeven.view.controller.orgao;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.orgao.OrgaoBean;
import br.jus.stj.sigeven.business.participanteorgaocargoevento.ParticipanteOrgaoCargoEventoBean;
import br.jus.stj.sigeven.business.to.ParametrosDisponiveisTO;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class OrgaoController extends ParametersRequestController {

	private static final String ORGAO_VIEW = "orgaoView";

	private static final String EVENTO_VIEW = "eventoView";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Boolean modalSucessoVisivel;

	protected EventoSGVEN evento;
	protected OrgaoMDIR orgao;
	
	@EJB
	protected OrgaoBean orgaoBean;
	@EJB
	protected ParticipanteOrgaoCargoEventoBean ocupacaoParticipanteBean;
	
	private ParametrosDisponiveisTO parametros;

	@PostConstruct
	public void init() {
		orgao = new OrgaoMDIR();
		definirParametrosRequestMap();
		definirParametrosParaVinculo();
	}

	private void definirParametrosParaVinculo() {
		parametros = orgaoBean.obterParametrosDisponiveisParaVinculo();
	}

	private void definirParametrosRequestMap() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		if (requestMap.get(ORGAO_VIEW) != null) {
			orgao = (OrgaoMDIR) obterObjetoEmRequestSession(ORGAO_VIEW);
//			evento = orgao.getEvento();
		} else if (requestMap.get(EVENTO_VIEW) != null) {
			evento = (EventoSGVEN) obterObjetoEmRequestSession(EVENTO_VIEW);
			orgao = new OrgaoMDIR();
//			orgao.setEvento(evento);
		}
	}


	public String comandoCancelar() {
		return getPaginaAnterior();
	}

	protected void colocarOrgaoNaRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(ORGAO_VIEW, orgao);
	}

	public OrgaoMDIR getOrgao() {
		return orgao;
	}

	public void setOrgao(OrgaoMDIR orgao) {
				
		this.orgao = orgao;
	}

	public Boolean getModalSucessoVisivel() {
		return modalSucessoVisivel;
	}

	public void setModalSucessoVisivel(Boolean modalSucessoVisivel) {
		this.modalSucessoVisivel = modalSucessoVisivel;
	}

	public ParametrosDisponiveisTO getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosDisponiveisTO parametros) {
		this.parametros = parametros;
	}

}
