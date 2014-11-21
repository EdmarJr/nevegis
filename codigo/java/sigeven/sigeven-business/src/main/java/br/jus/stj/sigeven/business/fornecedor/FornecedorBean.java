package br.jus.stj.sigeven.business.fornecedor;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.entity.db2.sigeven.FornecedorSGVEN;
import br.jus.stj.sigeven.persistence.FornecedorDAO;
import br.jus.stj.sigeven.persistence.GenericDAO;

@Stateless
public class FornecedorBean  extends GenericBeanMssql<FornecedorSGVEN>{
	
	@Inject
	private FornecedorDAO dao;
	
	@Override
	protected GenericDAO<FornecedorSGVEN> getDao() {
		return dao;
	}

	public List<FornecedorSGVEN> recupearTodos(){
		
		return dao.recuperarTodos(FornecedorSGVEN.class);
	}
	
}
