package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class PoderAreaDAOImpl extends GenericDAODb2Impl<PoderMalaMDIR>
		implements PoderAreaDAO {

	public List<PoderMalaMDIR> obterPoderAreaVinculadasAoEvento(EventoSGVEN evento) {
		Criteria criteriaOrgao = obterCriteria(PoderMalaMDIR.class);
		Criteria criteriaRelacionamentos = criteriaOrgao.createCriteria("relacionamentosParticipanteOrgaoCargo");
		Criteria criteriaParticipanteEvento = criteriaRelacionamentos.createCriteria("participanteEvento");
		criteriaParticipanteEvento.add(Restrictions.eq("evento", evento));
		/*@SuppressWarnings("unchecked")
		List<OrgaoMDIR> list = criteriaOrgao.list();

		ArrayList<PoderMalaMDIR> listaRetorno = new ArrayList<PoderMalaMDIR>();
		for(OrgaoMDIR orgao : list) {
			if(orgao.getPoderArea() != null) {
				listaRetorno.add(orgao.getPoderArea());
			}
		}
		
		
*/		return criteriaOrgao.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoderMalaMDIR> obterPorNome(String nome) {
		Criteria criteria = obterCriteria(PoderMalaMDIR.class);
		criteria.add(Restrictions.eq("nome", nome));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PoderMalaMDIR> obterPorId(Long id){
		Criteria criteria = obterCriteria(PoderMalaMDIR.class);
		criteria.add(Restrictions.eq("id", id));
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PoderMalaMDIR> pesquisar(PoderMalaMDIR poderArea) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select orgao from PoderMalaMDIR orgao ");
		jpql.append("where orgao.nome like :nome ");
		
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("nome", '%' + poderArea.getNome() + '%');
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PoderMalaMDIR recuperarPorNome(String nome) {
		String jpql = "select poderArea from PoderMalaMDIR poderArea "
				+ "where poderArea.nome = :nome";
	
		Query query = manager.createQuery(jpql);
		query.setParameter("nome", nome);
		List<PoderMalaMDIR> lista = query.getResultList();
		if (lista == null || lista.isEmpty())
			return null;
		return lista.get(0);
	}
	
	
}
