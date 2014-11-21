package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;

public interface TratamentoDAO extends GenericDAO<TratamentoMDIR> {
	
	TratamentoMDIR recuperarPorId(Long id);
	
	List<TratamentoMDIR> pesquisar(TratamentoMDIR tratamento);
	
	TratamentoMDIR recuperarPorNome(String nome);
	
	List<TratamentoMDIR> obterTratamentosDisponiveisInclusao();
	
	List<TratamentoMDIR> obterTratamentosVinculadosAoEvento(EventoSGVEN evento);
	
}
