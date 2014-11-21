package br.jus.stj.sigeven.entity.db2.sigeven;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;
import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "SIGEVEN", name = "GRUPO_PARTICIPANTE_EVENTO")
public class GrupoParticipanteEventoSGVEN extends EntidadeBase {
		
	@EmbeddedId
	private GrupoParticipanteEventoSGVENId id;
	
	public GrupoParticipanteEventoSGVEN() {
		id = new GrupoParticipanteEventoSGVENId();
	}
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1048101138932533833L;

	@ManyToOne
	@JoinColumn(name="SQ_PARTICIPANTE_EVENTO", referencedColumnName="SQ_PARTICIPANTE_EVENTO")
	@MapsId("participanteEventoId")
	private ParticipanteEventoSGVEN participanteEvento;//PKFK
	
	@ManyToOne
	@JoinColumn(name="SEQ_GRUPO", referencedColumnName="SEQ_GRUPO")
	@MapsId("grupoId")
	private GrupoMDIR grupo;
	
	/**
	 * @return the participanteEvento
	 */
	public ParticipanteEventoSGVEN getParticipanteEvento() {
		return participanteEvento;
	}

	/**
	 * @param participanteEvento the participanteEvento to set
	 */
	public void setParticipanteEvento(ParticipanteEventoSGVEN participanteEvento) {
		this.participanteEvento = participanteEvento;
	}

	/**
	 * @return the grupo
	 */
	public GrupoMDIR getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(GrupoMDIR grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the evento
	 */
	public EventoSGVEN getEvento() {
		return this.participanteEvento.getEvento();
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(EventoSGVEN evento) {
		this.participanteEvento.setEvento(evento);
	}
	
	/**
	 * @return the participante
	 */

	@Override
	public Long getId() {
		return null;
	}

	/**
	 * @return the participante
	 */

	/**
	 * @param id the id to set
	 */
	public void setId(GrupoParticipanteEventoSGVENId id) {
		this.id = id;
	}
		
}