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
import model.ProdutoComponenteAbstract;
import model.ProdutoComponentePO;
import model.ProdutoInternoPO;
import model.ProdutoPO;
import type.ClienteFornecedorEnum;
import util.cep.ViaCEP;
import util.cep.ViaCEPException;




@ManagedBean(name="produtoMB")
@ViewScoped
public class ProdutoMB extends AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdutoBO produtoBO;
	@EJB
	private ClienteFornecedorBO fornecedorBO;
	
	
	private ProdutoPO produtoPO = new ProdutoPO();
	
	
	private List<? super ProdutoComponenteAbstract> produtosComponentesList = new ArrayList<>();
	private List<? super ProdutoComponenteAbstract> componentesAdicionadosList = new ArrayList<>();
	
	
	private List<ComponentePO> componentePOsList = new ArrayList<ComponentePO>();
	
	private List<ClienteFornecedorPO> fornecedorPOsList  = new ArrayList<ClienteFornecedorPO>();
	private List<SelectItem> fornecedoresSelectItems = new ArrayList<>();
	private String fornecedorselectItem = "";
	private ClienteFornecedorPO clienteFornecedorPO =  new  ClienteFornecedorPO();
	private EnderecoPO enderecoPO = new EnderecoPO();
	private List<ProdutoPO> produtoPOsList = new ArrayList<>();
	private EnderecoPO viewEnderecoPO = new EnderecoPO();
	private ClienteFornecedorPO viewClienteFornecedorPO =  new  ClienteFornecedorPO();
	private ClienteFornecedorPO fornecedorPO = new ClienteFornecedorPO();
	private boolean produzido = false;
	private boolean interno = false;
	
	@PostConstruct
	public void ini(){
		produtoPOsList = produtoBO.findAll();
		
		produtoPO =  new  ProdutoPO();
		componentePOsList = produtoBO.findComponentes();
		fornecedorPO = new ClienteFornecedorPO();
		carregaFornecedores();
		fornecedorselectItem = "";
		viewEnderecoPO = new EnderecoPO();
		viewClienteFornecedorPO =  new  ClienteFornecedorPO();
		interno = false;
		
		produtosComponentesList = produtoBO.findComponentesProdutosInternos();
		componentesAdicionadosList = new ArrayList<>();
	}
	
	
	


	public void handleFileUpload(FileUploadEvent event) {
		produtoPO.setFoto(event.getFile().getContents());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	
	public void salvarProduto(){
		preSave();
		try {
			produtoBO.salvarProduto(produtoPO);
			menssagemSucesso("Produto Salvo com Sucesso!");
			ini();
		} catch (Exception e) {
			menssagemErro(e.getMessage());
		}
	}


	private void preSave() {
		produtoPO.setProduzido(this.produzido);
		produtoPO.setInterno(this.interno);
		if (fornecedorselectItem != null && !fornecedorselectItem.equals("")){
			fornecedorPO = fornecedorBO.find(new Long(fornecedorselectItem));
		} else {
			fornecedorPO = null;
		}
		produtoPO.setFornecedorPO(fornecedorPO);
		
		if (componentesAdicionadosList != null && !componentesAdicionadosList.isEmpty()) 
			produtoPO.setProdutoComponentePO(parseComp(componentesAdicionadosList));
	}
	
	private List<ProdutoComponentePO> parseComp(List<? super ProdutoComponenteAbstract> componentesAdicionadosList2) {
		List<ProdutoComponentePO> list = new ArrayList<>();
		
		for (Object o : componentesAdicionadosList2) {
			if (o instanceof ComponentePO){
				ProdutoComponentePO produtoComponentePO = new ProdutoComponentePO();
				produtoComponentePO.setProdutoPO(produtoPO);
				produtoComponentePO.setComponentePO((ComponentePO)o);
				produtoComponentePO.setQuantidade(((ComponentePO)o).getQuantidade());
				list.add(produtoComponentePO);
			
			} else if (o instanceof ProdutoPO){
				
				if (this.produtoPO.getProdutoInternoPO() == null ||
						this.produtoPO.getProdutoInternoPO().isEmpty())
					this.produtoPO.setProdutoInternoPO(new ArrayList<>()); 
					
				ProdutoInternoPO produtoInternoPO = new ProdutoInternoPO();
				produtoInternoPO.setProdutoInterno((ProdutoPO)o);
				produtoInternoPO.setQuantidade(((ProdutoPO)o).getQuantidade());
				produtoInternoPO.getProdutoInterno().setQuantidade(produtoInternoPO.getQuantidade());
				produtoInternoPO.setProdutoPO(produtoPO);
				this.produtoPO.getProdutoInternoPO().add(produtoInternoPO);
				
			}
		}
		return list;
	}


	public void calculaLucro(){
		if (produtoPO.getPrecoCompra() != null && produtoPO.getPrecoVenda() != null) 
			this.produtoPO.setLucro(produtoPO.getPrecoVenda() - produtoPO.getPrecoCompra());
	}
	
	public void selectProduto(ProdutoPO produtoPO){
		this.produtoPO = produtoPO;
		this.produzido = produtoPO.getProduzido();
		this.interno = produtoPO.getInterno();
		if(produtoPO.getProduzido()){
			this.carregaProduzido();
		} else {
			this.carregaRevenda();
		}
		
	}
	
	
	private void carregaRevenda() {
		if (this.produtoPO.getFornecedorPO() != null &&
			this.produtoPO.getFornecedorPO().getClienteFornecedorId() != null){
			this.fornecedorselectItem = this.produtoPO.getFornecedorPO().getClienteFornecedorId().toString(); 
		}
	}


	private void carregaProduzido() {
		this.componentesAdicionadosList = new ArrayList<>();
		if (this.produtoPO.getProdutoComponentePO() != null && 
			!this.produtoPO.getProdutoComponentePO().isEmpty()){
			for (ProdutoComponentePO componentePO : this.produtoPO.getProdutoComponentePO()) {
				componentePO.getComponentePO().setQuantidade(componentePO.getQuantidade());
				componentesAdicionadosList.add(componentePO.getComponentePO());
			}
		}
		
		if (this.produtoPO.getProdutoInternoPO() != null && 
				!this.produtoPO.getProdutoInternoPO().isEmpty()){
				for (ProdutoInternoPO pPO : this.produtoPO.getProdutoInternoPO()) {
					pPO.getProdutoInterno().setQuantidade(pPO.getQuantidade());
					componentesAdicionadosList.add(pPO.getProdutoInterno());
				}
			}
		
		
		carregaProduzidoPopUp();
	}


	private void carregaProduzidoPopUp() {
		for (Object o : componentesAdicionadosList) {
			if (o instanceof ComponentePO) {
				ComponentePO com1 = (ComponentePO) o;
				for (Object com2 : produtosComponentesList) {
					if (com2 instanceof ComponentePO){
						if (com1.getComponenteId().equals(((ComponentePO) com2).getComponenteId())) {
							((ComponentePO) com2).setQuantidade(com1.getQuantidade());
							((ComponentePO) com2).setFoiAdicionado(true);
						}
					}
				}
			} else if (o instanceof ProdutoPO){
				ProdutoPO com1 = (ProdutoPO) o;
				for (Object com2 : produtosComponentesList) {
					if (com2 instanceof ProdutoPO){
						if (com1.getProdutoId().equals(((ProdutoPO) com2).getProdutoId())) {
							((ProdutoPO) com2).setQuantidade(com1.getQuantidade());
							((ProdutoPO) com2).setFoiAdicionado(true);
						}
					}
				}
			}
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
				menssagemErro("Fornecedor salvo com Sucesso.");
			} catch (Exception e) {
				menssagemErro("Não foi possivel adicionar um novo Fornecedor.");
			}
			
		} else {
			menssagemErro("Não Foi possivel adicionar um novo Fornecedor. o campo Nome é Obrigatório.");
		}
	}
	
	public void adicionarComponente(ProdutoComponenteAbstract prod){
		if (prod instanceof ComponentePO){
			ComponentePO componentePO = (ComponentePO)prod;
			componentePO.setFoiAdicionado(true);
			this.componentesAdicionadosList.add(componentePO);
		} else if (prod instanceof ProdutoPO){
			ProdutoPO componentePO = (ProdutoPO)prod;
			componentePO.setFoiAdicionado(true);
			this.componentesAdicionadosList.add(componentePO);
		}
		calculaLucroProd();
	}
	
	
	public void removeComponente(ProdutoComponenteAbstract componentePO){
		this.componentesAdicionadosList.remove(componentePO);
		if (componentePO instanceof ComponentePO){
			for (Object componente : produtosComponentesList) {
				if (componente instanceof ComponentePO && 
						((ComponentePO) componente).getComponenteId().equals(((ComponentePO) componentePO).getComponenteId())){
					((ComponentePO) componente).setQuantidade(1);
					((ComponentePO) componente).setFoiAdicionado(false);
				}
			}
		} else if (componentePO instanceof ProdutoPO){
			for (Object componente : produtosComponentesList) {
				if (componente instanceof ComponentePO && 
						((ProdutoPO) componente).getProdutoId().equals(((ProdutoPO) componentePO).getProdutoId())){
					((ProdutoPO) componente).setQuantidade(1);
					((ProdutoPO) componente).setFoiAdicionado(false);
				}
			}
		}
		calculaLucroProd();
	}
	
	
	
	public void calculaLucroProd(){
		double valorcomp =  valorComponentes();
		produtoPO.setPrecoCompra(valorcomp);
		if (produtoPO.getPrecoCompra() != null && produtoPO.getPrecoVenda() != null) 
			this.produtoPO.setLucro(produtoPO.getPrecoVenda() - produtoPO.getPrecoCompra());
		
	}
	
	private double valorComponentes() {
		double valorTotal = 0;
		if (componentesAdicionadosList != null && !componentesAdicionadosList.isEmpty()){
			for (Object componente : componentesAdicionadosList) {
				if (componente instanceof ComponentePO)
				   valorTotal = valorTotal + (((ComponentePO) componente).getQuantidade() * ((ComponentePO) componente).getValorUnitarioCompra());
				else if (componente instanceof ProdutoPO)
					valorTotal = valorTotal + (((ProdutoPO) componente).getQuantidade() * ((ProdutoPO) componente).getPrecoCompra());
			}
		}
		return valorTotal;
	}


	public void gerarCodigo(){
		String codigo = produtoBO.gerarCodigo();
		this.produtoPO.setCodigoProduto(codigo);
	}
	
	
	public boolean isProduzido() {
		return produzido;
	}


	public void setProduzido(boolean produzido) {
		this.produzido = produzido;
	}


	public void limparPopUp (){
		this.clienteFornecedorPO = new ClienteFornecedorPO();
		this.enderecoPO = new EnderecoPO();
	}
	
	public ProdutoBO getProdutoBO() {
		return produtoBO;
	}



	public void setProdutoBO(ProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}




	public List<? super ProdutoComponenteAbstract> getProdutosComponentesList() {
		return produtosComponentesList;
	}


	public void setProdutosComponentesList(List<? super ProdutoComponenteAbstract> produtosComponentesList) {
		this.produtosComponentesList = produtosComponentesList;
	}
	
	public ProdutoPO getProdutoPO() {
		return produtoPO;
	}


	public void setProdutoPO(ProdutoPO produtoPO) {
		this.produtoPO = produtoPO;
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


	public List<? super ProdutoComponenteAbstract> getComponentesAdicionadosList() {
		return componentesAdicionadosList;
	}


	public void setComponentesAdicionadosList(List<? super ProdutoComponenteAbstract> componentesAdicionadosList) {
		this.componentesAdicionadosList = componentesAdicionadosList;
	}


	public List<ProdutoPO> getProdutoPOsList() {
		return produtoPOsList;
	}


	public void setProdutoPOsList(List<ProdutoPO> produtoPOsList) {
		this.produtoPOsList = produtoPOsList;
	}


	public boolean isInterno() {
		return interno;
	}


	public void setInterno(boolean interno) {
		this.interno = interno;
	}


	public List<? super ProdutoComponenteAbstract> getTeste() {
		return produtosComponentesList;
	}


	public void setTeste(List<? super ProdutoComponenteAbstract> teste) {
		this.produtosComponentesList = teste;
	}
	
	
	
}
