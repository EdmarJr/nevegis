package br.jus.stj.sigeven.view.controller.participantes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExclusaoParticipanteController extends ParticipanteController {

	
	private Boolean modalAlertaVisivel;
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void comandoExcluir() {
		
		participanteEventoBean.excluir(getParticipanteEvento());
		setModalAlertaVisivel(false);
		setModalSucessoVisivel(true);
		abrirModal("dialogMensagemSucesso");
	}

	public void comandoCancelarModal() {
		setModalAlertaVisivel(false);
		fecharModal("dialogMensagemSucesso");
	}
	
	public String depoisDeExcluir() {
		colocarEventoNoRequest();
		return getPaginaAnterior();

	}
	
	public String comandoCancelar() {
		colocarEventoNoRequest();
		return getPaginaAnterior();
	}

	public Boolean getModalAlertaVisivel() {
		return modalAlertaVisivel;
	}

	public void setModalAlertaVisivel(Boolean modalAlertaVisivel) {
		this.modalAlertaVisivel = modalAlertaVisivel;
	}
	
}
