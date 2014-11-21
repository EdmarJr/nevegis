package br.jus.stj.sigeven.view.controller.orgao;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.orgao.OrgaoBean;
import br.jus.stj.sigeven.business.to.ParametrosDisponiveisTO;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;

@ManagedBean
@ViewScoped
public class ConsultaOrgaoController extends ParametersRequestController {

	private static final String EVENTO_VIEW = "eventoView";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String PAGE_VISUALIZAR = "visualizar.jsf";

	private EventoSGVEN evento;
	@EJB
	private OrgaoBean orgaoBean;
	private List<OrgaoMDIR> orgaos;
	private OrgaoMDIR orgao;
	private ParametrosDisponiveisTO parametros;

	@PostConstruct
	public void init() {
		
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		if (requestMap.get("orgao") != null)
			orgao = (OrgaoMDIR)requestMap.get("orgao");
		else
			orgao = new OrgaoMDIR();
		
		definirEvento();
		definirOrgao();
		definirParametrosParaVinculo();		
	}

	private void definirOrgao() {
		orgaos = orgaoBean.obterOrgaosDisponiveisInclusao();
	}

	private void definirParametrosParaVinculo() {
		parametros = orgaoBean.obterParametrosDisponiveisParaVinculo();
	}

	private void definirEvento() {
		evento = (EventoSGVEN) obterObjetoEmRequestSession(EVENTO_VIEW);
	}

	@Override
	protected void antesDeColocarOsParametrosNoRequest() {
		super.antesDeColocarOsParametrosNoRequest();
		adicionarObjetoEmRequestSession(EVENTO_VIEW, evento);		
	}

	public List<OrgaoMDIR> getOrgaos() {
		return orgaos;
	}

	public void setOrgaos(List<OrgaoMDIR> orgaos) {
		this.orgaos = orgaos;
	}

	public String comandoAlterar(OrgaoMDIR orgao) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("orgaoView", orgao);
		return "alterarOrgao.xhtml";
	}

	public String comandoExcluir(OrgaoMDIR orgao) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("orgaoView", orgao);
		return "excluirOrgao.xhtml";
	}

	public String comandoVisualizar(OrgaoMDIR orgao) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("orgaoView", orgao);
		return "visualizarOrgao.xhtml";
	}

	public String comandoIncluir() {
		colocarEventoNaRequest();
		return "incluirOrgao.xhtml";
	}

	public void comandoPesquisar() {
		orgaos = orgaoBean.pesquisar(orgao);
	}

	public String comandoVisualizar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("orgao", orgao);
		return PAGE_VISUALIZAR;
	}

	public void comandoLimpar() {
		resetarAtributos();
	}

	private void resetarAtributos() {
		orgaos = orgaoBean.obterOrgaosDisponiveisInclusao();
		orgao = new OrgaoMDIR(); 
	}

	public String comandoCancelar() {
		orgao = null;
		return getPaginaAnterior();
	}

	private void colocarEventoNaRequest() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put(EVENTO_VIEW, evento);
	}

	/**
	 * @return the orgao
	 */
	public OrgaoMDIR getOrgao() {
		return orgao;
	}

	/**
	 * @param orgao the orgao to set
	 */
	public void setOrgao(OrgaoMDIR orgao) {
		this.orgao = orgao;
	}

	public ParametrosDisponiveisTO getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosDisponiveisTO parametros) {
		this.parametros = parametros;
	}

}
