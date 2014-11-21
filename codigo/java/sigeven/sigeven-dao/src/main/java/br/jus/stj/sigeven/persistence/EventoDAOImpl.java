package br.jus.stj.sigeven.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.commons.util.Utils;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class EventoDAOImpl extends GenericDAODb2Impl<EventoSGVEN> implements EventoDAO {

	@Override
	public EventoSGVEN recuperarPorId(Long id) {
		return manager.getReference(EventoSGVEN.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EventoSGVEN> pesquisar(EventoSGVEN evento) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select e from EventoSGVEN e ");
		jpql.append("where e.nome like :nome ");
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("nome", '%' + evento.getNome() + '%');
		List<EventoSGVEN> listaEvento = new ArrayList<EventoSGVEN>(); 
		listaEvento = query.getResultList();
		if(!listaEvento.isEmpty()){
			for(EventoSGVEN ev : listaEvento){
				Hibernate.initialize(ev.getParticipantes());
				for(ParticipanteEventoSGVEN part :  ev.getParticipantes()){
					Hibernate.initialize(part.getListaOcupacaoParticipacao());
				}
				
			}
		}
		return listaEvento;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EventoSGVEN recuperarPorNome(String nome) {
		String jpql = "select te from EventoSGVEN te " + "where te.nome = :nome ";

		Query query = manager.createQuery(jpql);
		query.setParameter("nome", nome);
		List<EventoSGVEN> lista = query.getResultList();
		if (lista == null || lista.isEmpty())
			return null;
		return lista.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EventoSGVEN> consultaEvento(EventoSGVEN evento) {
				
		StringBuilder sql = new StringBuilder();
		sql.append("select e from EventoSGVEN e where 1 = 1");

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		if (!Utils.seVazioOuNulo(evento.getNome())) {
			sql.append(" and e.nome like :nome");
			parametros.put("nome", "%"+evento.getNome()+"%");
		}
		if (!Utils.seVazioOuNulo(evento.getTipoEvento())) {
			sql.append(" and e.tipoEvento=:tipoEvento");
			parametros.put("tipoEvento", evento.getTipoEvento());
		}
		if (!Utils.seVazioOuNulo(evento.getDataInicio() )) {
			sql.append(" and e.dataInicio>=:dataInicio");
			parametros.put("dataInicio", evento.getDataInicio());
		}
		if (!Utils.seVazioOuNulo(evento.getDataFim())) {
			sql.append(" and e.dataFim<=:dataFim");
			parametros.put("dataFim", evento.getDataFim());
		}
		if (!Utils.seVazioOuNulo(evento.getStatus())) {
			sql.append(" and e.status=:status");
			parametros.put("status", evento.getStatus());
		}
		if (!Utils.seVazioOuNulo(evento.getLocal())) {
			sql.append(" and e.local=:local");
			parametros.put("local", evento.getLocal());
		}

		List<? extends Object> listaEvento = executarQueryJPQL(sql.toString(), parametros);
				
		if(!listaEvento.isEmpty()){
			for(EventoSGVEN ev : (List<EventoSGVEN>)listaEvento){
				Hibernate.initialize(ev.getParticipantes());
				for(ParticipanteEventoSGVEN part :  ev.getParticipantes()){
					Hibernate.initialize(part.getListaOcupacaoParticipacao());
				}
				
			}
		}
		
		return (List<EventoSGVEN>) listaEvento;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<EventoSGVEN> recuperarTodos(EventoSGVEN evento){
		StringBuilder sql = new StringBuilder();
		sql.append("select e from EventoSGVEN e");

		HashMap<String, Object> parametros = new HashMap<String, Object>();

		List<? extends Object> listaEvento = executarQueryJPQL(sql.toString(), parametros);
		
		
		if(!listaEvento.isEmpty()){
			for(EventoSGVEN ev : (List<EventoSGVEN>)listaEvento){
				Hibernate.initialize(ev.getParticipantes());
				for(ParticipanteEventoSGVEN part :  ev.getParticipantes()){
					Hibernate.initialize(part.getListaOcupacaoParticipacao());
				}
				
			}
		}
		
		return (List<EventoSGVEN>) listaEvento;

	}
	

	@Override
	public EventoSGVEN recuperarEventoComParticipantes(Long id) {

		Criteria c = obterCriteria(EventoSGVEN.class);
		c.add(Restrictions.eq("id", id));
		EventoSGVEN evento = (EventoSGVEN) c.uniqueResult();
		Hibernate.initialize(evento.getParticipantes());
		for (ParticipanteEventoSGVEN p : evento.getParticipantes()) {
			Hibernate.initialize(p.getListaOcupacaoParticipacao());

		}
		return evento;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verificaExisteEventoPorTipoEvento(TipoEventoSGVEN tipoEvento) {
		Criteria criteriaEvento = obterCriteria(EventoSGVEN.class);
		criteriaEvento.add(Restrictions.eq("tipoEvento", tipoEvento));
		List<EventoSGVEN> lista = criteriaEvento.list();
		if (lista!=null && !lista.isEmpty())
			return true;
		return false;
	}

}
