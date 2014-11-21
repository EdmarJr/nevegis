package br.jus.stj.sigeven.view.controller;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;

/**
 * Classe utilizada para guardar as páginas acessadas pelo o usuário na sessão.
 * 
 * @author edmar.junior
 * 
 */
@Entity
@ManagedBean
@SessionScoped
public class BreadCrumbsSessionController implements Serializable {

	private LinkedList<String> paginas;
	private String paginaAtual;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4646919848624145005L;

	@PostConstruct
	public void init() {
		paginas = new LinkedList<String>();
	}

	public void registrarPagina(String paginaAtual) {
		setPaginaAtual(normalizarEndereco(paginaAtual));
		int resultado = verificarSePaginaJaExiste(getPaginaAtual());
		if (resultado == -1) {
			paginas.addLast(getPaginaAtual());
			return;
		}
		removerPaginasSubSequentes(resultado);
	}

	private String normalizarEndereco(String paginaAtual) {
		Path enderecoPaginaAtualSemCaracteresRedundantes = Paths.get(paginaAtual).normalize();
		return enderecoPaginaAtualSemCaracteresRedundantes.toString().replace(
				'\\', '/');
	}

	private int verificarSePaginaJaExiste(String pagina) {
		Iterator<String> iterator = paginas.iterator();
		int retorno = 0;
		for (; iterator.hasNext(); retorno++) {
			String paginaTemp = iterator.next();
			if (paginaTemp.equals(pagina)) {
				return retorno;
			}
		}
		return -1;
	}

	private void removerPaginasSubSequentes(int position) {
		int nPaginas = paginas.size();
		--nPaginas;
		while (position < nPaginas) {
			paginas.remove(nPaginas);
			--nPaginas;
		}
	}
	
	/**
	 * Metodo responsável por retornar o caminho da pagina atual para a pagina
	 * anterior a mesma.
	 * 
	 * @author edmar.junior
	 * @return
	 */
	public String getPaginaAnterior() {
		String paginaAnterior = obterCaminhoPaginaAtualParaPaginaAnterior();
		return paginaAnterior;
	}

	private String obterCaminhoPaginaAtualParaPaginaAnterior() {
		String enderecoPaginaAnterior = obterEnderecoCompletoPaginaAnterior();
		Path pathAnterior = Paths.get(enderecoPaginaAnterior);
		Path pathAtual = Paths.get(normalizarEndereco(getPaginaAtual()));
		Path pathRelativa = pathAtual.relativize(pathAnterior);
		String paginaAnterior = pathRelativa.toString().replace(".jsf",
				".xhtml");
		paginaAnterior = paginaAnterior.replace('\\', '/');
		paginaAnterior = paginaAnterior.replaceFirst("\\.\\./", "");
		return paginaAnterior;
	}

	public LinkedList<String> getPaginas() {
		return paginas;
	}

	public void setPaginas(LinkedList<String> paginas) {
		this.paginas = paginas;
	}

	private String obterEnderecoCompletoPaginaAnterior() {
		int positionUltimaPagina = paginas.size();
		--positionUltimaPagina;
		--positionUltimaPagina;
		return normalizarEndereco(paginas.get(positionUltimaPagina));
	}

	public String getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(String paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

}
