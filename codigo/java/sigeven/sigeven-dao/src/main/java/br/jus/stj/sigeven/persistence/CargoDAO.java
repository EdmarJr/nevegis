package br.jus.stj.sigeven.persistence;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;

public interface CargoDAO extends GenericDAO<CargoMDIR> {

	public List<CargoMDIR> obterCargosDisponiveisInclusao();
	public List<CargoMDIR> obterCargosVinculadosAoEvento(EventoSGVEN evento);	
	public List<CargoMDIR> obterCargosPorNomeEEvento(String nome, EventoSGVEN evento);
	List<CargoMDIR> pesquisar(CargoMDIR cargo);	
	public CargoMDIR recuperarPorId(Long id);	
	public CargoMDIR recuperarPorNome(String nome);
	public List<CargoMDIR> consultaCargoDB2(CargoMDIR cargo);	
	public CargoMDIR recuperarCargoDB2ComParticipantes(Long id);
	
}
