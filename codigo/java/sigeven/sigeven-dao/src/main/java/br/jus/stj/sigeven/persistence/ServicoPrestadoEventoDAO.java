package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ServicoPrestadoEventoSGVEN;

public interface ServicoPrestadoEventoDAO extends
		GenericDAO<ServicoPrestadoEventoSGVEN> {
	
	
	public List<ServicoPrestadoEventoSGVEN> recuperarTodosPorEvento(EventoSGVEN eventoSGVEN);
	
	public void excluir(ServicoPrestadoEventoSGVEN servico);
	
}
