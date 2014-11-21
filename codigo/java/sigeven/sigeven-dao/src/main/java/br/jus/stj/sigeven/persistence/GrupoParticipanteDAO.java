package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;

public interface GrupoParticipanteDAO extends GenericDAO<GrupoMDIR> {
	
	GrupoMDIR recuperarPorId(Long id);
	
	List<GrupoMDIR> pesquisar(GrupoMDIR grupo);
	
	GrupoMDIR recuperarPorNome(String nome);
	
}
