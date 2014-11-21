package br.jus.stj.sigeven.business.orgao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.commons.util.MensagemUtil;
import br.jus.stj.sigeven.business.GenericBeanMssql;
import br.jus.stj.sigeven.business.esfera.EsferaBean;
import br.jus.stj.sigeven.business.poderarea.PoderAreaBean;
import br.jus.stj.sigeven.business.tipoorgao.TipoOrgaoBean;
import br.jus.stj.sigeven.business.to.ParametrosDisponiveisTO;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.GenericDAO;
import br.jus.stj.sigeven.persistence.OrgaoDAO;

/**
 * Session Bean implementation class CargoBean
 */
@Stateless
@LocalBean
public class OrgaoBean extends GenericBeanMssql<OrgaoMDIR> {

	@Inject
	private OrgaoDAO dao;
	@EJB
	private TipoOrgaoBean tipoOrgaoBean;
	@EJB
	private PoderAreaBean poderAreaBean;
	@EJB
	private EsferaBean esferaBean;

	/**
	 * Default constructor.
	 */
	public OrgaoBean() {
		// TODO Auto-generated constructor stub
	}

	public boolean validarInclusao(OrgaoMDIR orgao) {
		OrgaoMDIR verificador = dao.recuperarPorNome(orgao.getNome());
		if (verificador != null)
			return false;
		
		dao.incluir(orgao);
		return true;
	}

	public boolean validarAlteracao(OrgaoMDIR orgao) {
		OrgaoMDIR verificador = dao.recuperarPorNome(orgao.getNome());
		if (verificador != null)
			return false;
		
		dao.atualizar(orgao);
		return true;
	}
	
	public void validarExclusaoOrgaoMalaDireta(OrgaoMDIR orgao) {
//		if (orgao.getIdOrigem() != null) {
//			MensagemUtil.adicionarMensagemErro("msg",
//					"msg.MSG043.orgaoimportadomala");
//		}
	}

	public void validarExclusaoComRelacionamentosParticipantes(OrgaoMDIR orgao) {

		List<OcupacaoParticipanteEventoSGVEN> relacoes = dao.sincronizar(orgao)
				.getRelacionamentosParticipanteOrgaoCargo();
		if (relacoes != null && relacoes.size() != 0) {
			for (OcupacaoParticipanteEventoSGVEN relacaoTemp : relacoes) {
				if (relacaoTemp.getParticipanteEvento() != null) {
					MensagemUtil.adicionarMensagemErro("msg",
							"msg.MSG042.orgaocomparticipantes");
					break;
				}
			}

		}
	}
	
	public ParametrosDisponiveisTO obterParametrosDisponiveisParaVinculo() {
		ParametrosDisponiveisTO parametros = new ParametrosDisponiveisTO();
		parametros.setTipoOrgaosDisponiveis(tipoOrgaoBean.obterDisponiveis());
		parametros.setPoderAreaDisponiveis(poderAreaBean
				.obterDisponiveis());
		parametros.setEsferasDisponiveis(esferaBean.obterEsferasDisponiveis());
		return parametros;
	}

	public List<OrgaoMDIR> obterOrgaosPorNome(OrgaoMDIR orgao, EventoSGVEN evento) {
		return dao.obterOrgaosPorNomeEEvento(orgao.getNome().trim(), evento);
	}

	public OrgaoMDIR obterOrgaoERelacionamentos(OrgaoMDIR orgao) {
		return dao.obterOrgaoERelacionamentos(orgao);
	}

	public List<OrgaoMDIR> obterOrgaosDisponiveisInclusao() {
		return dao.obterOrgaosDisponiveisInclusao();
	}

	public List<OrgaoMDIR> obterOrgaosVinculadosAoEvento(EventoSGVEN evento) {
		return dao.obterOrgaosVinculadosAoEvento(evento);
	}

	public OrgaoMDIR sincronizar(OrgaoMDIR orgao) {
		return dao.sincronizar(orgao);
	}

	public void salvar(OrgaoMDIR orgao) {
		dao.incluir(orgao);
	}

	public void alterar(OrgaoMDIR orgao) {
		dao.atualizar(orgao);
	}

	public void excluir(OrgaoMDIR orgao) {
		dao.excluir(orgao);
	}

	public List<OrgaoMDIR> pesquisar(OrgaoMDIR orgao){
		return dao.pesquisar(orgao);
	}
	
	@Override
	protected GenericDAO<OrgaoMDIR> getDao() {
		return dao;		
	}

}
