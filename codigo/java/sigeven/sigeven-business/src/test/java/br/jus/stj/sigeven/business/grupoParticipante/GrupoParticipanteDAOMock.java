package br.jus.stj.sigeven.business.grupoParticipante;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.GrupoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.GrupoParticipanteDAO;
import br.jus.stj.sigeven.persistence.GrupoParticipanteDAOImpl;

@Alternative
public class GrupoParticipanteDAOMock extends GrupoParticipanteDAOImpl implements
		GrupoParticipanteDAO {

	@Override
	public List<GrupoMDIR> recuperarTodos(Class<GrupoMDIR> entityClass) {
		return getListaGrupoParticipante();
	}

	@Override
	public GrupoMDIR recuperarPorNome(String nome) {
		List<GrupoMDIR> lista = getListaGrupoParticipante();
//		for (GrupoMDIR grupoParticipante : lista) {
//			if (grupoParticipante.getParticipanteEvento().getNome().equalsIgnoreCase(nome))
//				return grupoParticipante;
//		}
		return null;
	}
	
	@Override
	public GrupoMDIR incluir(GrupoMDIR entity) {
//		entity.getParticipanteEvento().setId(10L);
		return entity;
	}

	@Override
	public GrupoMDIR recuperarPorId(Long id) {
		List<GrupoMDIR> lista = getListaGrupoParticipante();
		for (GrupoMDIR grupoParticipante : lista) {
			if (grupoParticipante.getId().longValue() == id.longValue())
				return grupoParticipante;
		}
		return null;
	}
	
	@Override
	public List<GrupoMDIR> pesquisar(GrupoMDIR grupoParticipante) {
		List<GrupoMDIR> listaRetorno = new ArrayList<GrupoMDIR>();
		List<GrupoMDIR> lista = getListaGrupoParticipante();
//		String nomePesquisado = grupoParticipante.getParticipanteEvento().getNome().toUpperCase();
//		for (GrupoMDIR te : lista) {
//			if (te.getGrupo().getNome().toUpperCase().indexOf(nomePesquisado) != -1) {
//				listaRetorno.add(te);
//			}
//		}
		return listaRetorno;
	}
	
	@Override
	public GrupoMDIR atualizar(GrupoMDIR entity) {
		return entity;
	}

	@Override
	public void excluir(GrupoMDIR entity) {
		
	}


	
	
	private List<GrupoMDIR> getListaGrupoParticipante() {
		List<GrupoMDIR> lista = new ArrayList<GrupoMDIR>();
		GrupoMDIR te1 = new GrupoMDIR();
		lista.add(te1);
		
		
//		te1.getGrupo().setId(1L);
//		te1.getGrupo().setNome("Grupo Participante 1");
		
		GrupoMDIR te2 = new GrupoMDIR();
		lista.add(te2);
//		te2.getGrupo().setId(2L);
//		te2.getGrupo().setNome("Grupo Participante 2");
		
		GrupoMDIR te3 = new GrupoMDIR();
		lista.add(te3);
//		te3.getGrupo().setId(3L);
//		te3.getGrupo().setNome("Grupo Participante 3");
		
		return lista;
	}
	
}
