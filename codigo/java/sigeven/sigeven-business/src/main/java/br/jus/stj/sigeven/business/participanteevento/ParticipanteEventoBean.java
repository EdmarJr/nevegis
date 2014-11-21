package br.jus.stj.sigeven.business.participanteevento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.business.cargo.CargoBean;
import br.jus.stj.sigeven.business.esfera.EsferaBean;
import br.jus.stj.sigeven.business.orgao.OrgaoBean;
import br.jus.stj.sigeven.business.participanteorgaocargoevento.ParticipanteOrgaoCargoEventoBean;
import br.jus.stj.sigeven.business.poderarea.PoderAreaBean;
import br.jus.stj.sigeven.business.to.ParametrosDisponiveisTO;
import br.jus.stj.sigeven.business.tratamento.TratamentoBean;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.persistence.ParticipanteEventoDAO;
import br.jus.stj.sigeven.persistence.to.FiltroPesquisaParticipanteEventoTO;

@Stateless
public class ParticipanteEventoBean {

	@Inject
	private ParticipanteEventoDAO dao;
	@EJB
	private ParticipanteOrgaoCargoEventoBean participanteCargoEventoBean;
	@EJB
	private CargoBean cargoBean;
	@EJB
	private OrgaoBean orgaoBean;
	@EJB
	private TratamentoBean tratamentoBean;
	@EJB
	private PoderAreaBean poderAreaBean;
	@EJB
	private EsferaBean esferaBean;
	
	public List<ParticipanteEventoSGVEN> obterParticipantesComNomesFoneticamenteIguais(
			List<ParticipanteEventoSGVEN> participantesEvento) {
		Set<ParticipanteEventoSGVEN> listaPEs = new TreeSet<ParticipanteEventoSGVEN>(
				new Comparator<ParticipanteEventoSGVEN>() {

					@Override
					public int compare(ParticipanteEventoSGVEN o1,
							ParticipanteEventoSGVEN o2) {
						return o1.getId().compareTo(o2.getId());
					}
				});

		for (ParticipanteEventoSGVEN participanteEvento : participantesEvento) {
			for (ParticipanteEventoSGVEN participanteEventoTemp : participantesEvento) {
				if (participanteEvento.getId() != participanteEventoTemp
						.getId()
						&& participanteEvento.getCodigoFonetico().split(" ")[0]
								.equals(participanteEventoTemp
										.getCodigoFonetico().split(" ")[0])) {

					listaPEs.add(participanteEvento);
					listaPEs.add(participanteEventoTemp);
				}
			}
		}

		return ordernarPorCodigoFonetico(listaPEs);
	}

	private ArrayList<ParticipanteEventoSGVEN> ordernarPorCodigoFonetico(
			Set<ParticipanteEventoSGVEN> listaPEs) {
		ArrayList<ParticipanteEventoSGVEN> lista = new ArrayList<ParticipanteEventoSGVEN>(
				listaPEs);
		Collections.sort(lista, new Comparator<ParticipanteEventoSGVEN>() {

			@Override
			public int compare(ParticipanteEventoSGVEN o1, ParticipanteEventoSGVEN o2) {
				return o1.getCodigoFonetico().compareTo(o2.getCodigoFonetico());
			}
		});
		return lista;
	}

	public void incluir(ParticipanteEventoSGVEN participanteEvento) {
		List<OcupacaoParticipanteEventoSGVEN> orgaosCargosEventos = participanteEvento
				.getListaOcupacaoParticipacao();
		for (OcupacaoParticipanteEventoSGVEN p : orgaosCargosEventos) {
			p.setCargo(cargoBean.sincronizar(p.getCargo()));
			p.setOrgao(orgaoBean.sincronizar(p.getOrgao()));
		}
		dao.incluir(participanteEvento);
	}

	public List<ParticipanteEventoSGVEN> listarTodos() {
		return dao.recuperarTodos(ParticipanteEventoSGVEN.class);
	}

	public ParametrosDisponiveisTO obterParametrosDisponiveisPesquisa(
			EventoSGVEN evento) {
		ParametrosDisponiveisTO param = new ParametrosDisponiveisTO();
		param.setCargosDisponiveis(cargoBean
				.obterCargosVinculadosAoEvento(evento));
		param.setOrgaosDisponiveis(orgaoBean
				.obterOrgaosVinculadosAoEvento(evento));
		param.setTratamentosDisponiveis(tratamentoBean
				.obterTratamentosVinculadosAoEvento(evento));
		param.setPoderAreaDisponiveis(poderAreaBean
				.obterPoderAreaDisponiveisPesquisa(evento));
		param.setEsferasDisponiveis(esferaBean
				.obterEsferasVinculadasAoEvento(evento));
		return param;
	}

	public List<ParticipanteEventoSGVEN> obterParticipantesPorParametro(
			FiltroPesquisaParticipanteEventoTO filtro, EventoSGVEN evento) {
		List<ParticipanteEventoSGVEN> participantes = dao
				.obterParticipantesPorParametro(filtro, evento);

		for (ParticipanteEventoSGVEN part : participantes) {
			List<OcupacaoParticipanteEventoSGVEN> orgaosCargosEventos = part
					.getListaOcupacaoParticipacao();
			for (OcupacaoParticipanteEventoSGVEN p : orgaosCargosEventos) {
				p.getOrgao().getSigla();
				p.getCargo().getNome();
			}
		}
		return participantes;
	}

	public void alterar(ParticipanteEventoSGVEN participanteEvento) {
		List<OcupacaoParticipanteEventoSGVEN> orgaosCargosEventos = new ArrayList<>(
				participanteEvento.getListaOcupacaoParticipacao());
		for (OcupacaoParticipanteEventoSGVEN p : orgaosCargosEventos) {
			if (!p.getAtivo()) {
				participanteCargoEventoBean.excluir(p);
				participanteEvento.getListaOcupacaoParticipacao().remove(p);
				continue;
			}
			p.setCargo(cargoBean.sincronizar(p.getCargo()));
			p.setOrgao(orgaoBean.sincronizar(p.getOrgao()));
		}		
		dao.atualizar(participanteEvento);		
	}

	public void excluir(ParticipanteEventoSGVEN participanteEvento) {
		dao.excluir(participanteEvento);

	}

}
