package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.sigeven.SetorSGVEN;

public interface SetorDAO extends GenericDAO<SetorSGVEN> {
	
	SetorSGVEN recuperarPorId(Long id);
	
	List<SetorSGVEN> pesquisar(SetorSGVEN setor);
	
	SetorSGVEN recuperarPorNome(String nome);
}
