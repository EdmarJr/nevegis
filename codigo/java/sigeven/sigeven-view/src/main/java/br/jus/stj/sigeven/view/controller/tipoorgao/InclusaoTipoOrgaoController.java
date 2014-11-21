package br.jus.stj.sigeven.view.controller.tipoorgao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class InclusaoTipoOrgaoController extends TipoOrgaoController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean exibeMensagem;	
	private String dialogMensagem;	
	private boolean sucesso;
	
	public void comandoSalvar() {
		
		getTipoOrgao().setNome(getTipoOrgao().getNome().trim());
		
		if(!"".equals(getTipoOrgao().getNome())){	
			sucesso = orgaoBean.validarInclusao(getTipoOrgao());
			exibeMensagem = true;
			if (sucesso) {
				dialogMensagem = getMensagem("msg", "msg.msg001.inclusaosucesso");
			} 
			else {
				dialogMensagem = getMensagem("msg", "msg.msg044.orgaomesmonome");
			}
		} else {
			exibeMensagem = true;
			dialogMensagem = getMensagem("msg", "msg.msg005.camposobrigatorios");
		}	
		
	}

	public String comandoLimpar() {
		return getPaginaAtual();
	}

	public String comandoSalvarComSucesso() {
		return getPaginaAnterior();
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
