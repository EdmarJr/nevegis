package br.jus.stj.sigeven.view.controller.participantes.cargosorgaos;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExclusaoCargoOrgaoController extends CargoOrgaoController {

	private String mensagemAlerta;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String concluir() {
		getParticipanteOrgaoCargoEvento().setAtivo(false);
		definirParametrosMapaRequest();
		return getPaginaAnterior();
	}
	
	@PostConstruct
	@Override
	public void init() {
		super.init();
		definirPropriedadesMensagemAlerta();
		definirPropriedadesMensagemSucesso();
	}

	@Override
	public Boolean camposSomenteLeitura() {
		return true;
	}

	public void definirPropriedadesMensagemAlerta() {
		setMensagemAlerta(getMensagem("msg", "msg.msg003.confirmarexclusao"));
		
	}
	
	public String cancelarExclusao() {
		
		definirParametrosMapaRequest();
		return getPaginaAnterior();
	}
	
	private void definirPropriedadesMensagemSucesso() {
		setMensagemSucesso(getMensagem("msg", "msg.msg004.exclusaosucesso"));
	}
	

	public String getMensagemAlerta() {
		return mensagemAlerta;
	}

	public void setMensagemAlerta(String mensagemAlerta) {
		this.mensagemAlerta = mensagemAlerta;
	}

	@Override
	public void antesDeConcluir() {
		// TODO Auto-generated method stub
		
	}

}
