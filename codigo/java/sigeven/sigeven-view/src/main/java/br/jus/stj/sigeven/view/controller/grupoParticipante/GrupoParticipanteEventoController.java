package br.jus.stj.sigeven.view.controller.grupoParticipante;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.grupoparticipante.GrupoParticipanteBean;
import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.util.ConstantesSigeven;
import br.jus.stj.sigeven.view.controller.GenericController;

@ManagedBean
@ViewScoped
public class GrupoParticipanteEventoController extends GenericController {
	
	private static final long serialVersionUID = -8456280180533412904L;

	private List<GrupoMDIR> listaGrupoParticipante;
	
	private GrupoMDIR grupoParticipante;
	
	private boolean exibeMensagem;
	private boolean exibeMensagemNaoExclusao = false;
	private String dialogMensagem;
	
	private boolean sucesso;
	
	private EventoSGVEN evento;
	
	private final static String PAGE_VISUALIZAR = "visualizar.jsf";
	
	private final static String PAGE_ALTERAR = "alterar.jsf";
	
	private final static String PAGE_EXCLUIR = "excluir.jsf";
	
	private final static String PAGE_CONSULTAR = "consultar.jsf";
	
	private final static String PAGE_INCLUIR= "incluir.jsf";
	
	@EJB
	private GrupoParticipanteBean grupoParticipanteBean;
	
	@PostConstruct
	public void init() {
		
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		
		if (requestMap.get("eventoView") != null){
			evento = (EventoSGVEN)requestMap.get("eventoView");
			System.out.println(evento.getNome());
		}
		
		if (requestMap.get("grupoParticipante") != null){
			grupoParticipante = (GrupoMDIR)requestMap.get("grupoParticipante");
		}else{
			grupoParticipante = new GrupoMDIR();
		}
		
		obterGrupoParticipante();
		
	}
	
	public void obterGrupoParticipante(){
		listaGrupoParticipante = grupoParticipanteBean.recuperarTodos();		
	}
	
	public void comandoIncluir() {		
		grupoParticipante.setIndPublico(ConstantesSigeven.IND_PUBLICO);
		grupoParticipante.setSeqLocal(ConstantesSigeven.SEQ_LOCAL);		
		
		if(grupoParticipanteBean.pesquisar(grupoParticipante)==null || grupoParticipanteBean.pesquisar(grupoParticipante).isEmpty()){			
			sucesso = grupoParticipanteBean.incluir(grupoParticipante);
		}else{
			sucesso = false;
		}
		exibeMensagem = true;
		if (sucesso) {
			dialogMensagem = getMensagem("msg", "msg.msg001.inclusaosucesso");
			resetarAtributos();
		} 
		else {
			dialogMensagem = getMensagem("msg", "msg.MSG040.grupoparticipante.jacadastrado");
		}
	}
	
	public void comandoLimpar() {
		resetarAtributos();
	}
	
	public void cancelar() {
		resetarAtributos();
	}
	
	public String direcionaInclusao() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("grupoParticipante", grupoParticipante);
		requestMap.put("eventoView", evento);
		return PAGE_INCLUIR;
	}
	
	public void comandoPesquisar() {
		listaGrupoParticipante = grupoParticipanteBean.pesquisar(grupoParticipante);
	}
	
	public String comandoVisualizar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("grupoParticipante", grupoParticipante);
		requestMap.put("eventoView", evento);
		return PAGE_VISUALIZAR;
	}
	
	public String comandoAlterar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("grupoParticipante", grupoParticipante);
		requestMap.put("eventoView", evento);
		return PAGE_ALTERAR;
	}
	
	public void comandoAlterarSalvar() {			
		sucesso = grupoParticipanteBean.alterar(grupoParticipante);
		exibeMensagem = true;
		if (sucesso) {
			dialogMensagem = getMensagem("msg", "msg.msg002.alteracaosucesso");
			resetarAtributos();
		} 
		else {
			dialogMensagem = getMensagem("msg", "msg.MSG040.grupoparticipante.jacadastrado");
		}
	}
	
	public String comandoExcluir() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("grupoParticipante", grupoParticipante);
		requestMap.put("eventoView", evento);
		return PAGE_EXCLUIR;
	}
	
	public String comandoVoltar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return PAGE_CONSULTAR;
	}
	
	public void comandoExcluirSalvar() {
		boolean excluido = grupoParticipanteBean.excluir(grupoParticipante);
		if(!excluido){
			exibeMensagemNaoExclusao = true;
			exibeMensagem = false;
		}else{
			exibeMensagem = true;
			exibeMensagemNaoExclusao = false;
		}
		resetarAtributos();
	}
	
	public String comandoCancelar() {
		return getPaginaAnterior();
	}
	
	public void comandoLimparAlteracao() {
		grupoParticipante.setNome("");
	}
	
	private void resetarAtributos() {
		listaGrupoParticipante = grupoParticipanteBean.recuperarTodos();
		grupoParticipante = new GrupoMDIR();
	}
	
	public List<GrupoMDIR> getListaGrupoParticipante() {
		return listaGrupoParticipante;
	}

	public GrupoMDIR getGrupoParticipante() {
		return grupoParticipante;
	}

	public void setGrupoParticipante(GrupoMDIR grupoParticipante) {
		this.grupoParticipante = grupoParticipante;
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
	
}
