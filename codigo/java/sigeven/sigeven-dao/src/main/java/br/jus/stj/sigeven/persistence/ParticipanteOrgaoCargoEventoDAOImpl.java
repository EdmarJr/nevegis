package br.jus.stj.sigeven.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class ParticipanteOrgaoCargoEventoDAOImpl extends GenericDAODb2Impl<OcupacaoParticipanteEventoSGVEN>
		implements ParticipanteOrgaoCargoEventoDAO {
	
	
	@SuppressWarnings("unchecked")
	public List<OcupacaoParticipanteEventoSGVEN> recuperarTodosPorOrgao(
			OrgaoMDIR orgao) {
		
		Criteria criteria = obterCriteria(OcupacaoParticipanteEventoSGVEN.class);
		
		criteria.add(Restrictions.eq("orgao", orgao));
		
		return criteria.list();
	}


}
