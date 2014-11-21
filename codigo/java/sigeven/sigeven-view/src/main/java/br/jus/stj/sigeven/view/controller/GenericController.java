package br.jus.stj.sigeven.view.controller;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.jus.stj.commons.util.MensagemUtil;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericController implements Serializable {
	
	private static final long serialVersionUID = -2162249893778518423L;
	
	private String paginaDestino;
	

	
	@ManyToOne
	@ManagedProperty(value = "#{breadCrumbsSessionController}")
	private BreadCrumbsSessionController crumbsController;
	
	public String getMensagem(String bundleName, String key) {
    	return MensagemUtil.getMensagem(bundleName, key);
    }
	
	public boolean validarDataInicioMenorQueDataFim(Date inicio, Date fim, String nomeHint) throws ValidatorException{
		if(inicio!=null && fim!=null){
				if(inicio.after(fim)){
					FacesMessage msg = new FacesMessage(getMensagem("msg", "msg.msg026.datafimmenordatainicio"));
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(nomeHint, new FacesMessage(FacesMessage.SEVERITY_WARN,getMensagem("msg", "msg.msg026.datafimmenordatainicio"),getMensagem("msg", "msg.msg026.datafimmenordatainicio")));
					return false;
				}
		}
		return true;
	}
	
	public Boolean seExisteMensagemErro() {
		return FacesContext.getCurrentInstance().getMessageList().size() != 0;
	}
	
	public String getPaginaDestino() {
		return paginaDestino;
	}

	public void setPaginaDestino(String paginaDestino) {
		this.paginaDestino = paginaDestino;
	}
	
	public void abrirModal(String widgetVar) {
		org.primefaces.context.RequestContext.getCurrentInstance().execute(widgetVar+".show();");
	}
	public void fecharModal(String widgetVar) {
		org.primefaces.context.RequestContext.getCurrentInstance().execute(widgetVar+".hide();");
	}

	public void setCrumbsController(BreadCrumbsSessionController crumbsController) {
		this.crumbsController = crumbsController;
	}
	
	public String getPaginaAnterior() {
		return crumbsController.getPaginaAnterior();
	}
	
	public String getPaginaAtual() {
		return Paths.get(crumbsController.getPaginaAtual()).getFileName()
				.toString().replace(".jsf", ".xhtml");
	}



}
