package br.jus.stj.sigeven.view.controller.tipoevento;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.jus.stj.sigeven.business.tipoevento.TipoEventoBean;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;
import br.jus.stj.sigeven.view.controller.GenericController;

@ManagedBean
@ViewScoped
public class TipoEventoController extends GenericController {
	
	private static final long serialVersionUID = -8456280180533412904L;

	private List<TipoEventoSGVEN> listaTipoEvento;
	
	private TipoEventoSGVEN tipoEvento;
	
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
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		if (requestMap.get("tipoEvento") != null)
			tipoEvento = (TipoEventoSGVEN)requestMap.get("tipoEvento");
		else
			tipoEvento = new TipoEventoSGVEN();
		listaTipoEvento = tipoEventoBean.recuperarTodos();
	}
	
	public void comandoIncluir() {
		
		tipoEvento.setNome(tipoEvento.getNome().trim());
		
		if(!"".equals(tipoEvento.getNome())){
			sucesso = tipoEventoBean.incluir(tipoEvento);
			exibeMensagem = true;
			if (sucesso) {
				dialogMensagem = getMensagem("msg", "msg.msg001.inclusaosucesso");
				resetarAtributos();
			} 
			else {
				dialogMensagem = getMensagem("msg", "msg.msg006.tipoeventomesmonome");
			}
		} else {
			exibeMensagem = true;
			dialogMensagem = getMensagem("msg", "msg.msg005.camposobrigatorios");
		}
		
	}
	
	public void comandoLimpar() {
		resetarAtributos();
	}
	
	public void cancelar() {
		resetarAtributos();
	}
	
	public void comandoPesquisar() {
		listaTipoEvento = tipoEventoBean.pesquisar(tipoEvento);
	}
	
	public String comandoVisualizar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("tipoEvento", tipoEvento);
		return PAGE_VISUALIZAR;
	}
	
	public String comandoAlterar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("tipoEvento", tipoEvento);
		return PAGE_ALTERAR;
	}
	
	public void comandoAlterarSalvar() {
		
		tipoEvento.setNome(tipoEvento.getNome().trim());
		
		if(!"".equals(tipoEvento.getNome())){
			sucesso = tipoEventoBean.alterar(tipoEvento);
			exibeMensagem = true;
			if (sucesso) {
				dialogMensagem = getMensagem("msg", "msg.msg002.alteracaosucesso");
				resetarAtributos();
			} 
			else {
				dialogMensagem = getMensagem("msg", "msg.msg006.tipoeventomesmonome");
			}
		} else {
			exibeMensagem = true;
			dialogMensagem = getMensagem("msg", "msg.msg005.camposobrigatorios");
		}
			
	}
	
	public String comandoExcluir() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("tipoEvento", tipoEvento);
		return PAGE_EXCLUIR;
	}
	
	public void comandoExcluirSalvar() {
		boolean excluido = tipoEventoBean.excluir(tipoEvento);
		if(!excluido){
			exibeMensagemNaoExclusao = true;
			exibeMensagem = false;
		}else{
			exibeMensagem = true;
			exibeMensagemNaoExclusao = false;
		}
		resetarAtributos();
	}
	
	private void resetarAtributos() {
		listaTipoEvento = tipoEventoBean.recuperarTodos();
		tipoEvento = new TipoEventoSGVEN();
	}
	
	public List<TipoEventoSGVEN> getListaTipoEvento() {
		return listaTipoEvento;
	}

	public TipoEventoSGVEN getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEventoSGVEN tipoEvento) {
		this.tipoEvento = tipoEvento;
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
