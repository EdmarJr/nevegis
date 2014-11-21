package br.jus.stj.sigeven.view.controller.cargo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.jus.stj.sigeven.util.ConstantesSigeven;

@ManagedBean
@ViewScoped
public class InclusaoCargoController extends CargoController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean exibeMensagem;	
	private String dialogMensagem;	
	private boolean sucesso;

	public void comandoSalvar() {
		
		getCargo().setIndPublico(ConstantesSigeven.IND_PUBLICO);
		getCargo().setSeqLocal(ConstantesSigeven.SEQ_LOCAL);
		
		getCargo().setNome(getCargo().getNome().trim());
		
		if(!"".equals(getCargo().getNome())){
			sucesso = cargoBean.validarInclusao(getCargo());
			exibeMensagem = true;
			if (sucesso) {
				dialogMensagem = getMensagem("msg", "msg.msg001.inclusaosucesso");
			} 
			else {
				dialogMensagem = getMensagem("msg", "msg.MSG048.cargo.mesmo.nome");
			}
		} else {
			exibeMensagem = true;
			dialogMensagem = getMensagem("msg", "msg.msg005.camposobrigatorios");
		}	
		
	}

	public String comandoLimpar() {
		colocarEventoNaRequest();
		return getPaginaAtual();
	}

	public String comandoSalvarComSucesso() {
		colocarEventoNaRequest();
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