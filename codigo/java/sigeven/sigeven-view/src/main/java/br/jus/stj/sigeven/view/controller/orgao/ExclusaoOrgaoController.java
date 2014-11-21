package br.jus.stj.sigeven.view.controller.orgao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;

@ManagedBean
@ViewScoped
public class ExclusaoOrgaoController extends OrgaoController {

	protected Boolean modalAlertaVisivel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void comandoConcluir() {

		orgaoBean.validarExclusaoComRelacionamentosParticipantes(getOrgao());
		orgaoBean.validarExclusaoOrgaoMalaDireta(getOrgao());
		
		List<OcupacaoParticipanteEventoSGVEN> relacoes = ocupacaoParticipanteBean
				.recuperarTodosPorOrgao(orgao);
				 
		 if (relacoes != null && relacoes.size() != 0) {
			for (OcupacaoParticipanteEventoSGVEN relacaoTemp : relacoes) {				 
				 if (relacaoTemp.getParticipanteEvento() != null) {
					 comandoCancelarModal();
					 criarMensagemErroParticipanteVinculado();
					 return;				
				 }
			}		
		 }
		
		orgaoBean.excluir(getOrgao());
		setModalSucessoVisivel(true);
	}

	public void comandoCancelarModal() {
		setModalAlertaVisivel(false);
		fecharModal("dialogMensagemAlerta");
	}
	
	private void criarMensagemErroParticipanteVinculado() {
		FacesMessage msg = new FacesMessage(getMensagem("msg",
		"msg.MSG042.orgaocomparticipantes"));
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage("nomeOrgao",
		 msg);
		
		RequestContext.getCurrentInstance().showMessageInDialog( msg);
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

}
