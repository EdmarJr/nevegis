package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class GrupoParticipanteDAOImpl extends GenericDAODb2Impl<GrupoMDIR> implements
		GrupoParticipanteDAO {

	@Override
	public GrupoMDIR recuperarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoMDIR> pesquisar(GrupoMDIR grupo) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select te from GrupoMDIR te ");
		jpql.append("where te.nome = :nome ");
		
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("nome", grupo.getNome());
		return query.getResultList();
	}	
	
	@Override
	public GrupoMDIR recuperarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
