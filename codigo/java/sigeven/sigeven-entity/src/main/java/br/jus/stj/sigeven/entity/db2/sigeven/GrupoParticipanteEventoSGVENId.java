package br.jus.stj.sigeven.entity.db2.sigeven;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class GrupoParticipanteEventoSGVENId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long participanteEventoId;
	private Long grupoId;
	
	/**
	 * @return the participanteEventoSGVENId
	 */
	public Long getParticipanteEventoId() {
		return participanteEventoId;
	}
	
	/**
	 * @param participanteEventoSGVENId the participanteEventoSGVENId to set
	 */
	public void setParticipanteEventoId(Long participanteEventoId) {
		this.participanteEventoId = participanteEventoId;
	}
	
	/**
	 * @return the grupoMDIRId
	 */
	public Long getGrupoId() {
		return grupoId;
	}
	
	/**
	 * @param grupoMDIRId the grupoMDIRId to set
	 */
	public void setGrupoId(Long grupoId) {
		this.grupoId = grupoId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((grupoId == null) ? 0 : grupoId.hashCode());
		result = prime
				* result
				+ ((participanteEventoId == null) ? 0
						: participanteEventoId.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoParticipanteEventoSGVENId other = (GrupoParticipanteEventoSGVENId) obj;
		if (grupoId == null) {
			if (other.grupoId != null)
				return false;
		} else if (!grupoId.equals(other.grupoId))
			return false;
		if (participanteEventoId == null) {
			if (other.participanteEventoId != null)
				return false;
		} else if (!participanteEventoId
				.equals(other.participanteEventoId))
			return false;
		return true;
	}
	
}
