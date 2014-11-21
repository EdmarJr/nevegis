package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.commons.util.Utils;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class CargoDAOImpl extends GenericDAODb2Impl<CargoMDIR>
		implements CargoDAO {

	public List<CargoMDIR> obterCargosDisponiveisInclusao() {
		return recuperarTodos(CargoMDIR.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CargoMDIR> obterCargosVinculadosAoEvento(EventoSGVEN evento) {
		Criteria criteria = obterCriteria(CargoMDIR.class);
		Criteria criteriaRelacionamentos = criteria.createCriteria("relacionamentosParticipanteOrgaoCargo");
		Criteria criteriaParticipanteEvento = criteriaRelacionamentos.createCriteria("participanteEvento");
		criteriaParticipanteEvento.add(Restrictions.eq("evento", evento));
		return (List<CargoMDIR>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public CargoMDIR recuperarPorNome(String nome) {
		String jpql = "select cargo from CargoMDIR cargo "
				+ "where cargo.nome = :nome";
	
	Query query = manager.createQuery(jpql);
	query.setParameter("nome", nome);
	List<CargoMDIR> lista = query.getResultList();
	if (lista == null || lista.isEmpty())
		return null;
	return lista.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoMDIR> obterCargosPorNomeEEvento(String nome, EventoSGVEN evento) {
		Criteria criteria = obterCriteria(CargoMDIR.class);
		criteria.add(Restrictions.eq("nome", nome));
		criteria.add(Restrictions.eq("evento", evento));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CargoMDIR> pesquisar(CargoMDIR cargo) {
		Criteria criteria = obterCriteria(CargoMDIR.class);
		if (!Utils.seVazioOuNulo(cargo.getNome())) {
			criteria.add(Restrictions.like("nome", cargo.getNome(),
					MatchMode.ANYWHERE));
		}
		return criteria.list();
	}
	
	@Override
	public CargoMDIR recuperarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CargoMDIR> consultaCargoDB2(CargoMDIR cargo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CargoMDIR recuperarCargoDB2ComParticipantes(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
