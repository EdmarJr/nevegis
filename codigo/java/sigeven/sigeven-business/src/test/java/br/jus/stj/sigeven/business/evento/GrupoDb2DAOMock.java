package br.jus.stj.sigeven.business.evento;

import javax.enterprise.inject.Alternative;

import br.jus.stj.sigeven.persistence.GrupoParticipanteDAO;
import br.jus.stj.sigeven.persistence.GrupoParticipanteDAOImpl;

@Alternative
public class GrupoDb2DAOMock extends GrupoParticipanteDAOImpl implements GrupoParticipanteDAO{

//	@Override
//	public List<GrupoMDIR> recuperarTodos(Class<GrupoMDIR> entityClass) {
//		List<GrupoMDIR> listaRetorno = new ArrayList<GrupoMDIR>();
//		for(int i =1;i<=10;i++){
//			GrupoMDIR populador =  new GrupoMDIR();
//			populador.setId((long) i);
//			populador.setNome("Grupo "+i);
//			listaRetorno.add(populador);
//		}
//		return listaRetorno;
//	}
	
}
