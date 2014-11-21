package br.jus.stj.sigeven.view.controller.evento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.jus.stj.commons.view.enums.AtivoInativoEnum;
import br.jus.stj.commons.view.enums.SimNaoEnum;
import br.jus.stj.sigeven.business.evento.EventoBean;
import br.jus.stj.sigeven.business.evento.LocalEventoBean;
import br.jus.stj.sigeven.business.fornecedor.FornecedorBean;
import br.jus.stj.sigeven.business.grupoparticipante.GrupoParticipanteBean;
import br.jus.stj.sigeven.business.mestrecerimonia.MestreCerimoniaBean;
import br.jus.stj.sigeven.business.servicoprestadoevento.ServicoPrestadoEventoBean;
import br.jus.stj.sigeven.business.tipoevento.TipoEventoBean;
import br.jus.stj.sigeven.business.tiposervico.TipoServicoBean;
import br.jus.stj.sigeven.business.usuario.UsuarioBean;
import br.jus.stj.sigeven.entity.db2.db2sa.Usuario;
import br.jus.stj.sigeven.entity.db2.maladir.GrupoMDIR;
import br.jus.stj.sigeven.entity.db2.sigeven.EventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.FornecedorSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.GrupoParticipanteEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.LocalEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.MestreCerimoniaSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.ServicoPrestadoEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoEventoSGVEN;
import br.jus.stj.sigeven.entity.db2.sigeven.TipoServicoSGVEN;
import br.jus.stj.sigeven.util.ChaveDupla;
import br.jus.stj.sigeven.view.controller.GenericController;

@ManagedBean
@ViewScoped 
public class EventoController extends GenericController {
	
	private static final long serialVersionUID = -8456280180533412904L;

	private List<EventoSGVEN> listaEvento;
	
	private List<ServicoPrestadoEventoSGVEN> listaServicoPrestadoEvento;
	private List<ServicoPrestadoEventoSGVEN> listaServicoPrestadoEventoRemover;
	private List<ServicoPrestadoEventoSGVEN> listaServicoPrestadoEventoAlterar;
	private List<FornecedorSGVEN> listaFornecedor;
	private List<TipoServicoSGVEN> listaTipoServico;
	private List<MestreCerimoniaSGVEN> listaMestreCerimonia;
	
	private List<Usuario> listaUsuario;
	
	private ServicoPrestadoEventoSGVEN servicoPrestadoEvento;
	private FornecedorSGVEN fornecedor;
	private Long fornecedorId;
	private TipoServicoSGVEN tipoServico;
	private Long tipoServicoId;
	private MestreCerimoniaSGVEN mestreCerimonia;
	private Long mestreCerimoniaId;
	private Usuario usuario;
	private Long usuarioId;
	
	
	private List<ChaveDupla> listServicoDupla;
	
	public Long getTipoServicoId() {
		return tipoServicoId;
	}

	public void setTipoServicoId(Long tipoServicoId) {
		this.tipoServicoId = tipoServicoId;
	}

	public Long getMestreCerimoniaId() {
		return mestreCerimoniaId;
	}

	public void setMestreCerimoniaId(Long mestreCerimoniaId) {
		this.mestreCerimoniaId = mestreCerimoniaId;
	}

	private List<LocalEventoSGVEN> listaLocal;
	private LocalEventoSGVEN localEvento;
	private Long idLocalEvento;
	
	private List<TipoEventoSGVEN> listaTipoEvento;
	private TipoEventoSGVEN tipoEvento;
	private Long idTipoEvento;
	
	private List<GrupoMDIR> listaGrupoParticipante;
	private GrupoParticipanteEventoSGVEN grupoParticipante;
	private Long idGrupoParticipante;
		
	private EventoSGVEN evento;
	
	private char status;
	private char traducaoLibra;
	private char taquigrafia;	
	
	private boolean exibeMensagem;
	private boolean exibeMensagemNomeRepetido;
	private boolean exibeMensagemListaConvidado;
	private boolean exibeMensagemErro;
	private boolean exibeImportacao;
	
	private String dialogMensagem;
	
	private boolean sucesso;
	private boolean verificaNome;
	
	private FacesMessage message;
	
	private final static String PAGE_VISUALIZAR = "visualizar.jsf";
	private final static String PAGE_CONSULTAR = "consultarEvento.jsf";
	private final static String PAGE_ALTERAR = "alterar.jsf";
	private final static String PAGE_EXCLUIR = "excluir.jsf";
	private final static String PAGE_CONSULTAR_CONVIDADO = "../manterListaConvidado/consultarConvidado.jsf";
	private final static String PAGE_INCLUIR_NAO_CADASTRADO = "../manterListaConvidado/incluirParticipante.jsf";
	private final static String PAGE_IMPORTAR_EXCEL = "../importarListaParticipanteExcel/importacao.jsf";
	private final static String PAGE_REGISTRO_DUPLICADO = "../verificarRegistrosDuplicados/verificarRegistrosDuplicados.jsf";
	private final static String PAGE_IMPORTAR_MALA_DIRETA = "../importarParticipanteMalaDireta/importacaoMalaDireta.jsf";
		
	@EJB
	private EventoBean eventoBean;
	
	@EJB
	private LocalEventoBean localEventoBean;

	@EJB
	private GrupoParticipanteBean grupoParticipanteBean;

	@EJB
	private TipoEventoBean tipoEventoBean;
	
	@EJB
	private FornecedorBean fornecedorBean;
	
	@EJB
	private TipoServicoBean tipoServicoBean;
	
	@EJB
	private ServicoPrestadoEventoBean servicoPrestadoEventoBean;
	
	@EJB
	private MestreCerimoniaBean mestreCerimoniaBean;

	@EJB
	private UsuarioBean usuarioBean;
	
	@PostConstruct
	public void init() {

		listServicoDupla = new LinkedList<ChaveDupla>();

		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		if (requestMap.get("eventoView") != null) {

			evento = (EventoSGVEN) requestMap.get("eventoView");
			listaServicoPrestadoEvento = servicoPrestadoEventoBean
					.recuperarTodosPorEvento(evento);
			listaServicoPrestadoEventoRemover = new ArrayList<ServicoPrestadoEventoSGVEN>();
			listaServicoPrestadoEventoAlterar = new ArrayList<ServicoPrestadoEventoSGVEN>();
			populaObjTela();
		} else if (evento == null) {

			evento = new EventoSGVEN();
			listaEvento = eventoBean.recuperarTodos();
			status = 'S';
			taquigrafia = SimNaoEnum.SIM.getValor();
			traducaoLibra = SimNaoEnum.SIM.getValor();

		}
		if (listaServicoPrestadoEvento == null)
			listaServicoPrestadoEvento = new ArrayList<ServicoPrestadoEventoSGVEN>();

	}

	public void cancelar(){
		exibeMensagem = false;
		exibeMensagemNomeRepetido = false;
		return;
	}
	
	public void verificaInclusao() {
		boolean verificaNome = eventoBean.verificaMesmoNomeincluir(evento);
		if(verificaNome){
			abrirModal("dialogMensagemMesmoNome");
			dialogMensagem = getMensagem("msg", "msg.msg028.existeeventonomeinformado");
			return;
		}else{
			comandoIncluir();
		}
	}	
	
	
	public void comandoIncluir() {
		fecharModal("dialogMensagemMesmoNome");
		populaEvento();
		if (!validarDataInicioMenorQueDataFim(getEvento().getDataInicio(),
				getEvento().getDataFim(), "formPrincipal:dataFim")) {
			return;
		}
		if (evento.getNome() != null && !"".equals(evento.getNome())
				&& evento.getId() == null) {
			evento.setNome(evento.getNome().trim());
			evento = eventoBean.incluir(evento, listaServicoPrestadoEvento);

			exibeMensagemNomeRepetido = false;

			if (evento != null && evento.getId() != null && evento.getId() > 0) {
				dialogMensagem = getMensagem("msg",
						"msg.msg001.inclusaosucesso");
				sucesso = true;
				abrirModal("dialogMensagem");
			}
		} else if (evento.getId() != null && evento.getId() > 0) {
			comandoAlterarSalvar();
		} else {
			dialogMensagem = getMensagem("msg", "msg.msg005.camposobrigatorios");
			sucesso = false;
			abrirModal("dialogMensagem");
		}

	}
	
	public String direciona(){
		
		fecharModal("dialogMensagem");
		if(getPaginaDestino()==null || getPaginaDestino().equals("")){
			setPaginaDestino(PAGE_CONSULTAR);
			resetarAtributos();
		}else{
			Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
			requestMap.put("eventoView", evento);
		}
		
		
		exibeMensagem = false;


		return getPaginaDestino();
	}
	
	private void populaEvento() {
		
		getEvento().setStatus(status);
		getEvento().setTraducaoLibra(traducaoLibra);
		getEvento().setTaquigrafia(taquigrafia);
		
		
		if(getIdTipoEvento()!=null && getIdTipoEvento()>0){
			getEvento().setTipoEvento(new TipoEventoSGVEN());
			getEvento().getTipoEvento().setId(getIdTipoEvento());
		}
		if(getIdLocalEvento()!=null && getIdLocalEvento()>0){
			getEvento().setLocal(new LocalEventoSGVEN());
			getEvento().getLocal().setId(getIdLocalEvento());
		}
		if(getUsuarioId() != null && getUsuarioId() > 0){
			getEvento().setUsuario(new Usuario());
			getEvento().getUsuario().setId(usuarioId);
		}
		if(getMestreCerimoniaId() != null && getMestreCerimoniaId() > 0){
			getEvento().setMestreCerimoniaSGVEN(new MestreCerimoniaSGVEN());
			getEvento().getMestreCerimoniaSGVEN().setId(mestreCerimoniaId);
			
		}
		if(evento.getParticipantes() != null ){
			evento.setParticipantes(null);
			
		}
	}


	private void populaObjTela() {

		status = getEvento().getStatus();
		traducaoLibra = getEvento().getTraducaoLibra();
		taquigrafia = getEvento().getTaquigrafia();
		if (getEvento().getTipoEvento() != null
				&& getEvento().getTipoEvento().getId() > 0)
			idTipoEvento = getEvento().getTipoEvento().getId();
		if (getEvento().getLocal() != null
				&& getEvento().getLocal().getId() > 0)
			idLocalEvento = getEvento().getLocal().getId();
		if (getEvento().getMestreCerimoniaSGVEN() != null
				&& getEvento().getMestreCerimoniaSGVEN().getId() > 0) {

			mestreCerimoniaId = getEvento().getMestreCerimoniaSGVEN().getId();
		}
		if (getEvento().getUsuario() != null
				&& getEvento().getUsuario().getId() > 0)
			usuarioId = getEvento().getUsuario().getId();

		if (getListaServicoPrestadoEvento().size() > 0) {

			for (ServicoPrestadoEventoSGVEN temp : listaServicoPrestadoEvento) {

				listServicoDupla.add(new ChaveDupla(temp.getFornecedorSGVEN()
						.getId(), temp.getTipoServicoSGVEN().getId()));
			}

		}
	}

	public void comandoLimpar() {
		resetarAtributosAlterar();
		listaEvento = eventoBean.recuperarTodos();
	}
	
	
	public void comandoPesquisar() {
		validarCampoConsulta(); 	
		if(!validarDataInicioMenorQueDataFim(getEvento().getDataInicio(), getEvento().getDataFim(), "formPrincipal:dataFim")){
			return;
		}
		listaEvento = eventoBean.pesquisaPorParametros(evento);
		System.out.println("");
	}
	
	private void validarCampoConsulta() {
		populaEvento();
		int i = 0;
		if(evento.getNome()!=null && !evento.getNome().equals("")){
			i++;
		}if(evento.getTipoEvento()!=null && evento.getTipoEvento().getId()>0){
			i++;
		}if(evento.getDataInicio()!=null ){
			i++;
		}if(evento.getDataFim()!=null){
			i++;
		}if(evento.getStatus()!='\0'){
			i++;
		}if(evento.getLocal()!=null && evento.getLocal().getId()>0){
			i++;
		}
		if(i==0){
			sucesso = false;
			exibeMensagem = true;
			dialogMensagem = getMensagem("msg", "msg.msg009.nenhumregistro");
			return ;
		}else{
			exibeMensagem = false;
		}
	}

	public String comandoVisualizar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return PAGE_VISUALIZAR;
	}
	
	public String comandoAlterar() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return PAGE_ALTERAR;
	}
	
	
	public void verificaAlteracao() {
		boolean verificaNome = eventoBean.verificaMesmoNomeincluir(evento);
		if(verificaNome){
			abrirModal("dialogMensagemMesmoNome");
			dialogMensagem = getMensagem("msg", "msg.msg028.existeeventonomeinformado");
			return;
		}else{
			comandoAlterarSalvar();
		}
	}	
	
	public void comandoAlterarSalvar() {
		fecharModal("dialogMensagemMesmoNome");
		populaEvento();
		if(!validarDataInicioMenorQueDataFim(getEvento().getDataInicio(), getEvento().getDataFim(), "formPrincipal:dataFim")){
			return;
		}
		
		evento.setNome(evento.getNome().trim());
		
		if(!"".equals(evento.getNome())){

			evento = eventoBean.alterarEvento(evento,
					listaServicoPrestadoEventoAlterar,
					listaServicoPrestadoEventoRemover);

			exibeMensagemNomeRepetido = false;
			exibeMensagem = true;
			
			if(evento!=null && evento.getId()!=null && evento.getId()>0){
				dialogMensagem = getMensagem("msg", "msg.msg002.alteracaosucesso");
				sucesso = true;
				abrirModal("dialogMensagem");
				
			}
		} else {
			dialogMensagem = getMensagem("msg", "msg.msg005.camposobrigatorios");
			sucesso = false;
			abrirModal("dialogMensagem");
		}
		
	}
	
	public String comandoExcluir() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return PAGE_EXCLUIR;
	}
	
	public void removerServico(){
		
		if (servicoPrestadoEvento != null) {
			listaServicoPrestadoEvento.remove(servicoPrestadoEvento);
			removeChaveDupla();

		}
	}
	
	public void removerServicoAlterar(){
		if(servicoPrestadoEvento != null){
			listaServicoPrestadoEvento.remove(servicoPrestadoEvento);
			if(servicoPrestadoEvento.getEventoSGVEN() != null &&
					servicoPrestadoEvento.getEventoSGVEN().getId() != null)
				listaServicoPrestadoEventoRemover.add(servicoPrestadoEvento);
			
			removeChaveDupla();
		}
	}

	private void removeChaveDupla() {
		for (ChaveDupla temp : listServicoDupla) {

			if (temp.getIdPrimeiro().equals(
					servicoPrestadoEvento.getFornecedorSGVEN().getId())
					&& temp.getIdSegundo().equals(
							servicoPrestadoEvento.getTipoServicoSGVEN().getId())) {

				listServicoDupla.remove(temp);
				break;
			}
		}
	}
	
	private boolean contemChaveDupla(ChaveDupla chaveDupla) {
		for (ChaveDupla temp : listServicoDupla) {

			if (temp.getIdPrimeiro().equals(
					chaveDupla.getIdPrimeiro())
					&& temp.getIdSegundo().equals(
							chaveDupla.getIdSegundo())) {

				return true;
			}
		}

		return false;

	}
	
	public String comandoListarConvidados() {
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
		return PAGE_CONSULTAR_CONVIDADO;
	}
	
	public void comandoExcluirSalvar() {
		
		/*for (ServicoPrestadoEventoSGVEN temp : listaServicoPrestadoEvento) {
			servicoPrestadoEventoBean.excluir(temp);
		}*/
		eventoBean.excluir(evento);
		exibeMensagem = true;
		fecharModal("dialogConfirmarExcluir");
		abrirModal("dialogMensagem");
		
	}
	
	public void comboLocal(){
		
		listaLocal = localEventoBean.recuperarTodos();
	}
	
	public void comboFornecedore(){
		
		listaFornecedor = fornecedorBean.recupearTodos();
	}
	
	public void comboMestreCerimonia(){
		
		listaMestreCerimonia = mestreCerimoniaBean.recuperarTodos();
	}
	
	public void comboTipoServico(){
	
		listaTipoServico = tipoServicoBean.recupearTodos();
	}
	
	public void comboUsuario(){
		
		listaUsuario = usuarioBean.recuperarTodos();
	}
	public void habilitaMensagemListaConvidado(){
		exibeMensagem = false;
		exibeMensagemListaConvidado = true;
		return;
	}
	
	public void desabiltaMensagemErro(){
		exibeMensagem = false;
		exibeMensagemErro = false;
		return;
	}
	
	public void comboGrupoParticipante(){
		listaGrupoParticipante = grupoParticipanteBean.recuperarTodos();
	}
	
	public void comboTipoEvento(){
		listaTipoEvento = tipoEventoBean.recuperarTodos();
	}
	
	
	public String ponteImportacaoAlterar(){
		fecharModal("dialogImportacao");
		comandoAlterarSalvar();
		setPaginaDestino(PAGE_IMPORTAR_EXCEL);
		addEventoSession();
		return getPaginaDestino();
	}
	
	public String ponteImportacaoIncluir(){
		fecharModal("dialogImportacao");
		verificaInclusao();
		setPaginaDestino(PAGE_IMPORTAR_EXCEL);
		addEventoSession();
		return getPaginaDestino();
	}
	
	private void addEventoSession(){
		Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		requestMap.put("eventoView", evento);
	}
	
	public void ponteImportacaoAlterarMalaDireta(){
		comandoAlterarSalvar();
		setPaginaDestino(PAGE_IMPORTAR_MALA_DIRETA);
		setExibeImportacao(false);
	}
	
	public void ponteImportacaoIncluirMalaDireta(){
		verificaInclusao();
		setPaginaDestino(PAGE_IMPORTAR_MALA_DIRETA);
	}
	
	
	public void carregaModalImportacao(){
		abrirModal("dialogImportacao");
	}
	
	public void ponteParticipanteNaoCadastradoIncluir(){
		verificaInclusao();
		setPaginaDestino(PAGE_INCLUIR_NAO_CADASTRADO);
	}
	
	public String ponteListaParticipante(){
		setPaginaDestino(PAGE_CONSULTAR_CONVIDADO);
		return direciona();
	}
	
	public void ponteRegistroDuplicadoIncluir(){
		verificaInclusao();
		setPaginaDestino("../verificarRegistrosDuplicados/verificarRegistrosDuplicados.xhtml");
	}
	

	
	public void ponteParticipanteNaoCadastradoAlterar(){
		comandoAlterarSalvar();
		setPaginaDestino(PAGE_INCLUIR_NAO_CADASTRADO);
	}
	
	public void ponteRegistroDuplicadoAlterar(){
		comandoAlterarSalvar();
		setPaginaDestino(PAGE_REGISTRO_DUPLICADO);
	}
	
	public void adicionarServicoPrestado(){
		
		TipoServicoSGVEN tipo = null;
		FornecedorSGVEN fornecedor = null;
		ChaveDupla chaveDupla;
		
		tipo = obterTipo(tipo);
		fornecedor = obterFornecedor(fornecedor);
		
		chaveDupla = new ChaveDupla(fornecedorId, tipoServicoId);
		if (!contemChaveDupla(chaveDupla)) {
			listaServicoPrestadoEvento.add(new ServicoPrestadoEventoSGVEN(
					new EventoSGVEN(), tipo, fornecedor));
			if(listaServicoPrestadoEventoAlterar != null){
				listaServicoPrestadoEventoAlterar
						.add(new ServicoPrestadoEventoSGVEN(new EventoSGVEN(),
								tipo, fornecedor));
			}
			listServicoDupla.add(chaveDupla);
			limparCampoDailogServico();
			RequestContext.getCurrentInstance().execute("PF('addDialogTipoServFornVar').hide()");
		} else {

			this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não é possível incluir. ",
					getMensagem("msg", "msg.MSG093.tipo.servico.fornecedor"));

			RequestContext.getCurrentInstance().showMessageInDialog( message);
			
		}

		
		
		
	}

	private void limparCampoDailogServico() {
		fornecedorId = null;
		tipoServicoId = null;
		listaTipoServico = null;
		listaFornecedor = null;
		
	}
	
	public void abrirDailogservico(){
		limparCampoDailogServico();
		RequestContext.getCurrentInstance().execute("PF('addDialogTipoServFornVar').show()");
	}
	
	private FornecedorSGVEN obterFornecedor(FornecedorSGVEN fornecedor) {
		if(fornecedorId != null){
			for (FornecedorSGVEN  temp: listaFornecedor) {
				if(temp.getId().equals(fornecedorId)){
					fornecedor = temp;
					break;
				}
			}
		}
		return fornecedor;
	}

	private TipoServicoSGVEN obterTipo(TipoServicoSGVEN tipo) {
		if(tipoServicoId != null){
			for (TipoServicoSGVEN temp : listaTipoServico) {
				if(temp.getId().equals(tipoServicoId)){
					tipo = temp;
					break;
				}
			}
		}
		return tipo;
	}
	
	
	
		
	public void setFornecedorId(Long fornecedorId) {
		this.fornecedorId = fornecedorId;
	}
	

	public Long getFornecedorId() {
		return fornecedorId;
	}

	private void resetarAtributos() {

		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context
				.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();

	}
	
	private void resetarAtributosAlterar() {		
		localEvento = null;
		idLocalEvento = null;
		tipoEvento = null;
		idTipoEvento = null;		
		grupoParticipante = null;
		idGrupoParticipante = null;
		mestreCerimoniaId = null;
		usuarioId = null;
		status = '0';
		traducaoLibra = '0';
		taquigrafia = '0';
		evento.setNome("");
		evento.setStatus('S');
		evento.setTraducaoLibra('N');
		evento.setTaquigrafia('N');
		evento.setDataFim(null);
		evento.setDataInicio(null);
		evento.setHora(null);
		evento.setMestreCerimoniaSGVEN(null);
		evento.setObservacao("");
		evento.setCustoFinal(0);
		status = 'S';
		evento.setUsuario(null);
		traducaoLibra = 'N';
		taquigrafia = 'N';
	}

	public void addFornecedor(){
		
		
	}
	
	public void addTipoServico(){
		
	}
	
	public List<EventoSGVEN> getListaEvento() {
		return listaEvento;
	}

	public EventoSGVEN getEvento() {
		if(evento==null)
			evento = new EventoSGVEN();
		return evento;
	}

	public void setEvento(EventoSGVEN evento) {
		this.evento = evento;
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

	public List<LocalEventoSGVEN> getListaLocal() {
		if(listaLocal==null || listaLocal.isEmpty()){
			comboLocal();
		}
		return listaLocal;
	}
	
	public List<FornecedorSGVEN> getListaFornecedor(){
		if(listaFornecedor == null || listaFornecedor.isEmpty()){
			comboFornecedore();
		}
		return listaFornecedor;
		
	}
	
	
	public void setListaFornecedor(List<FornecedorSGVEN> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}
	
	
	
	public List<TipoServicoSGVEN> getListaTipoServico() {
		
		if(listaTipoServico == null || listaTipoServico.isEmpty()){
			comboTipoServico();
		}
		return listaTipoServico;
	}

	public void setListaTipoServico(List<TipoServicoSGVEN> listaTipoServico) {
		this.listaTipoServico = listaTipoServico;
	}

	public void setListaLocal(List<LocalEventoSGVEN> listaLocal) {
		this.listaLocal = listaLocal;
	}

	public LocalEventoSGVEN getLocalEvento() {
		return localEvento;
	}

	public void setLocalEvento(LocalEventoSGVEN localEvento) {
		this.localEvento = localEvento;
	}

	public List<TipoEventoSGVEN> getListaTipoEvento() {
		if(listaTipoEvento==null || listaTipoEvento.isEmpty()){
			comboTipoEvento();
		}
		return listaTipoEvento;
	}
	
	public void setListaTipoEvento(List<TipoEventoSGVEN> listaTipoEvento) {
		this.listaTipoEvento = listaTipoEvento;
	}

	public TipoEventoSGVEN getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEventoSGVEN tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<GrupoMDIR> getListaGrupoParticipante() {
		if(listaGrupoParticipante==null || listaGrupoParticipante.isEmpty()){
			comboGrupoParticipante();
		}
		return listaGrupoParticipante;
	}

	public void setListaGrupoParticipante(
			List<GrupoMDIR> listaGrupoParticipante) {
		this.listaGrupoParticipante = listaGrupoParticipante;
	}

	public GrupoParticipanteEventoSGVEN getGrupoParticipante() {
		return grupoParticipante;
	}

	public void setGrupoParticipante(GrupoParticipanteEventoSGVEN grupoParticipante) {
		this.grupoParticipante = grupoParticipante;
	}

	public void setListaEvento(List<EventoSGVEN> listaEvento) {
		this.listaEvento = listaEvento;
	}
	
	public List<AtivoInativoEnum> getAtivoEnum(){  
		   return Arrays.asList(AtivoInativoEnum.values());  
		}

	public List<SimNaoEnum> getTraducaoEnum(){  
		   return Arrays.asList(SimNaoEnum.values());  
		}

	public List<SimNaoEnum> getTaquigrafiaEnum(){  
		   return Arrays.asList(SimNaoEnum.values());  
		}


	public Long getIdTipoEvento() {
		return idTipoEvento;
	}

	public void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	public Long getIdLocalEvento() {
		return idLocalEvento;
	}

	public void setIdLocalEvento(Long idLocalEvento) {
		this.idLocalEvento = idLocalEvento;
	}

	public Long getIdGrupoParticipante() {
		return idGrupoParticipante;
	}

	public void setIdGrupoParticipante(Long idGrupoParticipante) {
		this.idGrupoParticipante = idGrupoParticipante;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public char getTraducaoLibra() {
		return traducaoLibra;
	}

	public void setTraducaoLibra(char traducaoLibra) {
		this.traducaoLibra = traducaoLibra;
	}

	public char getTaquigrafia() {
		return taquigrafia;
	}

	public void setTaquigrafia(char taquigrafia) {
		this.taquigrafia = taquigrafia;
	}

	public boolean isVerificaNome() {
		return verificaNome;
	}

	public void setVerificaNome(boolean verificaNome) {
		this.verificaNome = verificaNome;
	}

	public boolean isExibeMensagemNomeRepetido() {
		return exibeMensagemNomeRepetido;
	}

	public void setExibeMensagemNomeRepetido(boolean exibeMensagemNomeRepetido) {
		this.exibeMensagemNomeRepetido = exibeMensagemNomeRepetido;
	}

	public boolean isExibeMensagemListaConvidado() {
		return exibeMensagemListaConvidado;
	}

	public void setExibeMensagemListaConvidado(boolean exibeMensagemListaConvidado) {
		this.exibeMensagemListaConvidado = exibeMensagemListaConvidado;
	}

	public boolean isExibeMensagemErro() {
		return exibeMensagemErro;
	}

	public void setExibeMensagemErro(boolean exibeMensagemErro) {
		this.exibeMensagemErro = exibeMensagemErro;
	}

	public boolean isExibeImportacao() {
		return exibeImportacao;
	}

	public void setExibeImportacao(boolean exibeImportacao) {
		this.exibeImportacao = exibeImportacao;
	}

	public List<MestreCerimoniaSGVEN> getListaMestreCerimonia() {
		if(listaMestreCerimonia == null || listaMestreCerimonia.isEmpty()){
			comboMestreCerimonia();
		}
		return listaMestreCerimonia;
	}

	public void setListaMestreCerimonia(List<MestreCerimoniaSGVEN> listaMestreCerimonia) {
		this.listaMestreCerimonia = listaMestreCerimonia;
	}

	public FornecedorSGVEN getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorSGVEN fornecedor) {
		this.fornecedor = fornecedor;
	}

	public TipoServicoSGVEN getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServicoSGVEN tipoServico) {
		this.tipoServico = tipoServico;
	}

	public MestreCerimoniaSGVEN getMestreCerimonia() {
		return mestreCerimonia;
	}

	public void setMestreCerimonia(MestreCerimoniaSGVEN mestreCerimonia) {
		this.mestreCerimonia = mestreCerimonia;
	}

	public List<ServicoPrestadoEventoSGVEN> getListaServicoPrestadoEvento() {
		return listaServicoPrestadoEvento;
	}

	public void setListaServicoPrestadoEvento(
			List<ServicoPrestadoEventoSGVEN> listaServicoPrestadoEvento) {
		this.listaServicoPrestadoEvento = listaServicoPrestadoEvento;
	}

	public ServicoPrestadoEventoSGVEN getServicoPrestadoEvento() {
		return servicoPrestadoEvento;
	}

	public void setServicoPrestadoEvento(ServicoPrestadoEventoSGVEN servicoPrestadoEvento) {
		this.servicoPrestadoEvento = servicoPrestadoEvento;
	}

	public List<Usuario> getListaUsuario() {
		if( listaUsuario == null || listaUsuario.isEmpty()){
			
			comboUsuario();
		}
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public List<ChaveDupla> getListServicoDupla() {
		return listServicoDupla;
	}

	public void setListServicoDupla(List<ChaveDupla> listServicoDupla) {
		this.listServicoDupla = listServicoDupla;
	}

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}
	
	
}
