package br.jus.stj.sigeven.business.evento;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;
import br.jus.stj.sigeven.persistence.EventoDAO;
import br.jus.stj.sigeven.persistence.EventoDAOImpl;

@Alternative
public class EventoDAOMock extends EventoDAOImpl implements
		EventoDAO {

	@Override
	public List<EventoSGVEN> recuperarTodos(Class<EventoSGVEN> entityClass) {
		return getListaEvento();
	}

	public List<EventoSGVEN> recuperarTodos(EventoSGVEN evento){
		return getListaEvento();

	}
	
	
	@Override
	public EventoSGVEN recuperarPorNome(String nome) {
		List<EventoSGVEN> lista = getListaEvento();
		for (EventoSGVEN evento : lista) {
			if (evento.getNome().equalsIgnoreCase(nome))
				return evento;
		}
		return null;
	}
	
	@Override
	public EventoSGVEN incluir(EventoSGVEN entity) {
		entity.setId(10L);
		return entity;
	}

	@Override
	public EventoSGVEN recuperarPorId(Long id) {
		List<EventoSGVEN> lista = getListaEvento();
		for (EventoSGVEN evento : lista) {
			if (evento.getId().longValue() == id.longValue())
				return evento;
		}
		return null;
	}
	
	@Override
	public List<EventoSGVEN> pesquisar(EventoSGVEN evento) {
		List<EventoSGVEN> listaRetorno = new ArrayList<EventoSGVEN>();
		List<EventoSGVEN> lista = getListaEvento();
		String nomePesquisado = evento.getNome().toUpperCase();
		for (EventoSGVEN te : lista) {
			if (te.getNome().toUpperCase().indexOf(nomePesquisado) != -1) {
					listaRetorno.add(te);
			}
		}
		return listaRetorno;
	}
	
	public List<EventoSGVEN> recuperarEventoComParticipantes(EventoSGVEN evento) {
		List<EventoSGVEN> listaRetorno = new ArrayList<EventoSGVEN>();
		List<EventoSGVEN> lista = getListaEvento();
		String nomePesquisado = evento.getNome().toUpperCase();
		for (EventoSGVEN te : lista) {
			if (te.getNome().toUpperCase().indexOf(nomePesquisado) != -1) {
					listaRetorno.add(te);
			}
		}
		return listaRetorno;
	}
	
	public List<EventoSGVEN> consultaEvento(EventoSGVEN evento) {
		List<EventoSGVEN> listaRetorno = new ArrayList<EventoSGVEN>();
		List<EventoSGVEN> lista = getListaEvento();
		String nomePesquisado  = null;
		if(evento.getNome()!=null){
		nomePesquisado = evento.getNome().toUpperCase();
		for (EventoSGVEN te : lista) {
			if (te.getNome().toUpperCase().indexOf(nomePesquisado) != -1) {
					listaRetorno.add(te);
			}
		}
		}
		return listaRetorno;
	}
	
	@Override
	public EventoSGVEN atualizar(EventoSGVEN entity) {
		return entity;
	}

	@Override
	public void excluir(EventoSGVEN entity) {
		
	}

	private List<EventoSGVEN> getListaEvento() {
		List<EventoSGVEN> lista = new ArrayList<EventoSGVEN>();
		EventoSGVEN te1 = new EventoSGVEN();
		lista.add(te1);
		
		
		te1.setId(1L);
		te1.setNome("Evento 1");
		
		EventoSGVEN te2 = new EventoSGVEN();
		lista.add(te2);
		te2.setId(2L);
		te2.setNome("Evento 2");
		
		EventoSGVEN te3 = new EventoSGVEN();
		lista.add(te3);
		te3.setId(3L);
		te3.setNome("Evento 3");
		
		return lista;
	}
	
	
	@Override
	public boolean verificaExisteEventoPorTipoEvento(TipoEventoSGVEN tipoEvento) {
		if (tipoEvento.getNome().equals("Tipo Evento 1"))
			return true;
		return false;
	}
	
}
