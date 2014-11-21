package br.jus.stj.sigeven.business.poderarea;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.commons.util.EntidadeComIdUtils;
import br.jus.stj.commons.util.MensagemUtil;
import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.GenericDAO;
import br.jus.stj.sigeven.persistence.PoderAreaDAO;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Stateless
public class PoderAreaBean extends GenericBeanMssql<PoderMalaMDIR> {

	private static final String MSG_MSG047_PODER_AREA_ORGAO_VINCULADO = "msg.MSG047.poder.area.orgao.vinculado";

	private static final String MSG_MSG045_PODER_AREA_MESMO_NOME = "msg.MSG045.poder.area.mesmo.nome";

	@ManyToOne
	@Inject
	private PoderAreaDAO dao;

	private static final String MSG = "msg";

	public void validarSeExisteOrgaoComMesmoNome(PoderMalaMDIR poderArea) {
		if (EntidadeComIdUtils.seExisteObjetoComIdDiferenteDentroLista(
				obterPorNome(poderArea), poderArea)) {
			MensagemUtil.adicionarMensagemErro(MSG,
					MSG_MSG045_PODER_AREA_MESMO_NOME);
		}
	}

	public List<PoderMalaMDIR> obterPorNome(PoderMalaMDIR orgao) {
		return dao.obterPorNome(orgao.getNome().trim());
	}

	public void validarExclusaoComRelacionamentosParticipantes(
			PoderMalaMDIR poderArea) {
		List<OrgaoMDIR> orgaos = dao.sincronizar(poderArea).getOrgaos();
		for (OrgaoMDIR orgao : orgaos) {
			List<OcupacaoParticipanteEventoSGVEN> relacoes = orgao
					.getRelacionamentosParticipanteOrgaoCargo();
			if (relacoes != null && relacoes.size() != 0) {
				for (OcupacaoParticipanteEventoSGVEN relacaoTemp : relacoes) {
					if (relacaoTemp.getParticipanteEvento() != null) {
						MensagemUtil.adicionarMensagemErro(MSG,
								MSG_MSG047_PODER_AREA_ORGAO_VINCULADO);
						break;
					}
				}

			}
		}
	}
	
	
	public boolean validarExclusao(PoderMalaMDIR poderArea) {
		List<OrgaoMDIR> orgaos = dao.sincronizar(poderArea).getOrgaos();
		
		for (OrgaoMDIR orgao : orgaos) {
			List<OcupacaoParticipanteEventoSGVEN> relacoes = orgao
					.getRelacionamentosParticipanteOrgaoCargo();
			if (relacoes != null && relacoes.size() != 0) {
				return false;
			}
		}
		
		dao.excluir(poderArea);
		return true;
	}
	

	public boolean validarInclusao(PoderMalaMDIR poderArea) {
		PoderMalaMDIR verificador = dao.recuperarPorNome(poderArea.getNome());
		if (verificador != null)
			return false;
		
		dao.incluir(poderArea);
		return true;
	}

	public boolean validarAlteracao(PoderMalaMDIR poderArea) {
		PoderMalaMDIR verificador = dao.recuperarPorNome(poderArea.getNome());
		if (verificador != null)
			return false;
		
		dao.atualizar(poderArea);
		return true;
	}
	
	public List<PoderMalaMDIR> obterPoderAreaDisponiveisPesquisa(EventoSGVEN evento) {
		return dao.obterPoderAreaVinculadasAoEvento(evento);
	}

	public List<PoderMalaMDIR> obterDisponiveis() {
		return dao.recuperarTodos(PoderMalaMDIR.class);
	}


	protected GenericDAO<PoderMalaMDIR> getDao() {
		return dao;
	}

	public List<PoderMalaMDIR> pesquisar(PoderMalaMDIR poderArea){
		return dao.pesquisar(poderArea);
	}
	
}
