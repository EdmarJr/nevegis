package br.jus.stj.sigeven.view.controller.importarListaParticipanteExcel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.util.Base64;

import br.jus.stj.sigeven.business.grupoparticipante.GrupoParticipanteBean;
import br.jus.stj.sigeven.business.importarListaParticipanteExcel.ImportarListaParticipanteExcelBean;
import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.SetorSGVEN;
import br.jus.stj.sigeven.util.ConstantesSigeven;
import br.jus.stj.sigeven.util.LeitorExcel;
import br.jus.stj.sigeven.view.controller.ParametersRequestController;
import br.jus.stj.sigeven.vo.ParticipanteVO;

@ManagedBean
@ViewScoped
public class ImportarListaParticipanteExcelController extends ParametersRequestController {
	
	private static final long serialVersionUID = -8456280180533412904L;

	private boolean exibeMensagem;
	
	private String dialogMensagem;
	
	private boolean sucesso;
	
	private EventoSGVEN evento;
	
	private List<GrupoMDIR> listaGrupoParticipante;
	
	private GrupoMDIR grupoParticipante;
	
	private String enderecoArquivo;
	
	private List<SetorSGVEN> listaSetor;
	
	private boolean mensagemNomeVazio;
	
	private final static String PAGE_MANTER_GRUPO_PARTICIPANTE = "../grupoParticipante/consultar.jsf";
	
	private final static String PAGE_MANTER_CONVIDADO = "../manterListaConvidado/consultarConvidado.xhtml";
	
	private final static String PAGE_ALTERAR_EVENTO = "../manterEvento/alterar.jsf";
	
	@EJB
	private ImportarListaParticipanteExcelBean importacaoBean;
	
	@EJB
	private GrupoParticipanteBean grupoParticipanteBean;
	
	private List<ParticipanteVO> listaImportadaParticipantes;
	
	private String cor;
	private Long grupo;
	private SetorSGVEN setor;
	
	@PostConstruct
	public void init() {
		
		exibeMensagem = false;
		
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		if (requestMap.get("eventoView") != null){
			evento = (EventoSGVEN)requestMap.get("eventoView");
			
			
			if (requestMap.get("grupoParticipante") != null){
				grupoParticipante = (GrupoMDIR)requestMap.get("grupoParticipante");
			}else{
				grupoParticipante = new GrupoMDIR();	
			}
			
			listaGrupoParticipante = grupoParticipanteBean.recuperarTodos();
			
			listaSetor = importacaoBean.listaSetor();
			if(setor==null){
				setor = new SetorSGVEN();
			}
			
		}
	}
	
	public void handleFileUpload() {
		setMensagemNomeVazio(false);
		try {
			LeitorExcel le = new LeitorExcel();
			InputStream myInputStream = new ByteArrayInputStream(Base64.decode(getEnderecoArquivo().split(",")[1])); 
			listaImportadaParticipantes = le.lerStream(myInputStream, ConstantesSigeven.TIPO_ARQUIVO_XLSX);
			if(verificaNomeVazio()){
				listaImportadaParticipantes = new ArrayList<ParticipanteVO>();
				setMensagemNomeVazio(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	    
	public String comandoCancelar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return getPaginaAnterior();
	}
	
	public String direcionaParticipante() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return PAGE_ALTERAR_EVENTO;
	}
	
	 private boolean verificaNomeVazio() {
		for(ParticipanteVO participante : listaImportadaParticipantes){
			if(participante.getNome()==null || participante.getNome().equals("")){
				return true;
			}
		}
		return false;
	}


	public String direcionaManterGrupoParticipante() {
		setPaginaDestino(PAGE_MANTER_GRUPO_PARTICIPANTE);
		Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return getPaginaDestino();
	}


	

	public String comandoSalvar(){
		if(listaImportadaParticipantes == null || listaImportadaParticipantes.isEmpty()){
			FacesContext.getCurrentInstance().addMessage("formPrincipal:messages", new FacesMessage(FacesMessage.SEVERITY_ERROR,getMensagem("msg", "importacao.excel.obrigatorio"),getMensagem("msg", "importacao.excel.obrigatorio")));
			return "";
		}
		importacaoBean.salvarParticipantes(listaImportadaParticipantes, evento, setor, grupo);
		dialogMensagem = getMensagem("msg", "msg.msg001.inclusaosucesso");
		exibeMensagem = true;
		return null;
	}
	
	

	public boolean isExibeMensagem() {
		return exibeMensagem;
	}

	public void setExibeMensagem(boolean exibeMensagem) {
		this.exibeMensagem = exibeMensagem;
	}

	public String getDialogMensagem() {
		return dialogMensagem;
	}

	public void setDialogMensagem(String dialogMensagem) {
		this.dialogMensagem = dialogMensagem;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}


	public List<ParticipanteVO> getListaImportadaParticipantes() {
		return listaImportadaParticipantes;
	}


	public void setListaImportadaParticipantes(
			List<ParticipanteVO> listaImportadaParticipantes) {
		this.listaImportadaParticipantes = listaImportadaParticipantes;
	}


	public String getCor() {
		return cor;
	}


	public void setCor(String cor) {
		this.cor = cor;
	}

	public Long getGrupo() {
		return grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

	public EventoSGVEN getEvento() {
		return evento;
	}


	public void setEvento(EventoSGVEN evento) {
		this.evento = evento;
	}

	public String getEnderecoArquivo() {
		return enderecoArquivo;
	}

	public void setEnderecoArquivo(String enderecoArquivo) {
		this.enderecoArquivo = enderecoArquivo;
	}

	public boolean isMensagemNomeVazio() {
		return mensagemNomeVazio;
	}

	public void setMensagemNomeVazio(boolean mensagemNomeVazio) {
		this.mensagemNomeVazio = mensagemNomeVazio;
	}

	public List<SetorSGVEN> getListaSetor() {
		return listaSetor;
	}

	public void setListaSetor(List<SetorSGVEN> listaSetor) {
		this.listaSetor = listaSetor;
	}


	public SetorSGVEN getSetor() {
		return setor;
	}

	public void setSetor(SetorSGVEN setor) {
		this.setor = setor;
	}

	/**
	 * @return the listaGrupoParticipante
	 */
	public List<GrupoMDIR> getListaGrupoParticipante() {
		return listaGrupoParticipante;
	}

	/**
	 * @param listaGrupoParticipante the listaGrupoParticipante to set
	 */
	public void setListaGrupoParticipante(List<GrupoMDIR> listaGrupoParticipante) {
		this.listaGrupoParticipante = listaGrupoParticipante;
	}

	/**
	 * @return the grupoParticipante
	 */
	public GrupoMDIR getGrupoParticipante() {
		return grupoParticipante;
	}

	/**
	 * @param grupoParticipante the grupoParticipante to set
	 */
	public void setGrupoParticipante(GrupoMDIR grupoParticipante) {
		this.grupoParticipante = grupoParticipante;
	}
	
}
