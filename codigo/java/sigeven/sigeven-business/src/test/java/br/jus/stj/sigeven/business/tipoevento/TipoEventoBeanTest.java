package br.jus.stj.sigeven.business.tipoevento;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import br.jus.stj.sigeven.business.evento.EventoDAOMock;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;

//@RunWith(CdiRunner.class)
//@ActivatedAlternatives({TipoEventoDAOMock.class, EventoDAOMock.class})
public class TipoEventoBeanTest {
	
//	@Inject
//	private TipoEventoBean tipoEventoBean;
//	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//	}
//	
//	@Test
//	public void incluirTipoEventoInexistente() {
//		TipoEventoSGVEN tipoEvento = new TipoEventoSGVEN();
//		tipoEvento.setNome("Tipo Evento Inexistente");
//		tipoEvento.setDescricao("Teste Unitário");
//		tipoEvento.setAtivo('S');
//		assertTrue(tipoEventoBean.incluir(tipoEvento));
//	}
//	
//	@Test
//	public void incluirTipoEventoExistente() {
//		TipoEventoSGVEN tipoEvento = new TipoEventoSGVEN();
//		tipoEvento.setNome("Tipo Evento 1");
//		tipoEvento.setDescricao("Teste Unitário");
//		tipoEvento.setAtivo('S');
//		assertFalse(tipoEventoBean.incluir(tipoEvento));
//	}
//	
//	@Test
//	public void recuperarTodos() {
//		List<TipoEventoSGVEN> lista = tipoEventoBean.recuperarTodos();
//		assertTrue(lista != null && lista.size() > 0);
//	}
//	
//	@Test
//	public void recuperarPorId() {
//		TipoEventoSGVEN tipoEvento = tipoEventoBean.recuperarPorId(1L);
//		assertTrue(tipoEvento.getId().longValue() == 1L);
//	}
//	
//	
//	
//	@Test
//	public void alterarQuandoNomeInexiste() {
//		TipoEventoSGVEN tipoEvento = new TipoEventoSGVEN();
//		tipoEvento.setNome("Tipo Evento Inexistente");
//		tipoEvento.setDescricao("Teste Unitário");
//		tipoEvento.setAtivo('S');
//		assertTrue(tipoEventoBean.alterar(tipoEvento));
//	}
//	
//	@Test
//	public void alterarQuandoNomeExiste() {
//		TipoEventoSGVEN tipoEvento = new TipoEventoSGVEN();
//		tipoEvento.setId(999L);
//		tipoEvento.setNome("Tipo Evento 1");
//		tipoEvento.setDescricao("Teste Unitário");
//		tipoEvento.setAtivo('S');
//		assertFalse(tipoEventoBean.alterar(tipoEvento));
//	}
//	
//	@Test
//	public void alterarStatus() {
//		TipoEventoSGVEN tipoEvento = new TipoEventoSGVEN();
//		tipoEvento.setId(1L);
//		tipoEvento.setNome("Tipo Evento 1");
//		tipoEvento.setDescricao("Teste Unitário");
//		tipoEvento.setAtivo('N');
//		assertTrue(tipoEventoBean.alterar(tipoEvento));
//	}
//	
//	@Test
//	public void excluirQuandoExistir() {
//		TipoEventoSGVEN tipoEvento = new TipoEventoSGVEN();
//		tipoEvento.setNome("Tipo Evento 1");
//		tipoEvento.setDescricao("Teste Unitário");
//		tipoEvento.setAtivo('S');
//		assertFalse(tipoEventoBean.excluir(tipoEvento));
//	}
//
//	@Test
//	public void excluirQuandoNaoExistir() {
//		TipoEventoSGVEN tipoEvento = new TipoEventoSGVEN();
//		tipoEvento.setNome("Tipo Evento 2");
//		tipoEvento.setDescricao("Teste Unitário");
//		tipoEvento.setAtivo('S');
//		assertTrue(tipoEventoBean.excluir(tipoEvento));
//	}

}
