package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;

public interface ParticipanteOrgaoCargoEventoDAO extends GenericDAO<OcupacaoParticipanteEventoSGVEN> {
	
	public List<OcupacaoParticipanteEventoSGVEN> recuperarTodosPorOrgao(
			OrgaoMDIR orgao);
	
}
