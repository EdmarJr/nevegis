package br.jus.stj.sigeven.view.controller.participantes;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;

@ManagedBean
@ViewScoped
public class AlteracaoParticipanteController extends ParticipanteController {
	private static final String PARTICIPANTE_EVENTO = "participanteEventoParam";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void comandoSalvar(ParticipanteEventoSGVEN participanteEvento) {
		removerMascarasParticipanteEvento(participanteEvento);
		if(participanteEvento.getSituacao()==null)
			participanteEvento.setSituacao('S');
		participanteEventoBean.alterar(participanteEvento);
		setModalSucessoVisivel(true);
	}
	
	public String depoisDeSalvar() {
		colocarEventoNoRequest();
		return getPaginaAnterior();

	}
	
	@Override
	public String comandoLimpar() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		ParticipanteEventoSGVEN participanteEventoTemp = new ParticipanteEventoSGVEN();
		participanteEventoTemp.setId(getParticipanteEvento().getId());
		participanteEventoTemp.setListaOcupacaoParticipacao(getParticipanteEvento()
				.getListaOcupacaoParticipacao());
		participanteEventoTemp.setEvento(getParticipanteEvento().getEvento());
		List<OcupacaoParticipanteEventoSGVEN> orgaosCargosEventosTemp = participanteEventoTemp
				.getListaOcupacaoParticipacao();
		for(OcupacaoParticipanteEventoSGVEN orgaosCargos : orgaosCargosEventosTemp) {
			orgaosCargos.setAtivo(false);
		}
		requestMap.put(PARTICIPANTE_EVENTO, participanteEventoTemp);
		return getPaginaAtual();
	}

	public String comandoCancelar() {
		colocarEventoNoRequest();
		return getPaginaAnterior();
	}
	
	
}
