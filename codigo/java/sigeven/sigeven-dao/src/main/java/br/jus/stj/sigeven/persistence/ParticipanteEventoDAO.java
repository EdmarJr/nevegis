package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.to.FiltroPesquisaParticipanteEventoTO;

public interface ParticipanteEventoDAO extends GenericDAO<ParticipanteEventoSGVEN> {
	List<ParticipanteEventoSGVEN> obterParticipantesPorParametro(FiltroPesquisaParticipanteEventoTO filtro, EventoSGVEN evento);
}
