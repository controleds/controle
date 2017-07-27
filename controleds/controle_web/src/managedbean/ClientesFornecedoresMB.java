package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.ClienteFornecedorBO;
import model.ClienteFornecedorPO;
import model.EnderecoPO;
import type.ClienteFornecedorEnum;
import util.cep.ViaCEP;
import util.cep.ViaCEPException;




@ManagedBean(name="clientesFornecedoresMB")
@ViewScoped
public class ClientesFornecedoresMB extends AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ClienteFornecedorBO  clienteFornecedorBO;
	
	
	private ClienteFornecedorPO clienteFornecedorPO =  new  ClienteFornecedorPO();
	private List<ClienteFornecedorPO> clientesPOsList = new ArrayList<ClienteFornecedorPO>();
	private List<ClienteFornecedorPO> fornecedoresPOsList = new ArrayList<ClienteFornecedorPO>();
	private EnderecoPO enderecoPO = new EnderecoPO();
	
		
	@PostConstruct
	public void ini(){
		this.clientesPOsList = clienteFornecedorBO.findClientes();
		this.fornecedoresPOsList = clienteFornecedorBO.findFornecedores();
		this.clienteFornecedorPO =  new  ClienteFornecedorPO();
		this.enderecoPO = new EnderecoPO();
	}
	
	
	
	public void salvarCli(){
		clienteFornecedorPO.setTipo(ClienteFornecedorEnum.CLIENTE.getValue());
		salvar();
		menssagemSucesso("Cliente Salvo Com Sucesso!");
		this.ini();
	}
	
	
	public void salvarFor(){
		clienteFornecedorPO.setTipo(ClienteFornecedorEnum.FORNECEDOR.getValue());
		salvar();
		menssagemSucesso("Fornecedor Salvo Com Sucesso!");
		this.ini();
	}
	
	public void salvar(){
		clienteFornecedorPO.setEnderecoPO(enderecoPO);
		try {
			this.clienteFornecedorBO.save(clienteFornecedorPO);
		} catch (Exception e) {
			menssagemErro("Erro: Não Foi Possivel Salvar..");
		}
		
	}
	
	
	
	public void buscarCep(){
		ViaCEP cep = new ViaCEP();
		try {
			Long baseId = null;
			if (enderecoPO.getEnderecoId() != null) baseId = enderecoPO.getEnderecoId();  
			enderecoPO = cep.buscar(enderecoPO.getCep());
			if (baseId != null) enderecoPO.setEnderecoId(baseId);
		} catch (ViaCEPException e) {
			e.printStackTrace();
		}
	}
	
	
	    
	
	
	public void selectCli(ClienteFornecedorPO clienteFornecedorPO){
		this.clienteFornecedorPO = clienteFornecedorPO;
		this.enderecoPO = clienteFornecedorPO.getEnderecoPO();
	}



	public ClienteFornecedorBO getClienteFornecedorBO() {
		return clienteFornecedorBO;
	}



	public void setClienteFornecedorBO(ClienteFornecedorBO clienteFornecedorBO) {
		this.clienteFornecedorBO = clienteFornecedorBO;
	}



	public ClienteFornecedorPO getClienteFornecedorPO() {
		return clienteFornecedorPO;
	}



	public void setClienteFornecedorPO(ClienteFornecedorPO clienteFornecedorPO) {
		this.clienteFornecedorPO = clienteFornecedorPO;
	}



	


	public List<ClienteFornecedorPO> getClientesPOsList() {
		return clientesPOsList;
	}



	public void setClientesPOsList(List<ClienteFornecedorPO> clientesPOsList) {
		this.clientesPOsList = clientesPOsList;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public EnderecoPO getEnderecoPO() {
		return enderecoPO;
	}



	public void setEnderecoPO(EnderecoPO enderecoPO) {
		this.enderecoPO = enderecoPO;
	}



	public List<ClienteFornecedorPO> getFornecedoresPOsList() {
		return fornecedoresPOsList;
	}



	public void setFornecedoresPOsList(List<ClienteFornecedorPO> fornecedoresPOsList) {
		this.fornecedoresPOsList = fornecedoresPOsList;
	}
	
	


	
	
}
