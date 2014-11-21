package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class TratamentoDAOImpl extends GenericDAODb2Impl<TratamentoMDIR> implements
		TratamentoDAO {
	
	@Override
	public TratamentoMDIR recuperarPorId(Long id) {
		return manager.getReference(TratamentoMDIR.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TratamentoMDIR> pesquisar(TratamentoMDIR tratamento) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select te from TratamentoMDIR te ");
		jpql.append("where te.nome like :nome ");
		
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("nome", '%' + tratamento.getNome() + '%');
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TratamentoMDIR recuperarPorNome(String nome) {
		String jpql = "select te from TratamentoMDIR te "
					+ "where te.nome = :nome ";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("nome", nome);
		List<TratamentoMDIR> lista = query.getResultList();
		if (lista == null || lista.isEmpty())
			return null;
		return lista.get(0);
	}
	
	public List<TratamentoMDIR> obterTratamentosDisponiveisInclusao() {
		return recuperarTodos(TratamentoMDIR.class);
	}
	@SuppressWarnings("unchecked")
	public List<TratamentoMDIR> obterTratamentosVinculadosAoEvento(EventoSGVEN evento) {
		Criteria criteriaTratamento = obterCriteria(TratamentoMDIR.class);
		Criteria criteriaRelacionamentos = criteriaTratamento.createCriteria("relacionamentosParticipanteOrgaoCargo");
		Criteria criteriaParticipanteEvento = criteriaRelacionamentos.createCriteria("participanteEvento");
		criteriaParticipanteEvento.add(Restrictions.eq("evento", evento));
		return criteriaTratamento.list();
	}
	
}
