package br.jus.stj.sigeven.entity.db2.db2sa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;

@Entity
@Table(schema="DB2SA",name="USUARIO")
public class Usuario extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="SEQ_USUARIO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NOME_USUARIO")
	private String nome;
	
	@Column(name="DESC_USERNAME")
	private String login;
	
	public Usuario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Usuario setId(Long id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public Usuario setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	

}
