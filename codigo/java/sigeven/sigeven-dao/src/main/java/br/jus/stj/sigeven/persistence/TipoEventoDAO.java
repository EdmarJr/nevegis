package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;

public interface TipoEventoDAO extends GenericDAO<TipoEventoSGVEN> {
	
	TipoEventoSGVEN recuperarPorId(Long id);
	
	List<TipoEventoSGVEN> pesquisar(TipoEventoSGVEN tipoEvento);
	
	TipoEventoSGVEN recuperarPorNome(String nome);
		
}
