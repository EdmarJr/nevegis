package br.jus.stj.sigeven.persistence;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.jus.stj.sigeven.entity.db2.db2sa.Usuario;
import br.jus.stj.sigeven.persistence.db2.GenericDAODb2Impl;

public class UsuarioDAOImpl extends GenericDAODb2Impl<Usuario> implements
		UsuarioDAO {

	@SuppressWarnings("unchecked")
	public List<Usuario> list() {
		Query query = manager.createQuery("from Usuario");
		return query.getResultList();
	}

	public Usuario getByName(String nome) {
		StringBuilder sb = new StringBuilder()
				.append("	from											")
				.append("		Usuario usuario								")
				// .append("		join usuario.locaisUsuarios locaisUsuario	")
				.append("	left join fetch									")
				.append("		usuario.locaisUsuarios						")
				.append("	where											")
				.append("		usuario.nome like :nome						");
		Query query = manager.createQuery(sb.toString());
		query.setParameter("nome", "%" + nome + "%");
		return (Usuario) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosPorNome(String nome) {
		Criteria criteria = obterCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Usuario buscarUsuarioPorNome(String nome) {
		Criteria criteria = obterCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		
		List<Usuario> list = criteria.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}

	
	public Usuario obterPorId(Long idUsuario) {
		Criteria criteria = obterCriteria(Usuario.class);
		criteria.add(Restrictions.eq("id", idUsuario));
		return (Usuario) criteria.uniqueResult();
	}
	
	@SuppressWarnings("rawtypes")
	public Usuario obterPorLogin(String login) {
		Criteria criteria = obterCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login));
		List list = criteria.list();
		return list.size() > 0 ? (Usuario) list.get(0) : null;
	}

}
