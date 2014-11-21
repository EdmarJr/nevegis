package br.jus.stj.sigeven.view.controller.participantes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;

@ManagedBean
@ViewScoped
public class InclusaoParticipanteController extends ParticipanteController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void comandoSalvar(ParticipanteEventoSGVEN participanteEvento) {
		removerMascarasParticipanteEvento(participanteEvento);
		participanteEventoBean.incluir(participanteEvento);
		setModalSucessoVisivel(true);
	}

	public void comandoInclusaoSucesso() {

	}	
	
	public String depoisDeSalvar() {
		colocarEventoNoRequest();
		return getPaginaAnterior();

	}
	
	public String comandoCancelar() {
		colocarEventoNoRequest();
		return getPaginaAnterior();
	}	
	
}
