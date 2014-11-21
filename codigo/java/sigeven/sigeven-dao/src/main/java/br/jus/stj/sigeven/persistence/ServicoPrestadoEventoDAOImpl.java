package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ServicoPrestadoEventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class ServicoPrestadoEventoDAOImpl extends
		GenericDAODb2Impl<ServicoPrestadoEventoSGVEN> implements
		ServicoPrestadoEventoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicoPrestadoEventoSGVEN> recuperarTodosPorEvento(
			EventoSGVEN eventoSGVEN) {
		Criteria criteria = obterCriteria(ServicoPrestadoEventoSGVEN.class);
		
		criteria.add(Restrictions.eq("eventoSGVEN", eventoSGVEN));
		return  criteria.list();
		
	}

	
	public void excluir(ServicoPrestadoEventoSGVEN servico) {

		Query query = manager
				.createQuery("Delete From ServicoPrestadoEventoSGVEN s where "
						+ " s.eventoSGVEN = :eventoSGVEN "
						+ " and s.tipoServicoSGVEN = :tipoServicoSGVEN "
						+ " and s.fornecedorSGVEN = :fornecedorSGVEN");

		query.setParameter("eventoSGVEN", servico.getEventoSGVEN());
		query.setParameter("tipoServicoSGVEN", servico.getTipoServicoSGVEN());
		query.setParameter("fornecedorSGVEN", servico.getFornecedorSGVEN());

		query.executeUpdate();

	}
	
}
