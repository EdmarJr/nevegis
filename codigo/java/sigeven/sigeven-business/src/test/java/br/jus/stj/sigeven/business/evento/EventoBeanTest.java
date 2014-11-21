package br.jus.stj.sigeven.business.evento;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({EventoDAOMock.class,OrgaoDb2DAOMock.class,CargoDb2DAOMock.class,TratamentoDb2DAOMock.class,GrupoDb2DAOMock.class})
public class EventoBeanTest {
	
//	@Inject
//	private EventoBean eventoBean;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void incluir() {
		EventoSGVEN evento = new EventoSGVEN();
		evento.setNome("Teste 2");
		
		//assertTrue(eventoBean.incluir(evento));
	}
	
	
//	@Test
//	public void populaGrupoImportado() {
//		assertTrue(true);
//	}
//
//	@Test
//	public void populaTratamentoImportado() {
//		assertTrue(true);
//	}
//
//	@Test
//	public void populaOrgaoImportado() {
//		assertTrue(true);
//	}
//
//
//	@Test
//	public void populaCargoImportado() {
//		assertTrue(true);
//	}

//	@Test
//	public void verificaMesmoNomeincluir() {
//		EventoSGVEN evento = new EventoSGVEN();
//		evento.setNome("Evento 1");
//		assertTrue(eventoBean.verificaMesmoNomeincluir(evento));
//	}
	
//	@Test
//	public void recuperarTodos() {
//		List<EventoSGVEN> lista = eventoBean.recuperarTodos();
//		assertTrue(lista != null && lista.size() > 0);
//	}

	
//	@Test
//	public void recuperarPorId() {
//		EventoSGVEN evento = eventoBean.recuperarPorId(1L);
//		assertTrue(evento.getId().longValue() == 1L);
//	}
	
//	@Test
//	public void pesquisarAlgumCampoPreenchido() {
//		EventoSGVEN evento = new EventoSGVEN();
//		evento.setNome("Evento");
//		List<EventoSGVEN> lista = eventoBean.pesquisar(evento);
//		assertTrue(lista.size() > 0);
//	}
	
//	@Test
//	public void alterarQuandoNomeInexiste() {
//		EventoSGVEN evento = new EventoSGVEN();
//		evento.setNome("Evento Inexistente");
//		assertTrue(eventoBean.alterar(evento));
//	}
	
	
//	@Test
//	public void excluir() {
//		EventoSGVEN evento = new EventoSGVEN();
//		evento.setNome("Evento 1");
//		eventoBean.excluir(evento);
//		assertTrue(evento.equals(new EventoSGVEN()));
//	}

//	@Test
//	public void pesquisaPorParametrosAlgumCampoPreenchido() {
//		EventoSGVEN evento = new EventoSGVEN();
//		evento.setNome("Evento");
//		List<EventoSGVEN> lista = eventoBean.pesquisar(evento);
//		assertTrue(lista.size() > 0);
//	}
	
//	@Test
//	public void pesquisaPorParametrosNenhumCampoPreenchido() {
//		EventoSGVEN evento = new EventoSGVEN();
//		List<EventoSGVEN> lista = new ArrayList<>();
//		lista = eventoBean.pesquisaPorParametros(evento);
//		assertFalse(lista.size() > 0);
//	}
	
}
