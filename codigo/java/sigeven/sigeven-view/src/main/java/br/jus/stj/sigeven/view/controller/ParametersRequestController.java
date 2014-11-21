package br.jus.stj.sigeven.view.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;

public abstract class ParametersRequestController extends GenericController {

	private static final long serialVersionUID = -2162249893778518423L;
	private Map<String, Object> mapaRequest = new HashMap<>();

	@SuppressWarnings("unchecked")
	public ParametersRequestController() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();

		if (requestMap.get("mapaRequestSession") != null) {
			mapaRequest = (Map<String, Object>) requestMap
					.get("mapaRequestSession");
		}

		if (mapaRequest == null) {
			mapaRequest = new HashMap<String, Object>();
		}
	}

	@PreDestroy
	protected void antesDeDestruir() {
		antesDeColocarOsParametrosNoRequest();
		colocarParametrosMapRequestSession();
	}

	private void colocarParametrosMapRequestSession() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("mapaRequestSession", mapaRequest);
	}

	protected void antesDeColocarOsParametrosNoRequest() {
		// TODO Auto-generated method stub

	}

	public void adicionarObjetoEmRequestSession(String nomeParametro,
			Object parametro) {
		if (mapaRequest == null) {
			mapaRequest = new HashMap<String, Object>();
		}
		mapaRequest.put(nomeParametro, parametro);
	}

	public void removerObjetoRequestSession(String nomeParametro) {
		mapaRequest.remove(nomeParametro);
	}

	public Object obterObjetoEmRequestSession(String nomeParametro) {
		Map<String, Object> map = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		if (mapaRequest.get(nomeParametro) != null) {
			return mapaRequest.get(nomeParametro);
		} else if (map.get(nomeParametro) != null) {
			return map.get(nomeParametro);
		}
		return null;
	}

}
