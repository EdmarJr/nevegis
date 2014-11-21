package br.jus.stj.sigeven.view.controller.participantes.cargosorgaos;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;

@ManagedBean
@ViewScoped
public class InclusaoCargoOrgaoController extends CargoOrgaoController {

	private static final String PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO = "participanteOrgaoCargoEventoParam";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public void antesDeConcluir() {
		definirPropriedadesMensagemSucesso();
	}
	private void definirPropriedadesMensagemSucesso() {
		setMensagemSucesso(getMensagem("msg",
				"msg.msg014.orgaovinculadosucesso"));
		setExibeMensagem(true);
	}

	@Override
	public String concluir() {
		salvarRelacionamentoOrgaoCargoEmLista();
		return getPaginaAnterior();
	}	 

	@Override
	public Boolean camposSomenteLeitura() {
		return true;
	}

	public String cancelarInclusao() {
		definirParametrosMapaRequest();
		return getPaginaAnterior();
	}

	private void salvarRelacionamentoOrgaoCargoEmLista() {
		ParticipanteEventoSGVEN participanteEvento = getParticipanteOrgaoCargoEvento()
				.getParticipanteEvento();
		participanteEvento.getListaOcupacaoParticipacao().add(
				getParticipanteOrgaoCargoEvento());
		getParticipanteOrgaoCargoEvento().setParticipanteEvento(
				participanteEvento);
		definirParametrosMapaRequest();
	}

	public String comandoLimpar() {
		resetarValores();
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(PARTICIPANTE_ORGAO_CARGO_EVENTO_PARAMETRO,
				getParticipanteOrgaoCargoEvento());
		return getPaginaAtual();
	}

	public void orgaoSelecionado(){
		
		getParticipanteOrgaoCargoEvento().setOrgao(getOrgao());
	}
	
}
