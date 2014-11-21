package br.jus.stj.sigeven.view.controller.participantes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class VisualizacaoParticipanteController extends ParticipanteController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String comandoVoltar() {
		colocarEventoNoRequest();
		return getPaginaAnterior();
	}
	
	
}
