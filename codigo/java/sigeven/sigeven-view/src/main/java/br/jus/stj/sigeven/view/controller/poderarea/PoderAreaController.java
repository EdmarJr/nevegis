package br.jus.stj.sigeven.view.controller.poderarea;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.poderarea.PoderAreaBean;
import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@ManagedBean
@ViewScoped
public class PoderAreaController extends ParametersRequestController {

	private static final String PODER_AREA_VIEW = "poderAreaView";
	
	private static final String EVENTO_VIEW = "eventoView";
	
	private static final String PODER_AREA__PARAMETRO = "poderAreaParam";


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Boolean modalSucessoVisivel;

	@ManyToOne
	private PoderMalaMDIR poderArea;
	@ManyToOne
	@EJB
	protected PoderAreaBean poderAreaBean;

	@PostConstruct
	public void init() {
		poderArea = new PoderMalaMDIR();
		definirParametros();
	}
	
	private void definirParametros() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		if (requestMap.get(PODER_AREA_VIEW) != null) {
			poderArea = (PoderMalaMDIR) requestMap.get(PODER_AREA_VIEW);
		} else if (requestMap.get(EVENTO_VIEW) != null) {
			poderArea = new PoderMalaMDIR();
		}
	}	

	public String comandoCancelar() {
		return getPaginaAnterior();
	}

	protected void colocarOrgaoNaRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(PODER_AREA_VIEW, poderArea);
	}

	public String comandoManterPoderArea() {
		adicionarObjetoEmRequestSession(
				PODER_AREA__PARAMETRO,
				poderArea);
		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequestMap()
				.put("poderAreaView",
						poderArea.getClass());
		return "../manterPoderArea/consultarPoderArea.xhtml";
	}
	
	public PoderMalaMDIR getPoderArea() {
		return poderArea;
	}

	public void setPoderArea(PoderMalaMDIR poderArea) {
		this.poderArea = poderArea;
	}

	public Boolean getModalSucessoVisivel() {
		return modalSucessoVisivel;
	}

	public void setModalSucessoVisivel(Boolean modalSucessoVisivel) {
		this.modalSucessoVisivel = modalSucessoVisivel;
	}

}
