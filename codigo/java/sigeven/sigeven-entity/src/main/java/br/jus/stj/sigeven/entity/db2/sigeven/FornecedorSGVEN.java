package br.jus.stj.sigeven.entity.db2.sigeven;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;


@Entity
@Table(schema = "SIGEVEN" , name = "FORNECEDOR")
public class FornecedorSGVEN extends EntidadeBase {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SQ_FORNECEDOR")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NM_FORNECEDOR")
	private String nome;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
