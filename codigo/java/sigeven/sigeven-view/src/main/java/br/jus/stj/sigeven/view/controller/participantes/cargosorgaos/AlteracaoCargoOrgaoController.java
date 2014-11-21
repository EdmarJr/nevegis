package br.jus.stj.sigeven.view.controller.participantes.cargosorgaos;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;

@ManagedBean
@ViewScoped
public class AlteracaoCargoOrgaoController extends CargoOrgaoController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OcupacaoParticipanteEventoSGVEN orgaoCargoTemp;

	@PostConstruct
	public void init() {
		super.init();
		orgaoCargoTemp = new OcupacaoParticipanteEventoSGVEN();
		orgaoCargoTemp.setCargo(getParticipanteOrgaoCargoEvento()
				.getCargo());
		orgaoCargoTemp.setOrgao(getParticipanteOrgaoCargoEvento()
				.getOrgao());
	}


	public OcupacaoParticipanteEventoSGVEN getOrgaoCargoTemp() {
		return orgaoCargoTemp;
	}

	public void setOrgaoCargoTemp(OcupacaoParticipanteEventoSGVEN orgaoCargoTemp) {
		this.orgaoCargoTemp = orgaoCargoTemp;
	}




	@Override
	public String concluir() {
		definirParametrosMapaRequest();
		return getPaginaAnterior();
	}

	@Override
	public Boolean camposSomenteLeitura() {
		return true;
	}

	@Override
	public void antesDeConcluir() {
		OcupacaoParticipanteEventoSGVEN participanteOrgaoCargoEvento = super
				.getParticipanteOrgaoCargoEvento();
		participanteOrgaoCargoEvento.setOrgao(orgaoCargoTemp.getOrgao());
		participanteOrgaoCargoEvento.setCargo(orgaoCargoTemp.getCargo());
		definirPropriedadesMensagemSucesso();
	}

	private void definirPropriedadesMensagemSucesso() {
		setMensagemSucesso(getMensagem("msg", "msg.msg015.orgaoalteradosucesso"));
		setExibeMensagem(true);
	}

	public String cancelarAlteracao() {
		definirParametrosMapaRequest();
		return getPaginaAnterior();
	}

}
