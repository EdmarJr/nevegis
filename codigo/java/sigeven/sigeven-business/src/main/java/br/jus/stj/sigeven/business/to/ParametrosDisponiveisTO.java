package br.jus.stj.sigeven.business.to;

import java.util.List;

import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.EsferaMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.TipoOrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;

public class ParametrosDisponiveisTO {
	
	private List<OrgaoMDIR> orgaosDisponiveis;
	private List<CargoMDIR> cargosDisponiveis;
	private List<TratamentoMDIR> tratamentosDisponiveis;
	private List<PoderMalaMDIR> poderAreaDisponiveis;
	private List<EsferaMDIR> esferasDisponiveis;
	private List<TipoOrgaoMDIR> tipoOrgaosDisponiveis;

	public List<OrgaoMDIR> getOrgaosDisponiveis() {
		return orgaosDisponiveis;
	}

	public void setOrgaosDisponiveis(List<OrgaoMDIR> orgaosDisponiveis) {
		this.orgaosDisponiveis = orgaosDisponiveis;
	}

	public List<CargoMDIR> getCargosDisponiveis() {
		return cargosDisponiveis;
	}

	public void setCargosDisponiveis(List<CargoMDIR> cargosDisponiveis) {
		this.cargosDisponiveis = cargosDisponiveis;
	}

	public List<TratamentoMDIR> getTratamentosDisponiveis() {
		return tratamentosDisponiveis;
	}

	public void setTratamentosDisponiveis(
			List<TratamentoMDIR> tratamentosDisponiveis) {
		this.tratamentosDisponiveis = tratamentosDisponiveis;
	}

	public List<PoderMalaMDIR> getPoderAreaDisponiveis() {
		return poderAreaDisponiveis;
	}

	public void setPoderAreaDisponiveis(List<PoderMalaMDIR> poderAreaDisponiveis) {
		this.poderAreaDisponiveis = poderAreaDisponiveis;
	}

	public List<EsferaMDIR> getEsferasDisponiveis() {
		return esferasDisponiveis;
	}

	public void setEsferasDisponiveis(List<EsferaMDIR> esferasDisponiveis) {
		this.esferasDisponiveis = esferasDisponiveis;
	}

	public List<TipoOrgaoMDIR> getTipoOrgaosDisponiveis() {
		return tipoOrgaosDisponiveis;
	}

	public void setTipoOrgaosDisponiveis(List<TipoOrgaoMDIR> tipoOrgaosDisponiveis) {
		this.tipoOrgaosDisponiveis = tipoOrgaosDisponiveis;
	}

}
