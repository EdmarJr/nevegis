package br.jus.stj.sigeven.view.controller.cargo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.cargo.CargoBean;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class ConsultaCargoController extends ParametersRequestController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CargoBean cargoBean;
	private List<CargoMDIR> cargos;
	private CargoMDIR cargo;

	@PostConstruct
	public void init() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		if (requestMap.get("cargo") != null)
			cargo = (CargoMDIR)requestMap.get("cargo");
		else
			cargo = new CargoMDIR();
		definirCargo();
	}

	public void definirCargo(){
		cargos = cargoBean.recuperarTodos();
	}
	
	public List<CargoMDIR> getCargos() {
		return cargos;
	}

	public void setCargos(List<CargoMDIR> cargos) {
		this.cargos = cargos;
	}

	public String comandoAlterar(CargoMDIR cargo) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("cargoView", cargo);
		return "alterarCargo.xhtml";
	}

	public String comandoExcluir(CargoMDIR cargo) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("cargoView", cargo);
		return "excluirCargo.xhtml";
	}

	public String comandoIncluir() {
		return "incluirCargo.xhtml";
	}

	public String comandoVisualizar(CargoMDIR cargo) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("cargoView", cargo);
		return "visualizar.xhtml";
	}

	public String comandoCancelar() {
		cargo = null;
		return getPaginaAnterior();
	}

	public void comandoPesquisar() {
		cargos = cargoBean.pesquisar(cargo);
	}

	public void comandoLimpar() {
		resetarAtributos();
	}

	private void resetarAtributos() {
		cargos = cargoBean.recuperarTodos();
		cargo = new CargoMDIR(); 
	}

	/**
	 * @return the cargo
	 */
	public CargoMDIR getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(CargoMDIR cargo) {
		this.cargo = cargo;
	}

}
