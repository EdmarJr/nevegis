package br.jus.stj.sigeven.view.controller.poderarea;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.poderarea.PoderAreaBean;
import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class ConsultaPoderAreaController extends ParametersRequestController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String PAGE_VISUALIZAR = "visualizar.jsf";
	
	@EJB
	private PoderAreaBean poderAreaBean;
	private List<PoderMalaMDIR> poderAreas;
	private PoderMalaMDIR poderArea;

	@PostConstruct
	public void init() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		if (requestMap.get("poderArea") != null)
			poderArea = (PoderMalaMDIR)requestMap.get("poderArea");
		else
			poderArea = new PoderMalaMDIR();
		definirPoderArea();
	}

	private void definirPoderArea() {
		poderAreas = poderAreaBean.obterDisponiveis();
	}

	public List<PoderMalaMDIR> getPoderAreas() {
		return poderAreas;
	}

	public void setPoderAreas(List<PoderMalaMDIR> poderAreas) {
		this.poderAreas = poderAreas;
	}
	
	public String comandoAlterar(PoderMalaMDIR poderArea) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("poderAreaView", poderArea);
		return "alterarPoderArea.xhtml";
	}

	public String comandoExcluir(PoderMalaMDIR poderArea) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("poderAreaView", poderArea);
		return "excluirPoderArea.xhtml";
	}

	public String comandoIncluir() {
		return "incluirPoderArea.xhtml";
	}

	public void comandoPesquisar() {
		poderAreas = poderAreaBean.pesquisar(poderArea);
	}

	public String comandoVisualizar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("poderArea", poderArea);
		return PAGE_VISUALIZAR;
	}

	public void comandoLimpar() {
		resetarAtributos();
	}

	private void resetarAtributos() {
		poderAreas = poderAreaBean.obterDisponiveis();
		poderArea = new PoderMalaMDIR(); 
	}
	
	public String comandoCancelar() {
		return getPaginaAnterior();
	}

	/**
	 * @return the poderArea
	 */
	public PoderMalaMDIR getPoderArea() {
		return poderArea;
	}

	/**
	 * @param poderArea the poderArea to set
	 */
	public void setPoderArea(PoderMalaMDIR poderArea) {
		this.poderArea = poderArea;
	}

}
