package br.jus.stj.sigeven.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.sigeven.entity.db2.maladir.EsferaMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class EsferaDAOImpl extends GenericDAODb2Impl<EsferaMDIR>
		implements EsferaDAO {

	@SuppressWarnings("unchecked")
	public List<EsferaMDIR> obterEsferasVinculadasAoEvento(EventoSGVEN evento) {
		Criteria criteriaOrgao = obterCriteria(EsferaMDIR.class);
		Criteria criteriaRelacionamentos = criteriaOrgao.createCriteria("relacionamentosParticipanteOrgaoCargo");
		Criteria criteriaParticipanteEvento = criteriaRelacionamentos.createCriteria("participanteEvento");
		criteriaParticipanteEvento.add(Restrictions.eq("evento", evento));
		List<OrgaoMDIR> list = criteriaOrgao.list();

		ArrayList<EsferaMDIR> listaRetorno = new ArrayList<EsferaMDIR>();
		for(OrgaoMDIR orgao : list) {
			if(orgao.getEsfera() != null) {
				listaRetorno.add(orgao.getEsfera());
			}
		}
		//TODO
		return listaRetorno;
	}
	
}
