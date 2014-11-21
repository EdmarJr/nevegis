package br.jus.stj.sigeven.business.evento;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.persistence.CargoDAO;
import br.jus.stj.sigeven.persistence.CargoDAOImpl;

@Alternative
public class CargoDb2DAOMock extends CargoDAOImpl implements CargoDAO{

	@Override
	public List<CargoMDIR> recuperarTodos(Class<CargoMDIR> entityClass) {
		List<CargoMDIR> listaRetorno = new ArrayList<CargoMDIR>();
		for(long i =1;i<=10;i++){
			CargoMDIR populador =  new CargoMDIR();
			populador.setId(i);
			populador.setNome("Grupo "+i);
			listaRetorno.add(populador);
		}
		return listaRetorno;
	}	
	
}
