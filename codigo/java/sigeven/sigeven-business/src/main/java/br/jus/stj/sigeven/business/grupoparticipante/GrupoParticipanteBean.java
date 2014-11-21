package br.jus.stj.sigeven.business.grupoparticipante;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;
import br.jus.stj.sigeven.persistence.GrupoParticipanteDAO;

@Stateless
public class GrupoParticipanteBean {
	
	@Inject
	private GrupoParticipanteDAO grupoParticipanteDAO;
	
	public boolean incluir(GrupoMDIR grupo) {
		GrupoMDIR verificador = grupoParticipanteDAO.recuperarPorNome(grupo.getNome());
		if (verificador != null)
			return false;
		
		grupoParticipanteDAO.incluir(grupo);
		return true;
	}
	
	public List<GrupoMDIR> recuperarTodos() {
		return grupoParticipanteDAO.recuperarTodos(GrupoMDIR.class);
	}
	
	public GrupoMDIR recuperarPorId(Long id) {
		return grupoParticipanteDAO.recuperarPorId(id);
	}
	
	public List<GrupoMDIR> pesquisar(GrupoMDIR grupo) {
		return grupoParticipanteDAO.pesquisar(grupo);
	}
	
	public boolean alterar(GrupoMDIR grupo) {
		List<GrupoMDIR> verificador = grupoParticipanteDAO.pesquisar(grupo);
		if (verificador != null && !verificador.isEmpty())
			return false;
		
		grupoParticipanteDAO.atualizar(grupo);
		return true;
	}
	
	public boolean excluir(GrupoMDIR grupo) {		
		grupoParticipanteDAO.excluir(grupo);
		return true;
		
	}
}
