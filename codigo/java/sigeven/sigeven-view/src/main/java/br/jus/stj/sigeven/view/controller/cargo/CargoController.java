package br.jus.stj.sigeven.view.controller.cargo;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.cargo.CargoBean;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class CargoController extends ParametersRequestController {

	private static final String CARGO_VIEW = "cargoView";

	private static final String EVENTO_VIEW = "eventoView";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Boolean modalSucessoVisivel;

	protected EventoSGVEN evento;
	private CargoMDIR cargo;
	@EJB
	protected CargoBean cargoBean;

	@PostConstruct
	public void init() {
		cargo = new CargoMDIR();
		definirParametros();
	}

	private void definirParametros() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		if (requestMap.get(CARGO_VIEW) != null) {
			cargo = (CargoMDIR) requestMap.get(CARGO_VIEW);
		} else if (requestMap.get(EVENTO_VIEW) != null) {
			cargo = new CargoMDIR();
		}
	}


	public String comandoCancelar() {
		colocarEventoNaRequest();
		return getPaginaAnterior();
	}

	protected void colocarEventoNaRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(EVENTO_VIEW, evento);
	}

	protected void colocarOrgaoNaRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(CARGO_VIEW, cargo);
	}

	public CargoMDIR getCargo() {
		return cargo;
	}

	public void setCargo(CargoMDIR cargo) {
		this.cargo = cargo;
	}

	public Boolean getModalSucessoVisivel() {
		return modalSucessoVisivel;
	}

	public void setModalSucessoVisivel(Boolean modalSucessoVisivel) {
		this.modalSucessoVisivel = modalSucessoVisivel;
	}

}
