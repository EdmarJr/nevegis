package br.jus.stj.sigeven.util;

public class ChaveDupla  {

	private Long idPrimeiro;
	
	private Long idSegundo;
	
	
	
	public ChaveDupla(Long idPrimeiro, Long idSegundo) {
		super();
		this.idPrimeiro = idPrimeiro;
		this.idSegundo = idSegundo;
	}

	public Long getIdPrimeiro() {
		return idPrimeiro;
	}

	public void setIdPrimeiro(Long idPrimeiro) {
		this.idPrimeiro = idPrimeiro;
	}

	public Long getIdSegundo() {
		return idSegundo;
	}

	public void setIdSegundo(Long idSegundo) {
		this.idSegundo = idSegundo;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idPrimeiro == null) ? 0 : idPrimeiro.hashCode());
		result = prime * result
				+ ((idSegundo == null) ? 0 : idSegundo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChaveDupla other = (ChaveDupla) obj;
		if (idPrimeiro == null) {
			if (other.idPrimeiro != null)
				return false;
		} else if (!idPrimeiro.equals(other.idPrimeiro))
			return false;
		if (idSegundo == null) {
			if (other.idSegundo != null)
				return false;
		} else if (!idSegundo.equals(other.idSegundo))
			return false;
		return true;
	}
	
	
	
	
}
