package br.jus.stj.sigeven.view.controller.tipoorgao;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.tipoorgao.TipoOrgaoBean;
import br.jus.stj.sigeven.entity.db2.maladir.TipoOrgaoMDIR;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class TipoOrgaoController extends ParametersRequestController {

	private static final String ORGAO_VIEW = "orgaoView";

	private static final String EVENTO_VIEW = "eventoView";
	
	private static final String TIPO_ORGAO__PARAMETRO = "tipoOrgaoParam";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Boolean modalSucessoVisivel;

	private TipoOrgaoMDIR tipoOrgao;
	@EJB
	protected TipoOrgaoBean orgaoBean;

	@PostConstruct
	public void init() {
		tipoOrgao = new TipoOrgaoMDIR();
		definirParametros();
	}

	private void definirParametros() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		if (requestMap.get(ORGAO_VIEW) != null) {
			tipoOrgao = (TipoOrgaoMDIR) requestMap.get(ORGAO_VIEW);
		} else if (requestMap.get(EVENTO_VIEW) != null) {
			tipoOrgao = new TipoOrgaoMDIR();
		}
	}


	public String comandoCancelar() {
		return getPaginaAnterior();
	}

	protected void colocarOrgaoNaRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(ORGAO_VIEW, tipoOrgao);
	}

	public String comandoManterTipoOrgao() {
		adicionarObjetoEmRequestSession(
				TIPO_ORGAO__PARAMETRO,
				tipoOrgao);
		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequestMap()
				.put("orgaoView",
						tipoOrgao.getClass());
		return "../manterTipoOrgao/consultarTipoOrgao.xhtml";
	}
	
	public TipoOrgaoMDIR getTipoOrgao() {
		return tipoOrgao;
	}

	public void setTipoOrgao(TipoOrgaoMDIR tipoOrgao) {
		this.tipoOrgao = tipoOrgao;
	}

	public Boolean getModalSucessoVisivel() {
		return modalSucessoVisivel;
	}

	public void setModalSucessoVisivel(Boolean modalSucessoVisivel) {
		this.modalSucessoVisivel = modalSucessoVisivel;
	}

}
