package br.jus.stj.sigeven.business.esfera;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.maladir.EsferaMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.persistence.EsferaDAO;

@Stateless
public class EsferaBean {

	@Inject
	private EsferaDAO dao;
	
	public List<EsferaMDIR> obterEsferasVinculadasAoEvento(EventoSGVEN evento) {
		return dao.obterEsferasVinculadasAoEvento(evento);
	}

	public List<EsferaMDIR> obterEsferasDisponiveis() {
		return dao.recuperarTodos(EsferaMDIR.class);
	}
}
