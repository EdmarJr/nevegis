package br.jus.stj.sigeven.entity.db2.sigeven;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.jus.stj.sigeven.entity.EntidadeBase;


@Entity
@Table (schema = "SIGEVEN" , name = "SERVICO_PRESTADO_EVENTO")
@IdClass(ServicoPrestadoEventoSGVENId.class)
public class ServicoPrestadoEventoSGVEN extends EntidadeBase{

	

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "SQ_EVENTO", referencedColumnName = "SQ_EVENTO")
	private EventoSGVEN eventoSGVEN;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "SQ_TIPO_SERVICO", referencedColumnName = "SQ_TIPO_SERVICO")
	private TipoServicoSGVEN tipoServicoSGVEN;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "SQ_FORNECEDOR", referencedColumnName = "SQ_FORNECEDOR")
	private FornecedorSGVEN fornecedorSGVEN;

	public ServicoPrestadoEventoSGVEN(EventoSGVEN eventoSGVEN ,TipoServicoSGVEN tipoServicoSGVEN,
			FornecedorSGVEN fornecedorSGVEN) {
		super();
		this.eventoSGVEN = eventoSGVEN;
		this.tipoServicoSGVEN = tipoServicoSGVEN;
		this.fornecedorSGVEN = fornecedorSGVEN;
	}

	public ServicoPrestadoEventoSGVEN(){
		
	}
	
	
	public TipoServicoSGVEN getTipoServicoSGVEN() {
		return tipoServicoSGVEN;
	}


	public void setTipoServicoSGVEN(TipoServicoSGVEN tipoServicoSGVEN) {
		this.tipoServicoSGVEN = tipoServicoSGVEN;
	}

	public FornecedorSGVEN getFornecedorSGVEN() {
		return fornecedorSGVEN;
	}

	public void setFornecedorSGVEN(FornecedorSGVEN fornecedorSGVEN) {
		this.fornecedorSGVEN = fornecedorSGVEN;
	}
	
	
	public EventoSGVEN getEventoSGVEN() {
		return eventoSGVEN;
	}

	public void setEventoSGVEN(EventoSGVEN eventoSGVEN) {
		this.eventoSGVEN = eventoSGVEN;
	}

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicoPrestadoEventoSGVEN other = (ServicoPrestadoEventoSGVEN) obj;
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
