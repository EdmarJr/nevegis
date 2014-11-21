package br.jus.stj.sigeven.persistence.to;

import br.jus.stj.sigeven.entity.db2.maladir.CargoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.EsferaMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.OrgaoMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.PoderMalaMDIR;
import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;

public class FiltroPesquisaParticipanteEventoTO {
	
	private String nome;
	private Character sexo;
	private Character situacao;
	private CargoMDIR cargo;
	private OrgaoMDIR orgao;
	private TratamentoMDIR tratamento;
	private PoderMalaMDIR poderArea;
	private EsferaMDIR esfera;
	private String uf;
	private String cidade;
	private Boolean presencaConfirmada;
	private Boolean presencaNaoConfirmada;
	
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
	 * @return the sexo
	 */
	public Character getSexo() {
		return sexo;
	}
	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}
	/**
	 * @return the situacao
	 */
	public Character getSituacao() {
		return situacao;
	}
	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(Character situacao) {
		this.situacao = situacao;
	}
	/**
	 * @return the cargo
	 */
	public CargoMDIR getCargo() {
		return cargo;
	}
	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(CargoMDIR cargo) {
		this.cargo = cargo;
	}
	/**
	 * @return the orgao
	 */
	public OrgaoMDIR getOrgao() {
		return orgao;
	}
	/**
	 * @param orgao the orgao to set
	 */
	public void setOrgao(OrgaoMDIR orgao) {
		this.orgao = orgao;
	}
	/**
	 * @return the tratamento
	 */
	public TratamentoMDIR getTratamento() {
		return tratamento;
	}
	/**
	 * @param tratamento the tratamento to set
	 */
	public void setTratamento(TratamentoMDIR tratamento) {
		this.tratamento = tratamento;
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
	/**
	 * @return the esfera
	 */
	public EsferaMDIR getEsfera() {
		return esfera;
	}
	/**
	 * @param esfera the esfera to set
	 */
	public void setEsfera(EsferaMDIR esfera) {
		this.esfera = esfera;
	}
	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}
	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}
	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}
	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	/**
	 * @return the presencaConfirmada
	 */
	public Boolean getPresencaConfirmada() {
		return presencaConfirmada;
	}
	/**
	 * @param presencaConfirmada the presencaConfirmada to set
	 */
	public void setPresencaConfirmada(Boolean presencaConfirmada) {
		this.presencaConfirmada = presencaConfirmada;
	}
	/**
	 * @return the presencaNaoConfirmada
	 */
	public Boolean getPresencaNaoConfirmada() {
		return presencaNaoConfirmada;
	}
	/**
	 * @param presencaNaoConfirmada the presencaNaoConfirmada to set
	 */
	public void setPresencaNaoConfirmada(Boolean presencaNaoConfirmada) {
		this.presencaNaoConfirmada = presencaNaoConfirmada;
	}
	
	
	
}
