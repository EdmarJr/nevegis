package br.jus.stj.sigeven.business.evento;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;
import br.jus.stj.sigeven.persistence.TratamentoDAO;
import br.jus.stj.sigeven.persistence.TratamentoDAOImpl;

@Alternative
public class TratamentoDb2DAOMock extends TratamentoDAOImpl implements TratamentoDAO{

	@Override
	public List<TratamentoMDIR> recuperarTodos(Class<TratamentoMDIR> entityClass) {
		List<TratamentoMDIR> listaRetorno = new ArrayList<TratamentoMDIR>();
		for(int i =1;i<=10;i++){
			TratamentoMDIR populador =  new TratamentoMDIR();
			populador.setId((long) i);
			populador.setNome("Grupo "+i);
			listaRetorno.add(populador);
		}
		return listaRetorno;
	}
	
}
