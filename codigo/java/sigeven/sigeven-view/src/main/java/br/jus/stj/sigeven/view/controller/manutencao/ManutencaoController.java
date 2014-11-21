package br.jus.stj.sigeven.view.controller.manutencao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.evento.EventoBean;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class ManutencaoController extends ParametersRequestController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private EventoBean eventoBean;
	private List<EventoSGVEN> eventos;

	private EventoSGVEN evento;

	@PostConstruct
	public void init() {
		evento = new EventoSGVEN();
		if (obterObjetoEmRequestSession("eventoView") != null) {
			evento = (EventoSGVEN) obterObjetoEmRequestSession("eventoView");
		}
		definirEventos();
	}

	private void definirEventos() {
		eventos = eventoBean.recuperarTodos();
	}
	public EventoSGVEN getEvento() {
		return evento;
	}

	public void setEvento(EventoSGVEN evento) {
		this.evento = evento;
	}

	public String comandoManterOrgaos() {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put("eventoView", evento);
		adicionarObjetoEmRequestSession("eventoView", evento);
		return "../manterOrgao/consultarOrgao.xhtml";
	}

	public List<EventoSGVEN> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoSGVEN> eventos) {
		this.eventos = eventos;
	}

}

