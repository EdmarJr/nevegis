package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.sigeven.LocalEventoSGVEN;

public interface LocalEventoDAO extends GenericDAO<LocalEventoSGVEN> {
	
	LocalEventoSGVEN recuperarPorId(Long id);
	
	List<LocalEventoSGVEN> pesquisar(LocalEventoSGVEN localEvento);
	
	LocalEventoSGVEN recuperarPorNome(String nome);
}
