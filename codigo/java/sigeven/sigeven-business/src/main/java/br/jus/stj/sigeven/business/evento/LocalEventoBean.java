package br.jus.stj.sigeven.business.evento;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.sigeven.LocalEventoSGVEN;
import br.jus.stj.sigeven.persistence.LocalEventoDAO;

@Stateless
public class LocalEventoBean {
	
	@Inject
	private LocalEventoDAO localEventoDAO;
	
	public boolean incluir(LocalEventoSGVEN localEvento) {
		LocalEventoSGVEN verificador = localEventoDAO.recuperarPorNome(localEvento.getNome());
		if (verificador != null)
			return false;
		
		localEventoDAO.incluir(localEvento);
		return true;
	}
	
	public List<LocalEventoSGVEN> recuperarTodos() {
		return localEventoDAO.recuperarTodos(LocalEventoSGVEN.class);
	}
	
	public LocalEventoSGVEN recuperarPorId(Long id) {
		return localEventoDAO.recuperarPorId(id);
	}
	
	public List<LocalEventoSGVEN> pesquisar(LocalEventoSGVEN localEvento) {
		return localEventoDAO.pesquisar(localEvento);
	}
	
	public boolean alterar(LocalEventoSGVEN localEvento) {
		LocalEventoSGVEN verificador = localEventoDAO.recuperarPorNome(localEvento.getNome());
		if (verificador != null)
			return false;
		
		localEventoDAO.atualizar(localEvento);
		return true;
	}
	
	public void excluir(LocalEventoSGVEN localEvento) {
		localEventoDAO.excluir(localEvento);
	}
}
