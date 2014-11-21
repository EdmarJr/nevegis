package br.jus.stj.sigeven.business.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.entity.db2.db2sa.Usuario;
import br.jus.stj.sigeven.persistence.UsuarioDAO;

@Stateless
public class UsuarioBean extends GenericBeanMssql<Usuario> {
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	
	public UsuarioDAO getDao() {
		return usuarioDAO;
	}
	
	
	public List<Usuario> listarUsuariosPorNome(String nome){
		return usuarioDAO.listarUsuariosPorNome(nome);
	}
	
	public Usuario obterPorId(Long id) {
		return usuarioDAO.obterPorId(id);
	}
	
	public Usuario buscarUsuarioPorNome(String nome) {
		return usuarioDAO.buscarUsuarioPorNome(nome);
	}
	
	public List<Usuario> recuperarTodos() {

		return usuarioDAO.recuperarTodos(Usuario.class);

	}
	
}
