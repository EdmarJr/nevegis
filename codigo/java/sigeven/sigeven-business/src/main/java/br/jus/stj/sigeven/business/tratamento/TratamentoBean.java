package br.jus.stj.sigeven.business.tratamento;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.persistence.TratamentoDAO;

@Stateless
public class TratamentoBean {
	
	@Inject
	private TratamentoDAO tratamentoDAO;
	
	public boolean incluir(TratamentoMDIR tratamento) {
		TratamentoMDIR verificador = tratamentoDAO.recuperarPorNome(tratamento.getNome());
		if (verificador != null)
			return false;
		
		tratamentoDAO.incluir(tratamento);
		return true;
	}
	
	public TratamentoMDIR recuperarPorId(Long id) {
		return tratamentoDAO.recuperarPorId(id);
	}
	
	public TratamentoMDIR recuperarPorNome(TratamentoMDIR tratamento) {
		return tratamentoDAO.recuperarPorNome(tratamento.getNome());
	}
	
	public List<TratamentoMDIR> pesquisar(TratamentoMDIR tratamento) {
		return tratamentoDAO.pesquisar(tratamento);
	}
	
	public boolean alterar(TratamentoMDIR tratamento) {
		TratamentoMDIR verificador = tratamentoDAO.recuperarPorNome(tratamento.getNome());
		if (verificador != null)
			return false;
		
		tratamentoDAO.atualizar(tratamento);
		return true;
	}
	
	public void excluir(TratamentoMDIR tratamento) {
		tratamentoDAO.excluir(tratamento);
	}
	
	public List<TratamentoMDIR> obterTratamentosDisponiveisInclusao(EventoSGVEN evento) {
    	return tratamentoDAO.obterTratamentosVinculadosAoEvento(evento);
	}
	public List<TratamentoMDIR> obterTratamentosVinculadosAoEvento(EventoSGVEN evento) {
		return tratamentoDAO.obterTratamentosVinculadosAoEvento(evento);
	}
}
