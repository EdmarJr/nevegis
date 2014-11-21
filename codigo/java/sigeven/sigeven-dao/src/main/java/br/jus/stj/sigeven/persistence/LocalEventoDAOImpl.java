package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.sigeven.LocalEventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class LocalEventoDAOImpl extends GenericDAODb2Impl<LocalEventoSGVEN> implements
LocalEventoDAO {

	@Override
	public LocalEventoSGVEN recuperarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocalEventoSGVEN> pesquisar(LocalEventoSGVEN localEvento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalEventoSGVEN recuperarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
