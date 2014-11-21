package br.jus.stj.sigeven.view.controller.ordemprecedencia;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.jus.stj.sigeven.business.cargo.CargoBean;
import br.jus.stj.sigeven.business.orgao.OrgaoBean;
import br.jus.stj.sigeven.business.poderarea.PoderAreaBean;
import br.jus.stj.sigeven.business.tipoevento.TipoEventoBean;
import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.view.controller.GenericController;

@ManagedBean
@ViewScoped
public class OrdemPrecedenciaController extends GenericController {
	
	private static final long serialVersionUID = -8456280180533412904L;

	@EJB
	protected CargoBean cargoBean;
	@EJB
	protected OrgaoBean orgaoBean;
	@EJB
	protected PoderAreaBean poderAreaBean;
	
	private List<CargoMDIR> listaCargos;
	private CargoMDIR cargo;
	private List<OrgaoMDIR> listaOrgaos;
	private OrgaoMDIR orgao;
	private List<PoderMalaMDIR> listaPoderArea;
	private PoderMalaMDIR poderArea;
	
	private boolean exibeMensagem;
	private boolean exibeMensagemNaoExclusao = false;
	private String dialogMensagem;
	
	private boolean sucesso;
	
	private final static String PAGE_VISUALIZAR = "visualizar.jsf";
	
	private final static String PAGE_ALTERAR = "alterar.jsf";
	
	private final static String PAGE_EXCLUIR = "excluir.jsf";
	
	@EJB
	private TipoEventoBean tipoEventoBean;
	
	@PostConstruct
	public void init() {
		carregarCombos();
	}
	
	private void carregarCombos(){
		definirCargosDisponiveis();
		definirOrgaosDisponiveis();
		definirPoderAreaDisponiveis();
	}
	
	private void definirCargosDisponiveis() {
		listaCargos = cargoBean.recuperarTodos();
	}

	private void definirOrgaosDisponiveis() {
		listaOrgaos = orgaoBean.obterOrgaosDisponiveisInclusao();
	}
	
	private void definirPoderAreaDisponiveis() {
		listaPoderArea = poderAreaBean.obterDisponiveis();
	}
	
	public void comandoIncluir() {
		
	}
	
	public void comandoLimpar() {
		resetarAtributos();
	}
	
	public void cancelar() {
		resetarAtributos();
	}
	
	public void comandoPesquisar() {
		
	}
	
	public String comandoVisualizar() {
		return null;
	}
	
	public String comandoAlterar() {
		return null;
	}
	
	public void comandoAlterarSalvar() {
		
	}
	
	public String comandoExcluir() {
		return null;
	}
	
	public void comandoExcluirSalvar() {
		
		resetarAtributos();
	}
	
	private void resetarAtributos() {
		
	}

	public boolean isExibeMensagem() {
		return exibeMensagem;
	}

	public void setExibeMensagem(boolean exibeMensagem) {
		this.exibeMensagem = exibeMensagem;
	}

	public String getDialogMensagem() {
		return dialogMensagem;
	}

	public void setDialogMensagem(String dialogMensagem) {
		this.dialogMensagem = dialogMensagem;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public boolean isExibeMensagemNaoExclusao() {
		return exibeMensagemNaoExclusao;
	}

	public void setExibeMensagemNaoExclusao(boolean exibeMensagemNaoExclusao) {
		this.exibeMensagemNaoExclusao = exibeMensagemNaoExclusao;
	}

	/**
	 * @return the listaCargos
	 */
	public List<CargoMDIR> getListaCargos() {
		return listaCargos;
	}

	/**
	 * @param listaCargos the listaCargos to set
	 */
	public void setListaCargos(List<CargoMDIR> listaCargos) {
		this.listaCargos = listaCargos;
	}

	/**
	 * @return the cargo
	 */
	public CargoMDIR getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(CargoMDIR cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the listaOrgaos
	 */
	public List<OrgaoMDIR> getListaOrgaos() {
		return listaOrgaos;
	}

	/**
	 * @param listaOrgaos the listaOrgaos to set
	 */
	public void setListaOrgaos(List<OrgaoMDIR> listaOrgaos) {
		this.listaOrgaos = listaOrgaos;
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

	/**
	 * @return the listaPoderArea
	 */
	public List<PoderMalaMDIR> getListaPoderArea() {
		return listaPoderArea;
	}

	/**
	 * @param listaPoderArea the listaPoderArea to set
	 */
	public void setListaPoderArea(List<PoderMalaMDIR> listaPoderArea) {
		this.listaPoderArea = listaPoderArea;
	}

	/**
	 * @return the poderArea
	 */
	public PoderMalaMDIR getPoderArea() {
		return poderArea;
	}

	/**
	 * @param poderArea the poderArea to set
	 */
	public void setPoderArea(PoderMalaMDIR poderArea) {
		this.poderArea = poderArea;
	}		
		
}
