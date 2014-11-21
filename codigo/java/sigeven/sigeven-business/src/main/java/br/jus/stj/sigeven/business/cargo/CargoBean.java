package br.jus.stj.sigeven.business.cargo;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.commons.util.MensagemUtil;
import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.CargoDAO;
import br.jus.stj.sigeven.persistence.GenericDAO;

/**
 * Session Bean implementation class CargoBean
 */
@Stateless
@LocalBean
public class CargoBean extends GenericBeanMssql<CargoMDIR> {

	private static final String MSG = "msg";
	private static final String MSG_MSG049_CARGO_VINCULADO_PARTICIPANTES = "msg.MSG049.cargo.vinculado.participantes";
	@Inject
	private CargoDAO dao;

	/**
	 * Default constructor.
	 */
	public CargoBean() {
		// TODO Auto-generated constructor stub
	}

	public boolean validarInclusao(CargoMDIR cargo) {
		CargoMDIR verificador = dao.recuperarPorNome(cargo.getNome());
		if (verificador != null)
			return false;
		
		dao.incluir(cargo);
		return true;
	}

	public boolean validarAlteracao(CargoMDIR cargo) {
		CargoMDIR verificador = dao.recuperarPorNome(cargo.getNome());
		if (verificador != null)
			return false;
		
		dao.atualizar(cargo);
		return true;
	}

	public void validarExclusaoComRelacionamentosParticipantes(CargoMDIR cargo) {

		List<OcupacaoParticipanteEventoSGVEN> relacoes = dao.sincronizar(cargo)
				.getRelacionamentosParticipanteOrgaoCargo();
		if (relacoes != null && relacoes.size() != 0) {
			for (OcupacaoParticipanteEventoSGVEN relacaoTemp : relacoes) {
				if (relacaoTemp.getParticipanteEvento() != null) {
					MensagemUtil.adicionarMensagemErro(MSG,
							MSG_MSG049_CARGO_VINCULADO_PARTICIPANTES);
					break;
				}
			}

		}
	}

	public List<CargoMDIR> obterCargosPorNome(CargoMDIR cargo, EventoSGVEN evento) {
		return dao.obterCargosPorNomeEEvento(cargo.getNome().trim(), evento);
	}

	public List<CargoMDIR> obterCargosDisponiveisInclusao() {
		return dao.obterCargosDisponiveisInclusao();
	}

	public CargoMDIR sincronizar(CargoMDIR cargo) {
		return dao.sincronizar(cargo);
	}

	public List<CargoMDIR> obterCargosVinculadosAoEvento(EventoSGVEN evento) {
		return dao.obterCargosVinculadosAoEvento(evento);
	}

	@Override
	protected GenericDAO<CargoMDIR> getDao() {
		return dao;
	}
	
	public List<CargoMDIR> recuperarTodos() {
		return dao.recuperarTodos(CargoMDIR.class);
	}
	
	public List<CargoMDIR> pesquisar(CargoMDIR cargo) {
		return dao.pesquisar(cargo);
	}
	
	public boolean incluir(CargoMDIR cargoDB2) {
		dao.incluir(cargoDB2);
		return true;
	}
	
	public boolean verificaMesmoNomeincluir(CargoMDIR cargoDB2) {
		CargoMDIR verificador = dao.recuperarPorNome(cargoDB2.getNome());
		if (verificador != null)
			return true;
		
		return false;
	}
	
	public CargoMDIR recuperarPorId(Long id) {
		return dao.recuperarPorId(id);
	}
	
	public void alterar(CargoMDIR cargoDB2) {
		dao.atualizar(cargoDB2);
	}
	
	public void excluir(CargoMDIR cargoDB2) {
		dao.excluir(cargoDB2);
	}
	
	@SuppressWarnings("unchecked")
	public List<CargoMDIR> pesquisaPorParametros(CargoMDIR cargoDB2) {
		List<? extends Object> listaCargoDB2 = dao.consultaCargoDB2(cargoDB2);
		return (List<CargoMDIR>)listaCargoDB2;
	}

}
