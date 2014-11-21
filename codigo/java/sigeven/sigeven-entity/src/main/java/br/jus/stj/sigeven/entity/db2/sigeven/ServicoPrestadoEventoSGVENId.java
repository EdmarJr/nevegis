package br.jus.stj.sigeven.entity.db2.sigeven;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ServicoPrestadoEventoSGVENId implements Serializable{

	

	private static final long serialVersionUID = 1L;

	private Long eventoSGVEN;
	
	private Long tipoServicoSGVEN;
	
	private Long fornecedorSGVEN;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventoSGVEN == null) ? 0 : eventoSGVEN.hashCode());
		result = prime * result
				+ ((fornecedorSGVEN == null) ? 0 : fornecedorSGVEN.hashCode());
		result = prime
				* result
				+ ((tipoServicoSGVEN == null) ? 0 : tipoServicoSGVEN.hashCode());
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
		ServicoPrestadoEventoSGVENId other = (ServicoPrestadoEventoSGVENId) obj;
		if (eventoSGVEN == null) {
			if (other.eventoSGVEN != null)
				return false;
		} else if (!eventoSGVEN.equals(other.eventoSGVEN))
			return false;
		if (fornecedorSGVEN == null) {
			if (other.fornecedorSGVEN != null)
				return false;
		} else if (!fornecedorSGVEN.equals(other.fornecedorSGVEN))
			return false;
		if (tipoServicoSGVEN == null) {
			if (other.tipoServicoSGVEN != null)
				return false;
		} else if (!tipoServicoSGVEN.equals(other.tipoServicoSGVEN))
			return false;
		return true;
	}

	

}