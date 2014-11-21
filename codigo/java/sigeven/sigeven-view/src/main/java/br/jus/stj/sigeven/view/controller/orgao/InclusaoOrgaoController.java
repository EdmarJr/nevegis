package br.jus.stj.sigeven.view.controller.orgao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.jus.stj.sigeven.util.ConstantesSigeven;

@ManagedBean
@ViewScoped
public class InclusaoOrgaoController extends OrgaoController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean exibeMensagem;	
	private String dialogMensagem;	
	private boolean sucesso;
	
	public void comandoSalvar() {

		getOrgao().setSeqLocal(ConstantesSigeven.SEQ_LOCAL);
		getOrgao().setIndPublico(ConstantesSigeven.IND_PUBLICO);
		getOrgao().getPoderArea().setSeqLocal(ConstantesSigeven.SEQ_LOCAL);
		getOrgao().getPoderArea().setIndPublico(ConstantesSigeven.IND_PUBLICO);
		
		getOrgao().setNome(getOrgao().getNome().trim());
		
		if(!"".equals(getOrgao().getNome())){
			sucesso = orgaoBean.validarInclusao(getOrgao());
			exibeMensagem = true;
			if (sucesso) {
				dialogMensagem = getMensagem("msg", "msg.msg001.inclusaosucesso");
			} 
			else {
				dialogMensagem = getMensagem("msg", "msg.msg0xx.orgaocommesmonome");
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
