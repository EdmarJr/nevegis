package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;

public interface OrgaoDAO extends GenericDAO<OrgaoMDIR> {

	public List<OrgaoMDIR> obterOrgaosDisponiveisInclusao();

	public List<OrgaoMDIR> obterOrgaosVinculadosAoEvento(EventoSGVEN evento);

	public OrgaoMDIR recuperarPorNome(String nome);

	public List<OrgaoMDIR> obterOrgaosPorNomeEEvento(String nome, EventoSGVEN evento);

	public OrgaoMDIR obterOrgaoERelacionamentos(OrgaoMDIR orgao);
	
	public List<OrgaoMDIR> pesquisar(OrgaoMDIR orgao);
	
	
}
