package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;

public interface EventoDAO extends GenericDAO<EventoSGVEN> {
	
	EventoSGVEN recuperarPorId(Long id);
	
	List<EventoSGVEN> pesquisar(EventoSGVEN tipoEvento);
	
	EventoSGVEN recuperarPorNome(String nome);

	List<EventoSGVEN> consultaEvento(EventoSGVEN evento);
	
	EventoSGVEN recuperarEventoComParticipantes(Long id);

	boolean verificaExisteEventoPorTipoEvento(TipoEventoSGVEN tipoEvento);
	
	List<EventoSGVEN> recuperarTodos(EventoSGVEN evento);
	
}
