package br.jus.stj.sigeven.business.evento;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.persistence.OrgaoDAO;
import br.jus.stj.sigeven.persistence.OrgaoDAOImpl;

@Alternative
public class OrgaoDb2DAOMock extends OrgaoDAOImpl implements OrgaoDAO{

	@Override
	public List<OrgaoMDIR> recuperarTodos(Class<OrgaoMDIR> entityClass) {
		List<OrgaoMDIR> listaRetorno = new ArrayList<OrgaoMDIR>();
		for(int i =1;i<=10;i++){
			OrgaoMDIR populador =  new OrgaoMDIR();
			populador.getPoderArea().setId((long) i);
			populador.getPoderArea().setNome("Orgão "+i);
			listaRetorno.add(populador);
		}
		return listaRetorno;
	}
	
}
