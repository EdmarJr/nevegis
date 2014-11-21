package br.jus.stj.sigeven.business.evento;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.business.cargo.CargoBean;
import br.jus.stj.sigeven.business.fornecedor.FornecedorBean;
import br.jus.stj.sigeven.business.grupoparticipante.GrupoParticipanteBean;
import br.jus.stj.sigeven.business.orgao.OrgaoBean;
import br.jus.stj.sigeven.business.servicoprestadoevento.ServicoPrestadoEventoBean;
import br.jus.stj.sigeven.business.tiposervico.TipoServicoBean;
import br.jus.stj.sigeven.business.tratamento.TratamentoBean;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.FornecedorSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ServicoPrestadoEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoServicoSGVEN;
import br.jus.stj.sigeven.persistence.EventoDAO;

@Stateless
public class EventoBean {

	@Inject
	private EventoDAO eventoDAO;

	@EJB
	private CargoBean cargoDB2Bean;

	@EJB
	private FornecedorBean fornecedorBean;

	@EJB
	private ServicoPrestadoEventoBean servicoPrestadoEventoBean;

	@EJB
	private OrgaoBean orgaoDB2Bean;

	@EJB
	private TipoServicoBean tipoServicoBean;

	@EJB
	private TratamentoBean tratamentoDB2Bean;

	@EJB
	private GrupoParticipanteBean grupoDB2Bean;

	public EventoSGVEN incluir(EventoSGVEN evento,
			List<ServicoPrestadoEventoSGVEN> servicos) {
		
		evento = eventoDAO.incluir(evento);
		incluirServicoPrestado(evento, servicos);
		return evento;
	}

	
	public EventoSGVEN incluir(EventoSGVEN evento){
		
		return eventoDAO.incluir(evento);
	}

	public EventoSGVEN alterarEvento(EventoSGVEN evento,
			List<ServicoPrestadoEventoSGVEN> servicos,
			List<ServicoPrestadoEventoSGVEN> servicosExcluir) {
		
		EventoSGVEN eventoTemp = eventoDAO.obterPorId(EventoSGVEN.class, evento.getId());
		
		popularEvento(evento, eventoTemp);
		
		evento = eventoDAO.atualizar(eventoTemp);
		
		incluirServicoPrestado(evento, servicos);
		
		excluirServicoPrestado(evento, servicosExcluir);

		return evento;
	}


	private void popularEvento(EventoSGVEN evento, EventoSGVEN eventoTemp) {
		eventoTemp.setCustoFinal(evento.getCustoFinal());
		eventoTemp.setDataFim(evento.getDataFim());
		eventoTemp.setDataInicio(evento.getDataInicio());
		eventoTemp.setHora(evento.getHora());
		eventoTemp.setLocal(evento.getLocal());
		eventoTemp.setMestreCerimoniaSGVEN(evento.getMestreCerimoniaSGVEN());
		eventoTemp.setNome(evento.getNome());
		eventoTemp.setObservacao(evento.getObservacao());
		eventoTemp.setParticipantes(evento.getParticipantes());
		eventoTemp.setServicoPrestadoEventoSGVEN(evento.getServicoPrestadoEventoSGVEN());
		eventoTemp.setStatus(evento.getStatus());
		eventoTemp.setTaquigrafia(evento.getTaquigrafia());
		eventoTemp.setTipoEvento(evento.getTipoEvento());
		eventoTemp.setTraducaoLibra(evento.getTraducaoLibra());
		eventoTemp.setUsuario(evento.getUsuario());
	}


	private void excluirServicoPrestado(EventoSGVEN evento,
			List<ServicoPrestadoEventoSGVEN> servicosExcluir) {
		for (ServicoPrestadoEventoSGVEN tempExcluir : servicosExcluir) {

			tempExcluir.setEventoSGVEN(evento);
			tempExcluir.setFornecedorSGVEN(fornecedorBean.obterPorId(
					FornecedorSGVEN.class, tempExcluir.getFornecedorSGVEN()
							.getId()));
			tempExcluir.setTipoServicoSGVEN(tipoServicoBean.obterPorId(
					TipoServicoSGVEN.class, tempExcluir.getTipoServicoSGVEN()
							.getId()));

			servicoPrestadoEventoBean.excluir(tempExcluir);

		}
	}
	

	private void incluirServicoPrestado(EventoSGVEN evento,
			List<ServicoPrestadoEventoSGVEN> servicos) {
		for (ServicoPrestadoEventoSGVEN temp : servicos) {
			
			temp.setEventoSGVEN(evento);
			temp.setFornecedorSGVEN(fornecedorBean.obterPorId(
					FornecedorSGVEN.class, temp.getFornecedorSGVEN().getId()));
			temp.setTipoServicoSGVEN(tipoServicoBean.obterPorId(
					TipoServicoSGVEN.class, temp.getTipoServicoSGVEN().getId()));
			servicoPrestadoEventoBean.salvar(temp);
		}
	}
	
	public EventoSGVEN alterarEvento(EventoSGVEN evento){
		
		return eventoDAO.atualizar(evento);
	}
	
	public boolean verificaMesmoNomeincluir(EventoSGVEN evento) {
		EventoSGVEN verificador = eventoDAO.recuperarPorNome(evento.getNome());
		if (verificador != null)
			return true;

		return false;
	}

	public List<EventoSGVEN> recuperarTodos() {
		return eventoDAO.recuperarTodos(new EventoSGVEN());
	}

	public EventoSGVEN recuperarPorId(Long id) {
		return eventoDAO.recuperarPorId(id);
	}

	public List<EventoSGVEN> pesquisar(EventoSGVEN evento) {
		return eventoDAO.pesquisar(evento);
	}

	public boolean alterar(EventoSGVEN evento) {
		eventoDAO.atualizar(evento);
		return true;
	}

	public void excluir(EventoSGVEN evento) {
		eventoDAO.excluir(evento);
	}

	@SuppressWarnings("unchecked")
	public List<EventoSGVEN> pesquisaPorParametros(EventoSGVEN evento) {
		List<? extends Object> listaEvento = eventoDAO.consultaEvento(evento);
		return (List<EventoSGVEN>) listaEvento;
	}

	public EventoSGVEN recuperarEventoComParticipantes(EventoSGVEN evento) {
		EventoSGVEN eventoTemp = eventoDAO
				.recuperarEventoComParticipantes(evento.getId());
		return eventoTemp;
	}

}
