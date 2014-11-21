package br.jus.stj.sigeven.business.grupoParticipante;


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

import br.jus.stj.sigeven.business.grupoparticipante.GrupoParticipanteBean;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.GrupoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;

//@RunWith(CdiRunner.class)
//@ActivatedAlternatives({GrupoParticipanteDAOMock.class})
public class GrupoParticipanteBeanTest {
	
//	@Inject
//	private GrupoParticipanteBean grupoParticipanteBean;
//	
//	private EventoSGVEN evento;
//	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//	}
//	
//	@Test
//	public void incluirGrupoParticipanteInexistente() {
//		GrupoParticipanteEventoSGVEN grupoParticipante = new GrupoParticipanteEventoSGVEN();
//		grupoParticipante.getGrupo().setNome("Grupo Participante Inexistente");
//		assertTrue(grupoParticipanteBean.incluir(grupoParticipante));
//	}
//	
//	@Test
//	public void incluirGrupoParticipanteExistente() {
//		GrupoParticipanteEventoSGVEN grupoParticipante = new GrupoParticipanteEventoSGVEN();
//		grupoParticipante.getGrupo().setNome("Grupo Participante 1");
//		assertFalse(grupoParticipanteBean.incluir(grupoParticipante));
//	}
//	
//	@Test
//	public void recuperarTodos() {
//		List<GrupoParticipanteEventoSGVEN> lista = grupoParticipanteBean.recuperarTodos();
//		assertTrue(lista != null && lista.size() > 0);
//	}
//	
//	@Test
//	public void recuperarPorId() {
//		GrupoParticipanteEventoSGVEN grupoParticipante = grupoParticipanteBean.recuperarPorId(1L);
//		assertTrue(grupoParticipante.getId().longValue() == 1L);
//	}
//	
//	
//	@Test
//	public void alterarQuandoNomeInexiste() {
//		GrupoParticipanteEventoSGVEN grupoParticipante = new GrupoParticipanteEventoSGVEN();
//		grupoParticipante.getGrupo().setNome("Grupo Participante Inexistente");
//		assertTrue(grupoParticipanteBean.alterar(grupoParticipante, evento));
//	}
//	
//	@Test
//	public void alterarQuandoNomeExiste() {
//		GrupoParticipanteEventoSGVEN grupoParticipante = new GrupoParticipanteEventoSGVEN();
//		grupoParticipante.getParticipanteEvento().setId(999L);
//		grupoParticipante.getGrupo().setNome("Grupo Participante 1");
//		assertFalse(grupoParticipanteBean.alterar(grupoParticipante, evento));
//	}
//	
//	
//	@Test
//	public void excluirQuandoExistir() {
//		GrupoParticipanteEventoSGVEN grupoParticipante = new GrupoParticipanteEventoSGVEN();
//		List<ParticipanteEventoSGVEN> listParticip = new ArrayList<ParticipanteEventoSGVEN>();
//		ParticipanteEventoSGVEN particioante = new ParticipanteEventoSGVEN();
//		particioante.setNome("Participante 1");
//		listParticip.add(particioante);
//		grupoParticipante.setParticipante(listParticip);
//		assertFalse(grupoParticipanteBean.excluir(grupoParticipante));
//	}
//
//	@Test
//	public void excluirQuandoNaoExistir() {
//		GrupoParticipanteEventoSGVEN grupoParticipante = new GrupoParticipanteEventoSGVEN();
//		grupoParticipante.getGrupo().setNome("Grupo Participante 2");
//		assertTrue(grupoParticipanteBean.excluir(grupoParticipante));
//	}
//	
//	@Test
//	public void recuperarTodosPorEvento() {
//		grupoParticipanteBean.recuperarTodosPorEvento(evento);
//	}
//
//	
//	public EventoSGVEN getEvento() {
//		if(evento==null)
//			evento = new EventoSGVEN();
//		evento.setId(1L);
//		evento.setNome("Evento Teste");
//		return evento;
//	}
//
//	public void setEvento(EventoSGVEN evento) {
//		this.evento = evento;
//	}
	
	

}
