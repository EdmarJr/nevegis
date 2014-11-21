package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.EsferaMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;

public interface EsferaDAO extends GenericDAO<EsferaMDIR> {

	public List<EsferaMDIR> obterEsferasVinculadasAoEvento(EventoSGVEN evento);
}
