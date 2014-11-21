package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;

public interface PoderAreaDAO extends GenericDAO<PoderMalaMDIR> {

	public List<PoderMalaMDIR> obterPoderAreaVinculadasAoEvento(EventoSGVEN evento);

	public List<PoderMalaMDIR> obterPorNome(String nome);
	
	public List<PoderMalaMDIR> obterPorId(Long id);
	
	List<PoderMalaMDIR> pesquisar(PoderMalaMDIR poderArea);	
	
	PoderMalaMDIR recuperarPorNome(String nome);
	
}
