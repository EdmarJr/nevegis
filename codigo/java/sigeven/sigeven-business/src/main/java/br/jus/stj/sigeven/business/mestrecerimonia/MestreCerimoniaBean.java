package br.jus.stj.sigeven.business.mestrecerimonia;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.entity.db2.sigeven.MestreCerimoniaSGVEN;
import br.jus.stj.sigeven.persistence.GenericDAO;
import br.jus.stj.sigeven.persistence.MestreCerimoniaDAO;

@Stateless
public class MestreCerimoniaBean  extends GenericBeanMssql<MestreCerimoniaSGVEN>{

	
	@Inject
	private MestreCerimoniaDAO dao;

	@Override
	protected GenericDAO<MestreCerimoniaSGVEN> getDao() {
		return dao;
	}
	
	
	public List<MestreCerimoniaSGVEN> recuperarTodos(){
		
		return dao.recuperarTodos(MestreCerimoniaSGVEN.class);
	}
	
	
}
