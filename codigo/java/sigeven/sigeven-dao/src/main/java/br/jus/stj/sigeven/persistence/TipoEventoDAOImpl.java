package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class TipoEventoDAOImpl extends GenericDAODb2Impl<TipoEventoSGVEN> implements
		TipoEventoDAO {
	
	@Override
	public TipoEventoSGVEN recuperarPorId(Long id) {
		return manager.getReference(TipoEventoSGVEN.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoEventoSGVEN> pesquisar(TipoEventoSGVEN tipoEvento) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select te from TipoEventoSGVEN te ");
		jpql.append("where te.nome like :nome ");
		jpql.append("and te.ativo = :ativo ");
		
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("nome", '%' + tipoEvento.getNome() + '%');
		query.setParameter("ativo", tipoEvento.getAtivo());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TipoEventoSGVEN recuperarPorNome(String nome) {
		String jpql = "select te from TipoEventoSGVEN te "
					+ "where te.nome = :nome ";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("nome", nome);
		List<TipoEventoSGVEN> lista = query.getResultList();
		if (lista == null || lista.isEmpty())
			return null;
		return lista.get(0);
	}
	
}
