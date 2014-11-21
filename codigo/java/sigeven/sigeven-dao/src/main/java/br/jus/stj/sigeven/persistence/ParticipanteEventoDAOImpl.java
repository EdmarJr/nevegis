package br.jus.stj.sigeven.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;
import br.jus.stj.sigeven.persistence.to.FiltroPesquisaParticipanteEventoTO;

public class ParticipanteEventoDAOImpl extends
		GenericDAODb2Impl<ParticipanteEventoSGVEN> implements ParticipanteEventoDAO {

	private Criteria criteriaPE;

	@SuppressWarnings("unchecked")
	public List<ParticipanteEventoSGVEN> obterParticipantesPorParametro(FiltroPesquisaParticipanteEventoTO filtro, EventoSGVEN evento) {

		Criteria criteriaOcupacao = null;
		Criteria criteriaOrgao = null;
		criteriaPE = obterCriteria(ParticipanteEventoSGVEN.class);
		criteriaPE.add(Restrictions.eq("evento", evento));
		 if (seFiltroPossuiJoins(filtro)) {
			criteriaOcupacao = criteriaPE.createCriteria("orgaosCargosEventos");
			criteriaOrgao = criteriaOcupacao.createCriteria("orgao");
		 }

		 if (filtro.getCargo() != null) {
			criteriaOcupacao.add(Restrictions.eq("cargo", filtro.getCargo()));
		}
		if (filtro.getOrgao() != null) {
			criteriaOcupacao.add(Restrictions.eq("orgao", filtro.getOrgao()));
		}
		if (filtro.getTratamento() != null) {
			criteriaPE
					.add(Restrictions.eq("tratamento", filtro.getTratamento()));
		}
		if (filtro.getCidade() != null && !filtro.getCidade().equals("")) {
			criteriaPE.add(Restrictions.like("cidade", filtro.getCidade(),
					MatchMode.ANYWHERE));
		}
		if (filtro.getNome() != null && !filtro.getNome().equals("")) {
			criteriaPE.add(Restrictions.like("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}
		if (filtro.getUf() != null && !filtro.getUf().equals("")) {
			criteriaPE.add(Restrictions.eq("uf", filtro.getUf()));
		}
		if (filtro.getPoderArea() != null) {
			criteriaOrgao.add(Restrictions.eq("poderArea",
					filtro.getPoderArea()));
		}
		if (filtro.getEsfera() != null) {
			criteriaOrgao.add(Restrictions.eq("esfera", filtro.getEsfera()));
		}
		char ch = 0;
		if (filtro.getSexo() != null && !(filtro.getSexo() == ch)) {
			criteriaPE.add(Restrictions.eq("sexo", filtro.getSexo()));
		}
		if (filtro.getPresencaConfirmada() != null
				&& filtro.getPresencaConfirmada()) {
			// TODO
		}
		if (filtro.getPresencaNaoConfirmada() != null
				&& filtro.getPresencaNaoConfirmada()) {
			// TODO
		}
		return criteriaPE.list();
	}

	private boolean seFiltroPossuiJoins(
			FiltroPesquisaParticipanteEventoTO filtro) {
		return filtro.getCargo() != null || filtro.getOrgao() != null || filtro.getPoderArea() != null || filtro.getEsfera() != null;
	}
	
}
