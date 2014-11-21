package br.jus.stj.sigeven.business.tipoevento;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;
import br.jus.stj.sigeven.persistence.TipoEventoDAO;
import br.jus.stj.sigeven.persistence.TipoEventoDAOImpl;

@Alternative
public class TipoEventoDAOMock extends TipoEventoDAOImpl implements
		TipoEventoDAO {

	@Override
	public List<TipoEventoSGVEN> recuperarTodos(Class<TipoEventoSGVEN> entityClass) {
		return getListaTipoEvento();
	}

	@Override
	public TipoEventoSGVEN recuperarPorNome(String nome) {
		List<TipoEventoSGVEN> lista = getListaTipoEvento();
		for (TipoEventoSGVEN tipoEvento : lista) {
			if (tipoEvento.getNome().equalsIgnoreCase(nome))
				return tipoEvento;
		}
		return null;
	}
	
	@Override
	public TipoEventoSGVEN incluir(TipoEventoSGVEN entity) {
		entity.setId(10L);
		return entity;
	}

	@Override
	public TipoEventoSGVEN recuperarPorId(Long id) {
		List<TipoEventoSGVEN> lista = getListaTipoEvento();
		for (TipoEventoSGVEN tipoEvento : lista) {
			if (tipoEvento.getId().longValue() == id.longValue())
				return tipoEvento;
		}
		return null;
	}
	
	@Override
	public List<TipoEventoSGVEN> pesquisar(TipoEventoSGVEN tipoEvento) {
		List<TipoEventoSGVEN> listaRetorno = new ArrayList<TipoEventoSGVEN>();
		List<TipoEventoSGVEN> lista = getListaTipoEvento();
		String nomePesquisado = tipoEvento.getNome().toUpperCase();
		String ativoPesquisado = String.valueOf(tipoEvento.getAtivo());
		for (TipoEventoSGVEN te : lista) {
			if (te.getNome().toUpperCase().indexOf(nomePesquisado) != -1) {
				if (ativoPesquisado == null || (ativoPesquisado != null && ativoPesquisado.equals(te.getAtivo())))
					listaRetorno.add(te);
			}
		}
		return listaRetorno;
	}
	
	@Override
	public TipoEventoSGVEN atualizar(TipoEventoSGVEN entity) {
		return entity;
	}

	@Override
	public void excluir(TipoEventoSGVEN entity) {
		
	}

	private List<TipoEventoSGVEN> getListaTipoEvento() {
		List<TipoEventoSGVEN> lista = new ArrayList<TipoEventoSGVEN>();
		TipoEventoSGVEN te1 = new TipoEventoSGVEN();
		lista.add(te1);
		
		
		te1.setId(1L);
		te1.setNome("Tipo Evento 1");
		te1.setDescricao("Descricao do TE1");
		te1.setAtivo('S');
		
		TipoEventoSGVEN te2 = new TipoEventoSGVEN();
		lista.add(te2);
		te2.setId(2L);
		te2.setNome("Tipo Evento 2");
		te2.setDescricao("Descricao do TE2");
		te2.setAtivo('S');
		
		TipoEventoSGVEN te3 = new TipoEventoSGVEN();
		lista.add(te3);
		te3.setId(3L);
		te3.setNome("Tipo Evento 3");
		te3.setDescricao("Descricao do TE3");
		te3.setAtivo('S');
		
		return lista;
	}
	
}
