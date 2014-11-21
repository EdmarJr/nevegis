package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.TipoOrgaoMDIR;

public interface TipoOrgaoDAO extends GenericDAO<TipoOrgaoMDIR> {
	
	List<TipoOrgaoMDIR> pesquisar(TipoOrgaoMDIR tipoOrgao);	
	
	TipoOrgaoMDIR recuperarPorNome(String nome);
	
}
