package br.jus.stj.sigeven.entity.db2.maladir;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;
import br.jus.stj.sigeven.entity.db2.sigeven.GrupoParticipanteEventoSGVEN;

/**
 * @author zainer.silva
 *
 */
@Entity
@Table(schema = "MALADIR", name = "GRUPO")
public class GrupoMDIR extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3767127438620329856L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_GRUPO")
	private Long id;//PK
	
	@Column(name = "IND_PUBLICO")
	private String indPublico;
	
	@Column(name = "DESC_GRUPO_MALA")
	private String nome;
	
	@Column(name = "SEQ_LOCAL")
	private Integer seqLocal;
	
	@Column(name = "IND_TIPO_GRUPO")
	private Character indTipoGrupo;
		
	@OneToMany(mappedBy="grupo")
	private List<GrupoParticipanteEventoSGVEN> participantesComGrupos;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the indPublico
	 */
	public String getIndPublico() {
		return indPublico;
	}

	/**
	 * @param indPublico the indPublico to set
	 */
	public void setIndPublico(String indPublico) {
		this.indPublico = indPublico;
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the seqLocal
	 */
	public Integer getSeqLocal() {
		return seqLocal;
	}

	/**
	 * @param seqLocal the seqLocal to set
	 */
	public void setSeqLocal(Integer seqLocal) {
		this.seqLocal = seqLocal;
	}

	/**
	 * @return the indTipoGrupo
	 */
	public Character getIndTipoGrupo() {
		return indTipoGrupo;
	}

	/**
	 * @param indTipoGrupo the indTipoGrupo to set
	 */
	public void setIndTipoGrupo(Character indTipoGrupo) {
		this.indTipoGrupo = indTipoGrupo;
	}
	
	/**
	 * @return the participantesComGrupos
	 */
	public List<GrupoParticipanteEventoSGVEN> getParticipantesComGrupos() {
		return participantesComGrupos;
	}

	/**
	 * @param participantesComGrupos the participantesComGrupos to set
	 */
	public void setParticipantesComGrupos(
			List<GrupoParticipanteEventoSGVEN> participantesComGrupos) {
		this.participantesComGrupos = participantesComGrupos;
	}

}