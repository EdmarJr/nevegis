package br.jus.stj.sigeven.business.tipoevento;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;
import br.jus.stj.sigeven.persistence.EventoDAO;
import br.jus.stj.sigeven.persistence.TipoEventoDAO;

@Stateless
public class TipoEventoBean {
	
	@Inject
	private TipoEventoDAO tipoEventoDAO;
	
	@Inject
	private EventoDAO eventoDAO;
	

	
	public boolean incluir(TipoEventoSGVEN tipoEvento) {
		TipoEventoSGVEN verificador = tipoEventoDAO.recuperarPorNome(tipoEvento.getNome());
		if (verificador != null)
			return false;
		
		tipoEventoDAO.incluir(tipoEvento);
		return true;
	}
	
	public List<TipoEventoSGVEN> recuperarTodos() {
		return tipoEventoDAO.recuperarTodos(TipoEventoSGVEN.class);
	}
	
	public TipoEventoSGVEN recuperarPorId(Long id) {
		return tipoEventoDAO.recuperarPorId(id);
	}
	
	public List<TipoEventoSGVEN> pesquisar(TipoEventoSGVEN tipoEvento) {
		return tipoEventoDAO.pesquisar(tipoEvento);
	}
	
	public boolean alterar(TipoEventoSGVEN tipoEvento) {
		TipoEventoSGVEN verificador = tipoEventoDAO.recuperarPorNome(tipoEvento.getNome());
		if (verificador != null && 
				verificador.getId().longValue() != tipoEvento.getId().longValue())
			return false;
		
		tipoEventoDAO.atualizar(tipoEvento);
		return true;
	}
	
	public boolean excluir(TipoEventoSGVEN tipoEvento) {
		if(eventoDAO.verificaExisteEventoPorTipoEvento(tipoEvento)){
			return false;
		}
		tipoEventoDAO.excluir(tipoEvento);
		return true;
	}
}
