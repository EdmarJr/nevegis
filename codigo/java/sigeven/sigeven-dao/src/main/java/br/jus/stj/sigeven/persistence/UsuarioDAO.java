package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.db2sa.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {
	
	
	public Usuario getByName(String nome);
	public List<Usuario> listarUsuariosPorNome(String nome);
	public Usuario buscarUsuarioPorNome(String nome);
	public Usuario obterPorId(Long idUsuario);
	public Usuario obterPorLogin(String login);

}
