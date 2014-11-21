package br.jus.stj.sigeven.business.tiposervico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoServicoSGVEN;
import br.jus.stj.sigeven.persistence.GenericDAO;
import br.jus.stj.sigeven.persistence.TipoServicoDAO;


@Stateless
public class TipoServicoBean extends GenericBeanMssql<TipoServicoSGVEN> {

	 
	@Inject
	private TipoServicoDAO dao;

	@Override
	protected GenericDAO<TipoServicoSGVEN> getDao() {
		
		return dao;
	}
	
	public List<TipoServicoSGVEN> recupearTodos() {

		return dao.recuperarTodos(TipoServicoSGVEN.class);
	}
}
