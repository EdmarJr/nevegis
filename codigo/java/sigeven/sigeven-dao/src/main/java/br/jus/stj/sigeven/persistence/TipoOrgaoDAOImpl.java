package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import br.jus.stj.sigeven.entity.db2.maladir.TipoOrgaoMDIR;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class TipoOrgaoDAOImpl extends GenericDAODb2Impl<TipoOrgaoMDIR> implements
		TipoOrgaoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoOrgaoMDIR> pesquisar(TipoOrgaoMDIR tipoOrgao) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select to from TipoOrgaoMDIR to ");
		jpql.append("where to.nome like :nome ");
		
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("nome", '%' + tipoOrgao.getNome() + '%');
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TipoOrgaoMDIR recuperarPorNome(String nome) {
		String jpql = "select te from TipoOrgaoMDIR te "
					+ "where te.nome = :nome ";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("nome", nome);
		List<TipoOrgaoMDIR> lista = query.getResultList();
		if (lista == null || lista.isEmpty())
			return null;
		return lista.get(0);
	}	

}