package br.jus.stj.sigeven.view.controller.cargo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExclusaoCargoController extends CargoController {

	protected Boolean modalAlertaVisivel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void comandoConcluir() {
		cargoBean.validarExclusaoComRelacionamentosParticipantes(getCargo());
		if (seExisteMensagemErro()) {
			comandoCancelarModal();
			abrirModal("dialogMensagemSucesso1");
			return;
		}
		cargoBean.excluir(getCargo());
		setModalSucessoVisivel(true);
	}

	public void comandoCancelarModal() {
		setModalAlertaVisivel(false);
		fecharModal("dialogMensagemAlerta");
	}

	public String comandoExcluirComSucesso() {
		colocarEventoNaRequest();
		return getPaginaAnterior();
	}

	public Boolean getModalAlertaVisivel() {
		return modalAlertaVisivel;
	}

	public void setModalAlertaVisivel(Boolean modalAlertaVisivel) {
		this.modalAlertaVisivel = modalAlertaVisivel;
	}

}
