package br.jus.stj.sigeven.business.tipoorgao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.maladir.TipoOrgaoMDIR;
import br.jus.stj.sigeven.persistence.TipoOrgaoDAO;

/**
 * Session Bean implementation class CargoBean
 */
@Stateless
@LocalBean
public class TipoOrgaoBean {

	@Inject
	private TipoOrgaoDAO dao;

	public boolean validarInclusao(TipoOrgaoMDIR tipoOrgao) {
		TipoOrgaoMDIR verificador = dao.recuperarPorNome(tipoOrgao.getNome());
		if (verificador != null)
			return false;
		
		dao.incluir(tipoOrgao);
		return true;
	}

	public boolean validarAlteracao(TipoOrgaoMDIR tipoOrgao) {
		TipoOrgaoMDIR verificador = dao.recuperarPorNome(tipoOrgao.getNome());
		if (verificador != null)
			return false;
		
		dao.atualizar(tipoOrgao);
		return true;
	}
	
	public void alterar(TipoOrgaoMDIR tipoOrgao) {
		dao.atualizar(tipoOrgao);
	}

	public void validarExclusaoComRelacionamentosParticipantes(
			TipoOrgaoMDIR tipoOrgao) {
		// TODO Auto-generated method stub

	}

	public void validarExclusaoOrgaoMalaDireta(TipoOrgaoMDIR tipoOrgao) {

	}

	public void excluir(TipoOrgaoMDIR tipoOrgao) {
		dao.excluir(tipoOrgao);
	}

	public void salvar(TipoOrgaoMDIR tipoOrgao) {
		dao.incluir(tipoOrgao);
	}

	public List<TipoOrgaoMDIR> obterDisponiveis() {
		return dao.recuperarTodos(TipoOrgaoMDIR.class);

	}
	
	public List<TipoOrgaoMDIR> pesquisar(TipoOrgaoMDIR tipoOrgao) {
		return dao.pesquisar(tipoOrgao);
	}
	
	public List<TipoOrgaoMDIR> recuperarTodos() {
		return dao.recuperarTodos(TipoOrgaoMDIR.class);
	}

}
