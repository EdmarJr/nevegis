package br.jus.stj.sigeven.business.servicoprestadoevento;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ServicoPrestadoEventoSGVEN;
import br.jus.stj.sigeven.persistence.GenericDAO;
import br.jus.stj.sigeven.persistence.ServicoPrestadoEventoDAO;

@Stateless
public class ServicoPrestadoEventoBean  extends GenericBeanMssql<ServicoPrestadoEventoSGVEN>{
	
	@Inject
	private ServicoPrestadoEventoDAO dao;
	
	@Override
	protected GenericDAO<ServicoPrestadoEventoSGVEN> getDao() {
		return dao;
	}

	public List<ServicoPrestadoEventoSGVEN> recupearTodos(){
		
		return dao.recuperarTodos(ServicoPrestadoEventoSGVEN.class);
	}
	
	public List<ServicoPrestadoEventoSGVEN> recuperarTodosPorEvento(EventoSGVEN eventoSGVEN){
		
		return dao.recuperarTodosPorEvento(eventoSGVEN);
	}
	
	public void excluir(ServicoPrestadoEventoSGVEN entidade) {
	
		dao.excluir(entidade);
	}
	

	
}
