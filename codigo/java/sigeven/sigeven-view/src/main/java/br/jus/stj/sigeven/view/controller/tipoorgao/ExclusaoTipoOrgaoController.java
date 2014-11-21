package br.jus.stj.sigeven.view.controller.tipoorgao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExclusaoTipoOrgaoController extends TipoOrgaoController {

	protected Boolean modalAlertaVisivel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  
	public void comandoConcluir() {

		orgaoBean
				.validarExclusaoComRelacionamentosParticipantes(getTipoOrgao());
		orgaoBean.validarExclusaoOrgaoMalaDireta(getTipoOrgao());
		if (seExisteMensagemErro()) {
			comandoCancelarModal();
			abrirModal("dialogMensagemSucesso1");
			return;
		} 
		
		// Orgao orgao = orgaoBean
		// .obterOrgaoERelacionamentos(getOrgao());
		// if (orgao.getIdOrigem() != null) {
		// criarMensagemErroImportacaoMalaDireta();
		// comandoCancelarModal();
		// return;
		// }
		
		// List<ParticipanteOrgaoCargoEvento> relacoes = orgao
		// .getRelacionamentosParticipanteOrgaoCargo();
		// if (relacoes != null && relacoes.size() != 0) {
		// for (ParticipanteOrgaoCargoEvento relacaoTemp : relacoes) {
		// if (relacaoTemp.getParticipanteEvento() != null) {
		// criarMensagemErroParticipanteVinculado();
		// comandoCancelarModal();
		// return;
		//
		// }
		// }
		//
		// }
		
		orgaoBean.excluir(getTipoOrgao());
		setModalSucessoVisivel(true);
		
	}

	public void comandoCancelarModal() {
		setModalAlertaVisivel(false);
		fecharModal("dialogMensagemAlerta");
	}

	// private void criarMensagemErroParticipanteVinculado() {
	// FacesMessage msg = new FacesMessage(getMensagem("msg",
	// "msg.MSG042.orgaocomparticipantes"));
	// msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	// FacesContext.getCurrentInstance().addMessage("nomeOrgao",
	// msg);
	// }
	//
	// private void criarMensagemErroImportacaoMalaDireta() {
	// FacesMessage msg = new FacesMessage(getMensagem("msg",
	// "msg.MSG043.orgaoimportadomala"));
	// msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	// FacesContext.getCurrentInstance().addMessage("nomeOrgao", msg);
	// }

	public String comandoExcluirComSucesso() {
		return getPaginaAnterior();
	}

	public Boolean getModalAlertaVisivel() {
		return modalAlertaVisivel;
	}

	public void setModalAlertaVisivel(Boolean modalAlertaVisivel) {
		this.modalAlertaVisivel = modalAlertaVisivel;
	}

}
