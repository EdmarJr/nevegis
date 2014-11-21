package br.jus.stj.sigeven.business.participanteorgaocargoevento;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.ParticipanteOrgaoCargoEventoDAO;

/**
 * Session Bean implementation class ParticipanteOrgaoCargoEventoBean
 */
@Stateless
@LocalBean
public class ParticipanteOrgaoCargoEventoBean {

	@Inject
	private ParticipanteOrgaoCargoEventoDAO dao;

	public ParticipanteOrgaoCargoEventoBean() {
		// TODO Auto-generated constructor stub
	}
	
	public void incluir(OcupacaoParticipanteEventoSGVEN participanteOrgaoCargoEvento) {
		dao.incluir(participanteOrgaoCargoEvento);
	}
	
	public void removerRegistrosOrfaos() {
		// TODO Auto-generated method stub

	}

	public void excluir(OcupacaoParticipanteEventoSGVEN p) {
		dao.excluir(p);
	}

	public List<OcupacaoParticipanteEventoSGVEN> recuperarTodosPorOrgao(
			OrgaoMDIR orgao){
		
		return dao.recuperarTodosPorOrgao(orgao);
	}
}

