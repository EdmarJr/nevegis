package br.jus.stj.sigeven.view.controller.tipoorgao;

import java.util.List;
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
public class ConsultaTipoOrgaoController extends ParametersRequestController {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String PAGE_VISUALIZAR = "visualizar.jsf";
		
	@EJB
	private TipoOrgaoBean tipoOrgaoBean;
	private List<TipoOrgaoMDIR> tipoOrgaos;
	private TipoOrgaoMDIR tipoOrgao;
	
	@PostConstruct
	public void init() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		if (requestMap.get("tipoOrgao") != null)
			tipoOrgao = (TipoOrgaoMDIR)requestMap.get("tipoOrgao");
		else
			tipoOrgao = new TipoOrgaoMDIR();
		definirOrgaos();
	}


	private void definirOrgaos() {
		tipoOrgaos = tipoOrgaoBean.obterDisponiveis();
	}

	public List<TipoOrgaoMDIR> getTipoOrgaos() {
		return tipoOrgaos;
	}

	public void setTipoOrgaos(List<TipoOrgaoMDIR> tipoOrgaos) {
		this.tipoOrgaos = tipoOrgaos;
	}

	public String comandoAlterar(TipoOrgaoMDIR orgao) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("orgaoView", orgao);
		return "alterarTipoOrgao.xhtml";
	}

	public String comandoExcluir(TipoOrgaoMDIR orgao) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("orgaoView", orgao);
		return "excluirTipoOrgao.xhtml";
	}

	public String comandoIncluir() {
		return "incluirTipoOrgao.xhtml";
	}
	
	public String comandoVisualizar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("tipoOrgao", tipoOrgao);
		return PAGE_VISUALIZAR;
	}
	
	public String comandoCancelar() {
		return getPaginaAnterior();
	}
	
	public void comandoPesquisar() {
		tipoOrgaos = tipoOrgaoBean.pesquisar(tipoOrgao);
	}
	
	public void comandoLimpar() {
		resetarAtributos();
	}
	
	private void resetarAtributos() {
		tipoOrgaos = tipoOrgaoBean.recuperarTodos();
		tipoOrgao = new TipoOrgaoMDIR(); 
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

}
