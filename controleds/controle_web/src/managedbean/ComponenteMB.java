package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import ejb.ClienteFornecedorBO;
import ejb.ProdutoBO;
import model.ClienteFornecedorPO;
import model.ComponentePO;
import model.EnderecoPO;
import type.ClienteFornecedorEnum;
import util.cep.ViaCEP;
import util.cep.ViaCEPException;




@ManagedBean(name="componenteMB")
@ViewScoped
public class ComponenteMB extends AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdutoBO produtoBO;
	@EJB
	private ClienteFornecedorBO fornecedorBO;
	
	
	private ComponentePO componentePO =  new  ComponentePO();
	private List<ComponentePO> componentePOsList = new ArrayList<ComponentePO>();
	private List<ComponentePO> componentePOsListFilter = new ArrayList<ComponentePO>();
	private List<ClienteFornecedorPO> fornecedorPOsList  = new ArrayList<ClienteFornecedorPO>();
	private List<SelectItem> fornecedoresSelectItems = new ArrayList<>();
	private String fornecedorselectItem = "";
	private ClienteFornecedorPO clienteFornecedorPO =  new  ClienteFornecedorPO();
	private EnderecoPO enderecoPO = new EnderecoPO();
	
	private EnderecoPO viewEnderecoPO = new EnderecoPO();
	private ClienteFornecedorPO viewClienteFornecedorPO =  new  ClienteFornecedorPO();


	private ClienteFornecedorPO fornecedorPO = new ClienteFornecedorPO();
		
	@PostConstruct
	public void ini(){
		componentePO =  new  ComponentePO();
		componentePOsList = produtoBO.findComponentes();
		fornecedorPO = new ClienteFornecedorPO();
		carregaFornecedores();
		fornecedorselectItem = "";
		viewEnderecoPO = new EnderecoPO();
		viewClienteFornecedorPO =  new  ClienteFornecedorPO();
		componentePOsListFilter = produtoBO.findComponentes();
	}
	
	
	public void handleFileUpload(FileUploadEvent event) {
		componentePO.setFoto(event.getFile().getContents());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	
	public void salvarComponente(){
		if (fornecedorselectItem != null && !fornecedorselectItem.equals("")){
			fornecedorPO = fornecedorBO.find(new Long(fornecedorselectItem));
		} else {
			fornecedorPO = null;
		}
		componentePO.setFornecedor(fornecedorPO);
		try {
			produtoBO.salvarComponente(componentePO);
			menssagemSucesso("Componente Salvo com Sucesso!");
			ini();
		} catch (Exception e) {
			menssagemErro(e.getMessage());
		}
		
	}
	
	public void selectFornecedor(ComponentePO componentePO){
		ClienteFornecedorPO fornecedorPO = componentePO.getFornecedor();
		if (fornecedorPO != null){
			this.viewClienteFornecedorPO = componentePO.getFornecedor();
			if (fornecedorPO.getEnderecoPO() != null){
				this.viewEnderecoPO = fornecedorPO.getEnderecoPO();
			} else {
				this.viewEnderecoPO = new EnderecoPO();
			}
		} else {
			this.viewClienteFornecedorPO = new ClienteFornecedorPO();
			this.viewEnderecoPO = new EnderecoPO();
		}
	}
	
	
	private void carregaFornecedores(){
		fornecedoresSelectItems = new ArrayList<>();
		fornecedorPOsList  = fornecedorBO.findFornecedores();
		if (fornecedorPOsList != null && !fornecedorPOsList.isEmpty()){
			for (ClienteFornecedorPO fornecedor : fornecedorPOsList) {
				SelectItem item = new SelectItem();
				item.setLabel(fornecedor.getNome() +" - "+ fornecedor.getSite());
				item.setValue(fornecedor.getClienteFornecedorId().toString());
				this.fornecedoresSelectItems.add(item);
			}
		}
	}
	
	
	public void salvarFor(){
		if (clienteFornecedorPO != null && clienteFornecedorPO.getNome() !=null && !clienteFornecedorPO.getNome().isEmpty()){
			try {
				clienteFornecedorPO.setTipo(ClienteFornecedorEnum.FORNECEDOR.getValue());
				clienteFornecedorPO.setEnderecoPO(enderecoPO);
				fornecedorBO.save(clienteFornecedorPO);
				this.carregaFornecedores();
				menssagemSucesso("Fornecedor salvo com Sucesso.");
			} catch (Exception e) {
				menssagemErro("Não foi possivel adicionar um novo Fornecedor.");
			}
			
		} else {
			menssagemErro("Não Foi possivel adicionar um novo Fornecedor. o campo Nome é Obrigatório.");
		}
	}
	
	
	public void limparPopUp (){
		this.clienteFornecedorPO = new ClienteFornecedorPO();
		this.enderecoPO = new EnderecoPO();
	}
	
	public void selectComponente(){
		if (componentePO.getFornecedor() != null && componentePO.getFornecedor().getClienteFornecedorId() != null){
			this.setFornecedorselectItem(componentePO.getFornecedor().getClienteFornecedorId().toString());
		}
	}

	
	
	
	public List<ComponentePO> getComponentePOsListFilter() {
		return componentePOsListFilter;
	}


	public void setComponentePOsListFilter(List<ComponentePO> componentePOsListFilter) {
		this.componentePOsListFilter = componentePOsListFilter;
	}


	public ProdutoBO getProdutoBO() {
		return produtoBO;
	}



	public void setProdutoBO(ProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}



	public ComponentePO getComponentePO() {
		return componentePO;
	}



	public void setComponentePO(ComponentePO componentePO) {
		this.componentePO = componentePO;
	}



	public List<ComponentePO> getComponentePOsList() {
		return componentePOsList;
	}



	public void setComponentePOsList(List<ComponentePO> componentePOsList) {
		this.componentePOsList = componentePOsList;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public ClienteFornecedorBO getFornecedorBO() {
		return fornecedorBO;
	}



	public void setFornecedorBO(ClienteFornecedorBO fornecedorBO) {
		this.fornecedorBO = fornecedorBO;
	}



	public List<ClienteFornecedorPO> getFornecedorPOsList() {
		return fornecedorPOsList;
	}



	public void setFornecedorPOsList(List<ClienteFornecedorPO> fornecedorPOsList) {
		this.fornecedorPOsList = fornecedorPOsList;
	}



	public ClienteFornecedorPO getFornecedorPO() {
		return fornecedorPO;
	}



	public void setFornecedorPO(ClienteFornecedorPO fornecedorPO) {
		this.fornecedorPO = fornecedorPO;
	}


	public List<SelectItem> getFornecedoresSelectItems() {
		return fornecedoresSelectItems;
	}


	public void setFornecedoresSelectItems(List<SelectItem> fornecedoresSelectItems) {
		this.fornecedoresSelectItems = fornecedoresSelectItems;
	}


	public String getFornecedorselectItem() {
		return fornecedorselectItem;
	}


	public void setFornecedorselectItem(String fornecedorselectItem) {
		this.fornecedorselectItem = fornecedorselectItem;
	}


	public ClienteFornecedorPO getClienteFornecedorPO() {
		return clienteFornecedorPO;
	}


	public void setClienteFornecedorPO(ClienteFornecedorPO clienteFornecedorPO) {
		this.clienteFornecedorPO = clienteFornecedorPO;
	}


	public EnderecoPO getEnderecoPO() {
		return enderecoPO;
	}


	public void setEnderecoPO(EnderecoPO enderecoPO) {
		this.enderecoPO = enderecoPO;
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


	public EnderecoPO getViewEnderecoPO() {
		return viewEnderecoPO;
	}


	public void setViewEnderecoPO(EnderecoPO viewEnderecoPO) {
		this.viewEnderecoPO = viewEnderecoPO;
	}


	public ClienteFornecedorPO getViewClienteFornecedorPO() {
		return viewClienteFornecedorPO;
	}


	public void setViewClienteFornecedorPO(ClienteFornecedorPO viewClienteFornecedorPO) {
		this.viewClienteFornecedorPO = viewClienteFornecedorPO;
	}
	
	
	
}
