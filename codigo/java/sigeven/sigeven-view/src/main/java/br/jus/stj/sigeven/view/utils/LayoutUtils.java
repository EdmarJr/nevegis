package br.jus.stj.sigeven.view.utils;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.jus.stj.sigeven.view.controller.GenericController;

@ManagedBean
@ViewScoped
public class LayoutUtils extends GenericController {


	private String[] menus = { "manterEvento", "tipoEvento", "manutencao" };
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String paginaHover;

	@PostConstruct
	private void init() {
	}

	public Boolean isHover(String nome) {
		if (paginaHover != null && paginaHover.equals(nome)) {
			return true;
		}
		return false;
	}

	public void definirMenuAtivo(List<String> paginas) {
		paginaHover = null;
		int size = paginas.size();
		int paginaAtual = --size;
		boolean hoverEncontrado = false;
		for (; paginaAtual > -1; paginaAtual--) {
			for (String pagina : menus) {
				if (paginas.get(paginaAtual) != null
						&& paginas.get(paginaAtual).indexOf(pagina) != -1) {
					paginaHover = pagina;
					hoverEncontrado = true;
					break;
				}
			}
			if (hoverEncontrado) {
				break;
			}
		}
	}

}
