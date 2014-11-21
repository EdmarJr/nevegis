package br.jus.stj.sigeven.view.controller.cargo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class VisualizacaoCargoController extends CargoController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String comandoVoltar() {
		return getPaginaAnterior();

	}

}
