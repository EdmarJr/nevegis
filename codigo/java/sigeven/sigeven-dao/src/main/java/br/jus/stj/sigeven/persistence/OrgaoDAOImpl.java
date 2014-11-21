package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.commons.util.Utils;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class OrgaoDAOImpl extends GenericDAODb2Impl<OrgaoMDIR> implements
		OrgaoDAO {

	public List<OrgaoMDIR> obterOrgaosDisponiveisInclusao() {
		return recuperarTodos(OrgaoMDIR.class);
	}

	@SuppressWarnings("unchecked")
	public List<OrgaoMDIR> obterOrgaosVinculadosAoEvento(EventoSGVEN evento) {
		Criteria criteriaOrgao = obterCriteria(OrgaoMDIR.class);
		Criteria criteriaRelacionamentos = criteriaOrgao
				.createCriteria("relacionamentosParticipanteOrgaoCargo");
		Criteria criteriaParticipanteEvento = criteriaRelacionamentos
				.createCriteria("participanteEvento");
		criteriaParticipanteEvento.add(Restrictions.eq("evento", evento));
		return criteriaOrgao.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrgaoMDIR recuperarPorNome(String nome) {
		String jpql = "select te from OrgaoMDIR te "
				+ "where te.nome = :nome";

		Query query = manager.createQuery(jpql);
		query.setParameter("nome", nome);
		List<OrgaoMDIR> lista = query.getResultList();
		if (lista == null || lista.isEmpty())
			return null;
		return lista.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<OrgaoMDIR> obterOrgaosPorNomeEEvento(String nome,
			EventoSGVEN evento) {
		Criteria criteria = obterCriteria(OrgaoMDIR.class);
		criteria.add(Restrictions.eq("nome", nome));
		criteria.add(Restrictions.eq("evento", evento));
		return criteria.list();
	}

	public OrgaoMDIR obterOrgaoERelacionamentos(OrgaoMDIR orgao) {
		OrgaoMDIR orgaoTemp = sincronizar(orgao);
		Hibernate.initialize(orgaoTemp
				.getRelacionamentosParticipanteOrgaoCargo());
		return orgaoTemp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrgaoMDIR> pesquisar(OrgaoMDIR orgao) {
		Criteria criteria = obterCriteria(OrgaoMDIR.class);
		if (!Utils.seVazioOuNulo(orgao.getNome())) {
			criteria.add(Restrictions.like("nome", orgao.getNome(),
					MatchMode.ANYWHERE));
		}
		if (!Utils.seVazioOuNulo(orgao.getSigla())) {
			criteria.add(Restrictions.like("sigla", orgao.getSigla(),
					MatchMode.ANYWHERE));
		}
		if(!Utils.seVazioOuNulo(orgao.getTipoOrgao())){
			criteria.add(Restrictions.eq("tipoOrgao", orgao.getTipoOrgao()));			
		}
		if(!Utils.seVazioOuNulo(orgao.getPoderArea())){
			criteria.add(Restrictions.eq("poderArea", orgao.getPoderArea()));			
		}
		if(!Utils.seVazioOuNulo(orgao.getEsfera())){
			criteria.add(Restrictions.eq("esfera", orgao.getEsfera()));			
		}
		return criteria.list();
	}

	
}
