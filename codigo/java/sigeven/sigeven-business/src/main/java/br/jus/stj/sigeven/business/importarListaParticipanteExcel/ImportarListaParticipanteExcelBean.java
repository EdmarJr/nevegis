package br.jus.stj.sigeven.business.importarListaParticipanteExcel;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.GrupoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.OcupacaoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.SetorSGVEN;
import br.jus.stj.sigeven.persistence.CargoDAO;
import br.jus.stj.sigeven.persistence.OrgaoDAO;
import br.jus.stj.sigeven.persistence.ParticipanteEventoDAO;
import br.jus.stj.sigeven.persistence.PoderAreaDAO;
import br.jus.stj.sigeven.persistence.SetorDAO;
import br.jus.stj.sigeven.persistence.TratamentoDAO;
import br.jus.stj.sigeven.util.ConstantesSigeven;
import br.jus.stj.sigeven.vo.ParticipanteVO;

@Stateless
public class ImportarListaParticipanteExcelBean {

	@Inject
	private TratamentoDAO tratamentoDAO;

	@Inject
	private CargoDAO cargoDAO;

	@Inject
	private OrgaoDAO orgaoDAO;

	@Inject
	private SetorDAO setorDAO;

	@Inject
	private ParticipanteEventoDAO participanteDAO;
	
	@Inject
	private PoderAreaDAO poderAreaDAO;	
	
	private List<PoderMalaMDIR> listaPoderArea;
	
	private PoderMalaMDIR poderArea;

	public TratamentoMDIR recuperarTratamentoPorNome(TratamentoMDIR tratamento) {
		TratamentoMDIR verificador = tratamentoDAO.recuperarPorNome(
				tratamento.getNome());
		if (verificador != null) {
			return verificador;
		} else {	
			tratamento.setIndPublico(ConstantesSigeven.IND_PUBLICO);
			tratamento.setSeqLocal(ConstantesSigeven.SEQ_LOCAL);
			return tratamentoDAO.incluir(tratamento);			
		}
	}

	public CargoMDIR recuperarCargoPorNome(CargoMDIR cargo) {
		CargoMDIR verificador = cargoDAO.recuperarPorNome(cargo.getNome());
		if (verificador != null) {
			return verificador;
		} else {
			cargo.setIndPublico(ConstantesSigeven.IND_PUBLICO);
			cargo.setSeqLocal(ConstantesSigeven.SEQ_LOCAL);
			return cargoDAO.incluir(cargo);
		}
	}

	public OrgaoMDIR recuperarOrgaoPorNome(OrgaoMDIR orgao) {
		OrgaoMDIR verificador = orgaoDAO.recuperarPorNome(orgao.getNome());
		if (verificador != null) {
			return verificador;
		} else {
			orgao.setIndPublico(ConstantesSigeven.IND_PUBLICO);
			orgao.setSeqLocal(ConstantesSigeven.SEQ_LOCAL);
			
			//condição temporária apenas para teste 	
			listaPoderArea = poderAreaDAO.obterPorId(2L);
			
			for(PoderMalaMDIR lista: listaPoderArea){
				orgao.setPoderArea(lista);				
			}
			
			//condição temporária apenas para teste
			
			return orgaoDAO.incluir(orgao);
		}
	}

	private List<ParticipanteEventoSGVEN> tratarListaImportada(
			List<ParticipanteVO> listaImportada, EventoSGVEN evento) {
		List<ParticipanteEventoSGVEN> listaImportadaTratada = new ArrayList<ParticipanteEventoSGVEN>();
		for (ParticipanteVO paticipante : listaImportada) {
			ParticipanteEventoSGVEN paticipanteTratado = new ParticipanteEventoSGVEN();
			if (paticipante != null && !paticipante.equals("")) {				
				paticipanteTratado.setTratamento(consultarTratamento(
						paticipante.getTratamento()));
				paticipanteTratado.setTitulo(paticipante.getTitulo());
				paticipanteTratado.setNome(paticipante.getNome());
				paticipanteTratado.setListaOcupacaoParticipacao(consultaCargoOrgao(
						paticipante, paticipanteTratado, evento));
				paticipanteTratado.setEndereco(paticipante.getEndereco());
				paticipanteTratado.setUf(paticipante.getEstado());
				paticipanteTratado.setCidade(paticipante.getCidade());
				paticipanteTratado.setCep(paticipante.getCep());
				listaImportadaTratada.add(paticipanteTratado);
			}
		}
		return listaImportadaTratada;
	}

	public TratamentoMDIR consultarTratamento(String tratamento) {
		TratamentoMDIR tratamentolocal = new TratamentoMDIR();
		tratamentolocal.setNome(tratamento);
		tratamentolocal = recuperarTratamentoPorNome(tratamentolocal);
		return tratamentolocal;
	}

	public List<OcupacaoParticipanteEventoSGVEN> consultaCargoOrgao(
			ParticipanteVO paticipante, ParticipanteEventoSGVEN paticipanteTratado,
			EventoSGVEN evento) {
		List<OcupacaoParticipanteEventoSGVEN> listaCargoOrg = new ArrayList<OcupacaoParticipanteEventoSGVEN>();
		OcupacaoParticipanteEventoSGVEN partOrgCarg = new OcupacaoParticipanteEventoSGVEN();

		partOrgCarg.setParticipanteEvento(paticipanteTratado);

		CargoMDIR cargo = new CargoMDIR();
		cargo.setNome(paticipante.getCargo());
		cargo = recuperarCargoPorNome(cargo);
		partOrgCarg.setCargo(cargo);

		OrgaoMDIR orgao = new OrgaoMDIR();
		orgao.setNome(paticipante.getOrgao());
		orgao = recuperarOrgaoPorNome(orgao);
		partOrgCarg.setOrgao(orgao);

		listaCargoOrg.add(partOrgCarg);

		return listaCargoOrg;
	}
	
	public boolean salvarParticipantes(
			List<ParticipanteVO> listaImportadaParticipantes, EventoSGVEN evento,
			SetorSGVEN setor, Long grupo) {
		GrupoParticipanteEventoSGVEN grupoParticipante = new GrupoParticipanteEventoSGVEN();
		if (grupo != null && grupo > 0) {
			grupoParticipante.getGrupo().setId(grupo);
			grupoParticipante = consultaGrupoporId(grupoParticipante);
		}
		List<ParticipanteEventoSGVEN> listaImportadaTratada = tratarListaImportada(
				listaImportadaParticipantes, evento);
		for (ParticipanteEventoSGVEN participante : listaImportadaTratada) {
			if (grupoParticipante != null && grupoParticipante.getId() != null
					&& grupoParticipante.getId() > 0) {
				List<GrupoParticipanteEventoSGVEN> listGrupo = new ArrayList<GrupoParticipanteEventoSGVEN>();
				listGrupo.add(grupoParticipante);
				participante.setGrupoParticipante(listGrupo);
			}
			if (seUfParticipanteForaDosPadroes(participante)) {
				participante.setUf(participante.getUf().substring(0, 2));
			}	
			participante.setEvento(evento);
			participanteDAO.incluir(participante);
		}
		return true;
	}

	private boolean seUfParticipanteForaDosPadroes(
			ParticipanteEventoSGVEN participante) {
		return participante.getUf() != null && !participante.getUf().equals("")
				&& participante.getUf().toCharArray().length > 2;
	}

	public GrupoParticipanteEventoSGVEN consultaGrupoporId(
			GrupoParticipanteEventoSGVEN grupoParticipante) {
		List<GrupoParticipanteEventoSGVEN> listaGrupo = new ArrayList<GrupoParticipanteEventoSGVEN>();
//		listaGrupo = (List<GrupoParticipanteEventoSGVEN>) grupoParticipanteDAO
//				.recuperarPorParametro(grupoParticipante);
		if (listaGrupo.isEmpty())
			return null;
		return listaGrupo.get(0);
	}

	public List<SetorSGVEN> listaSetor() {
		return setorDAO.recuperarTodos(SetorSGVEN.class);
	}

	/**
	 * @return the listaPoderArea
	 */
	public List<PoderMalaMDIR> getListaPoderArea() {
		return listaPoderArea;
	}

	/**
	 * @param listaPoderArea the listaPoderArea to set
	 */
	public void setListaPoderArea(List<PoderMalaMDIR> listaPoderArea) {
		this.listaPoderArea = listaPoderArea;
	}

	/**
	 * @return the poderArea
	 */
	public PoderMalaMDIR getPoderArea() {
		return poderArea;
	}

	/**
	 * @param poderArea the poderArea to set
	 */
	public void setPoderArea(PoderMalaMDIR poderArea) {
		this.poderArea = poderArea;
	}
	
}
