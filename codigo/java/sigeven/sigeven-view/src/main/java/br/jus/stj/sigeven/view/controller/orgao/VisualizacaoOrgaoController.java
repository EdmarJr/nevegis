package br.jus.stj.sigeven.view.controller.orgao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class VisualizacaoOrgaoController extends OrgaoController {



	/**
	 * 
	 */
	private static final long serialVersionUID = 9100802970997932848L;

	public String comandoVoltar() {
		return getPaginaAnterior();
	}



}
