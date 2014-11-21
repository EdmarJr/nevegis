package br.jus.stj.sigeven.view.controller.poderarea;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExclusaoPoderAreaController extends PoderAreaController {

	protected Boolean modalAlertaVisivel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean exibeMensagem;	
	private String dialogMensagem;	
	private boolean sucesso;
	
	public void comandoConcluir() {
		
		sucesso = poderAreaBean.validarExclusao(getPoderArea());
		exibeMensagem = true;
		if (sucesso) {
			dialogMensagem = getMensagem("msg", "msg.msg004.exclusaosucesso");
		} 
		else {
			dialogMensagem = getMensagem("msg", "msg.MSG047.poder.area.orgao.vinculado");
		}
		
	}

	public void comandoCancelarModal() {
		setModalAlertaVisivel(false);
		fecharModal("dialogMensagemAlerta");
	}

	public String comandoExcluirComSucesso() {
		return getPaginaAnterior();
	}

	public Boolean getModalAlertaVisivel() {
		return modalAlertaVisivel;
	}

	public void setModalAlertaVisivel(Boolean modalAlertaVisivel) {
		this.modalAlertaVisivel = modalAlertaVisivel;
	}

	/**
	 * @return the exibeMensagem
	 */
	public boolean isExibeMensagem() {
		return exibeMensagem;
	}

	/**
	 * @param exibeMensagem the exibeMensagem to set
	 */
	public void setExibeMensagem(boolean exibeMensagem) {
		this.exibeMensagem = exibeMensagem;
	}

	/**
	 * @return the dialogMensagem
	 */
	public String getDialogMensagem() {
		return dialogMensagem;
	}

	/**
	 * @param dialogMensagem the dialogMensagem to set
	 */
	public void setDialogMensagem(String dialogMensagem) {
		this.dialogMensagem = dialogMensagem;
	}

	/**
	 * @return the sucesso
	 */
	public boolean isSucesso() {
		return sucesso;
	}

	/**
	 * @param sucesso the sucesso to set
	 */
	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}	
	
}