package br.jus.stj.sigeven.business;

import java.util.List;

import br.jus.stj.sigeven.entity.EntidadeBase;
import br.jus.stj.sigeven.persistence.GenericDAO;

public abstract class GenericBeanMssql<T extends EntidadeBase> {

	public List<T> filtrar(T entidade) {
		return getDao().filtrar(entidade);
	}

	public void salvar(T entidade) {
		getDao().incluir(entidade);
	}

	public void alterar(T entidade) {
		getDao().atualizar(entidade);
	}

	public void excluir(T entidade) {
		getDao().excluir(entidade);
	}

	public List<T> obterTodos(Class<T> clazz) {
		return getDao().recuperarTodos(clazz);
	}
	
	public T obterPorId(Class<T> clazz, Long id) {
		return getDao().obterPorId(clazz, id);
	}

	protected abstract GenericDAO<T> getDao();
}
