package br.jus.stj.sigeven.view.controller.participantes.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.jus.stj.sigeven.persistence.to.FiltroPesquisaParticipanteEventoTO;

public class FiltroUtils {
	private static final String GET = "get";

	public static Boolean isTotalmenteVazio(
			FiltroPesquisaParticipanteEventoTO filtro) {
		Boolean retorno = true;
		Field[] fields = filtro.getClass().getDeclaredFields();
		for (Field field : fields) {
			retorno = verificarValorDoCampo(filtro, retorno, field);
			if (!retorno) {
				return retorno;
			}
		}
		return retorno;

	}

	private static Boolean verificarValorDoCampo(
			FiltroPesquisaParticipanteEventoTO filtro, Boolean retorno,
			Field field) {
		String nameMethod;
		field.getName();
		nameMethod = GET + obterPrimeiraLetraMinuscula(field.getName())
				+ field.getName().substring(1);
		Method metodo = null;
		try {
			metodo = filtro.getClass().getMethod(nameMethod);
			Object retornoMetodo = metodo.invoke(filtro);
			retorno = verificarSeDiferenteDeNuloOuVazio(retorno, retornoMetodo);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	private static Boolean verificarSeDiferenteDeNuloOuVazio(Boolean retorno,
			Object retornoMetodo) {
		if (retornoMetodo instanceof String
				|| retornoMetodo instanceof Character) {
			String aux = String.valueOf(retornoMetodo).trim();
			if (aux != null && !aux.equals("")) {
				retorno = false;
				return retorno;
			}
		} else if (retornoMetodo instanceof Boolean) {
			Boolean aux = (Boolean) retornoMetodo;
			if (aux) {
				retorno = false;
			}
		} else {

			if (retornoMetodo != null) {
				retorno = false;
				return retorno;
			}
		}
		return retorno;
	}

	private static String obterPrimeiraLetraMinuscula(String texto) {
		return texto.substring(0, 1).toUpperCase();
	}
}
