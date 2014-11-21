package br.jus.stj.sigeven.view.controller.poderarea;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.jus.stj.sigeven.util.ConstantesSigeven;

@ManagedBean
@ViewScoped
public class AlteracaoPoderAreaController extends PoderAreaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean exibeMensagem;	
	private String dialogMensagem;	
	private boolean sucesso;	
	
	public void comandoSalvar() {
		
		getPoderArea().setIndPublico(ConstantesSigeven.IND_PUBLICO);
		getPoderArea().setSeqLocal(ConstantesSigeven.SEQ_LOCAL);
		
		getPoderArea().setNome(getPoderArea().getNome().trim());
		
		if(!"".equals(getPoderArea().getNome())){	
			sucesso = poderAreaBean.validarAlteracao(getPoderArea());
			exibeMensagem = true;
			if (sucesso) {
				dialogMensagem = getMensagem("msg", "msg.msg002.alteracaosucesso");
			} 
			else {
				dialogMensagem = getMensagem("msg", "msg.MSG045.poder.area.mesmo.nome");
			}
		} else {
			exibeMensagem = true;
			dialogMensagem = getMensagem("msg", "msg.msg005.camposobrigatorios");
		}	
		
	}

	public String comandoLimpar() {
		getPoderArea().setNome("");
		colocarOrgaoNaRequest();
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
