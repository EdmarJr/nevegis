package br.jus.stj.sigeven.view.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ProgressController {
	private Integer progress;

	public ProgressController() {
		progress = Integer.valueOf(0);
	}

	public void iniciarBarraProgresso(String widgetVarProgressBar,
			String widgetVarBotaoAction) {
		org.primefaces.context.RequestContext.getCurrentInstance().execute(
				"PF('" + widgetVarProgressBar + "').start();PF('"
						+ widgetVarBotaoAction + "').disable()");
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}


	public void onComplete(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(mensagem));
	}

	public void cancel() {
		progress = null;
	}
}
