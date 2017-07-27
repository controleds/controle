package managedbean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import ejb.ClienteFornecedorBO;
import ejb.ProdutoBO;
import ejb.VendaBO;
import ejb.VendedorBO;
import model.ClienteFornecedorPO;
import model.EnderecoPO;
import model.ProdutoPO;
import model.VendaPO;
import model.VendaProdutoPO;
import model.VendedorPO;
import type.ClienteFornecedorEnum;
import util.cep.ViaCEP;
import util.cep.ViaCEPException;




@ManagedBean(name="vendaMB")
@ViewScoped
public class VendaMB extends AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdutoBO produtoBO;
	@EJB
	private ClienteFornecedorBO clienteBO;
	@EJB
	private VendaBO vendaBO;
	@EJB
	private VendedorBO vendedorBO;
	
	
	private VendaPO vendaPO= new VendaPO();
	
	private String clienteIdSelect = "";
	private String vendedorIdSelect = "";
	private List<SelectItem> clientesSelectItems = new ArrayList<>();
	private List<SelectItem> vendedoresSelectItems = new ArrayList<>();
	private ClienteFornecedorPO clienteInsert = new ClienteFornecedorPO();
	private EnderecoPO enderecoPOInsert = new EnderecoPO();
	
	
	private List<VendaProdutoPO> produtosAdicionados = new ArrayList<>();
	private List<ProdutoPO> produtosList = new ArrayList<>();
	private List<VendedorPO> vendedorPOList = new ArrayList<>();
	
	private double valorCliente = 0;
	private double valorTroco = 0;
	private boolean habilitaVenda = false;
	private boolean troco = false;
	private double percentDecs = 0;
	private double valorDesc = 0;
	private double valorTotalVenda =0;
	private boolean habilitaDesc = false;
	
	@ManagedProperty("#{loginMB}")
    private LoginMB sessionBean;
	
	@PostConstruct
	public void ini(){
		vendaPO= new VendaPO();
		clienteIdSelect = "";
		vendedorIdSelect = "";
		clientesSelectItems = new ArrayList<>();
		vendedoresSelectItems = new ArrayList<>();
		enderecoPOInsert = new EnderecoPO();
		clienteInsert = new ClienteFornecedorPO();
		produtosAdicionados = new ArrayList<>();
		produtosList = produtoBO.findAll();
		valorCliente = 0;
		valorTroco = 0;
		percentDecs = 0;
		valorDesc = 0;
		valorTotalVenda =0;
		troco = false;
		habilitaVenda = false;
		habilitaDesc = false;
		this.carregaClientes();
		this.carregaVendedores();
	}

	
	private void carregaClientes(){
		clientesSelectItems = new ArrayList<>();
		List<ClienteFornecedorPO> fornecedorPOsList  = clienteBO.findClientes();
		if (fornecedorPOsList != null && !fornecedorPOsList.isEmpty()){
			for (ClienteFornecedorPO fornecedor : fornecedorPOsList) {
				SelectItem item = new SelectItem();
				item.setLabel(fornecedor.getNome());
				item.setValue(fornecedor.getClienteFornecedorId().toString());
				this.clientesSelectItems.add(item);
			}
		}
	}
	
	private void carregaVendedores(){
		vendedoresSelectItems = new ArrayList<>();
		List<VendedorPO> vPOsList  = vendedorBO.findAll();
		if (vPOsList != null && !vPOsList.isEmpty()){
			for (VendedorPO vend : vPOsList ) {
				SelectItem item = new SelectItem();
				item.setLabel(vend.getNome());
				item.setValue(vend.getVendedorId().toString());
				this.vendedoresSelectItems.add(item);
			}
		}
	}
	
	public void buscarCep(){
		ViaCEP cep = new ViaCEP();
		try {
			Long baseId = null;
			if (enderecoPOInsert.getEnderecoId() != null) baseId = enderecoPOInsert.getEnderecoId();  
			enderecoPOInsert = cep.buscar(enderecoPOInsert.getCep());
			if (baseId != null) enderecoPOInsert.setEnderecoId(baseId);
		} catch (ViaCEPException e) {
			e.printStackTrace();
		}
	}
	
	
	public void limparPopUp (){
		this.clienteInsert = new ClienteFornecedorPO();
		this.enderecoPOInsert = new EnderecoPO();
	}
	
	
	
	public void salvarCliente(){
		if (clienteInsert != null && clienteInsert.getNome() !=null && !clienteInsert.getNome().isEmpty()){
			try {
				clienteInsert.setTipo(ClienteFornecedorEnum.CLIENTE.getValue());
				clienteInsert.setEnderecoPO(enderecoPOInsert);
				clienteBO.save(clienteInsert);
				this.carregaClientes();
				menssagemSucesso("Cliente salvo com Sucesso.");
				
			} catch (Exception e) {
				menssagemErro("Não foi possivel adicionar um novo Cliente.");
			}
			
			limparPopUp();
		} else {
			menssagemErro("Não Foi possivel adicionar um novo Cliente. o campo Nome é Obrigatório.");
		}
	}
	

	
	
	public List<SelectItem> getVendedoresSelectItems() {
		return vendedoresSelectItems;
	}


	public void setVendedoresSelectItems(List<SelectItem> vendedoresSelectItems) {
		this.vendedoresSelectItems = vendedoresSelectItems;
	}


	public void adicionarProduto(ProdutoPO produtoPO){
		produtoPO.setFoiAdicionado(true);
		VendaProdutoPO vendaProdutoPO = new VendaProdutoPO();
		vendaProdutoPO.setProdutoPO(produtoPO);
		vendaProdutoPO.setQuantidade(produtoPO.getQuantidade());
		vendaProdutoPO.setValorCompraUni(produtoPO.getPrecoCompra());
		vendaProdutoPO.setValorVendaUni(produtoPO.getPrecoVenda());
		this.produtosAdicionados.add(vendaProdutoPO);
		this.calculaTotalVenda();
		this.habilitaVenda();
		this.vefiricaEstoque(produtoPO);
	}
	
	private void vefiricaEstoque(ProdutoPO produtoPO) {
		if (produtoPO !=null && produtoPO.getQuantidadeEstoque() != null){
			if (produtoPO.getQuantidadeEstoque() - produtoPO.getQuantidade() < 1){
				menssagemAlerta("ALERTA !!! VERIFICAR O ESTOQUE DO PORDUTO "+ produtoPO.getNome());
			}
		}
		
	}


	private void habilitaVenda() {
		if (produtosAdicionados != null && !produtosAdicionados.isEmpty()) {
			habilitaVenda = true;
		} else {
			habilitaVenda = false;
		}
	}
	
	public void removeProduto(VendaProdutoPO vendaProdutoPO){
		this.produtosAdicionados.remove(vendaProdutoPO);
		for (ProdutoPO prod : produtosList) {
			if (prod.getProdutoId().equals(vendaProdutoPO.getProdutoPO().getProdutoId())){
				prod.setQuantidade(1);
				prod.setFoiAdicionado(false);
			}
		}
		this.calculaTotalVenda();
		this.habilitaVenda();
	}
	
	
	public double calculaTotalVenda() {
		double valorTot = 0;
		if (produtosAdicionados != null && !produtosAdicionados.isEmpty()){
			for (VendaProdutoPO vendaProdutoPO : produtosAdicionados) {
				valorTot = valorTot + 
						(vendaProdutoPO.getQuantidade()* vendaProdutoPO.getProdutoPO().getPrecoVenda());
			}
		}
		return valorTot ;
	}

	
	
	public String totalString(){
		if (produtosAdicionados == null || produtosAdicionados.isEmpty()) return "Fechar Venda. Valor Total: "; 
		String msg = "Fechar Venda. Valor Total: ";
		double result = 0;
		result = calculaTotalVenda();
		result = result - valorDesc;
		DecimalFormat formato = new DecimalFormat("#,##0.00");
		msg = msg + formato.format(result);
		return msg;
	}

	
	public void calculaTroco(){
		double result = this.calculaTotalVenda();
		result = result - valorDesc;
		valorTroco = valorCliente - result;
	}
	
	
	public void cancelaVenda(){
		valorCliente = 0;
		valorTroco = 0;
		percentDecs = 0;
		valorDesc = 0;
		valorTotalVenda =0;
		troco = false;
		habilitaDesc = false;
	}

	public void fecharVenda(){
		try {
			preSave();
			vendaBO.save(vendaPO);
			menssagemSucesso("Venda Cadastrada com Sucesso.");
			ini();
		} catch (Exception e) {
			menssagemErro("Não foi possível efetuar a venda. Consute o LOG");
		}
		
	}

	
	private void preSave() {
		vendaPO.setValorTotal(this.calculaTotalVenda());
		vendaPO.setValorDesc(this.valorDesc);
		vendaPO.setPercDesc(this.percentDecs);
		vendaPO.setValorFinalVenda(vendaPO.getValorTotal() - valorDesc);
		vendaPO.setProdutos(this.produtosAdicionados);
		vendaPO.setUsuarioPO(sessionBean.getUsuarioPO());
		if (clienteIdSelect != null && !clienteIdSelect.isEmpty())
			vendaPO.setClientePO(this.clienteBO.find(new Long(clienteIdSelect)));
		if (vendedorIdSelect != null && !vendedorIdSelect.isEmpty())
			vendaPO.setVendedorPO(this.vendedorBO.find(new Long(vendedorIdSelect)));
	}
	

	
	public void calculaDesconto(){
		double total = this.calculaTotalVenda();
		double vl = total * percentDecs/100;
		this.valorDesc = vl;
		this.valorTotalVenda =  total - vl;
	}
	
	
	
	
	public boolean isHabilitaDesc() {
		return habilitaDesc;
	}


	public void setHabilitaDesc(boolean habilitaDesc) {
		this.habilitaDesc = habilitaDesc;
	}


	public double getValorTotalVenda() {
		return valorTotalVenda;
	}


	public void setValorTotalVenda(double valorTotalVenda) {
		this.valorTotalVenda = valorTotalVenda;
	}


	public double getPercentDecs() {
		return percentDecs;
	}


	public void setPercentDecs(double percentDecs) {
		this.percentDecs = percentDecs;
	}


	public double getValorDesc() {
		return valorDesc;
	}


	public void setValorDesc(double valorDesc) {
		this.valorDesc = valorDesc;
	}


	public VendedorBO getVendedorBO() {
		return vendedorBO;
	}


	public void setVendedorBO(VendedorBO vendedorBO) {
		this.vendedorBO = vendedorBO;
	}


	public String getVendedorIdSelect() {
		return vendedorIdSelect;
	}


	public void setVendedorIdSelect(String vendedorIdSelect) {
		this.vendedorIdSelect = vendedorIdSelect;
	}


	public List<VendedorPO> getVendedorPOList() {
		return vendedorPOList;
	}


	public void setVendedorPOList(List<VendedorPO> vendedorPOList) {
		this.vendedorPOList = vendedorPOList;
	}


	public ClienteFornecedorBO getClienteBO() {
		return clienteBO;
	}


	public void setClienteBO(ClienteFornecedorBO clienteBO) {
		this.clienteBO = clienteBO;
	}


	public LoginMB getSessionBean() {
		return sessionBean;
	}


	public void setSessionBean(LoginMB sessionBean) {
		this.sessionBean = sessionBean;
	}


	public boolean isTroco() {
		return troco;
	}


	public void setTroco(boolean troco) {
		this.troco = troco;
	}

	
	public double getValorCliente() {
		return valorCliente;
	}


	public void setValorCliente(double valorCliente) {
		this.valorCliente = valorCliente;
	}


	public double getValorTroco() {
		return valorTroco;
	}


	public void setValorTroco(double valorTroco) {
		this.valorTroco = valorTroco;
	}


	public List<ProdutoPO> getProdutosList() {
		return produtosList;
	}


	public void setProdutosList(List<ProdutoPO> produtosList) {
		this.produtosList = produtosList;
	}


	public List<VendaProdutoPO> getProdutosAdicionados() {
		return produtosAdicionados;
	}


	public void setProdutosAdicionados(List<VendaProdutoPO> produtosAdicionados) {
		this.produtosAdicionados = produtosAdicionados;
	}


	public ProdutoBO getProdutoBO() {
		return produtoBO;
	}

	public void setProdutoBO(ProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}

	public ClienteFornecedorBO getFornecedorBO() {
		return clienteBO;
	}

	public void setFornecedorBO(ClienteFornecedorBO fornecedorBO) {
		this.clienteBO = fornecedorBO;
	}

	public VendaBO getVendaBO() {
		return vendaBO;
	}

	public void setVendaBO(VendaBO vendaBO) {
		this.vendaBO = vendaBO;
	}

	public VendaPO getVendaPO() {
		return vendaPO;
	}

	public void setVendaPO(VendaPO vendaPO) {
		this.vendaPO = vendaPO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClienteIdSelect() {
		return clienteIdSelect;
	}

	public void setClienteIdSelect(String clienteIdSelect) {
		this.clienteIdSelect = clienteIdSelect;
	}

	public List<SelectItem> getClientesSelectItems() {
		return clientesSelectItems;
	}

	public void setClientesSelectItems(List<SelectItem> clientesSelectItems) {
		this.clientesSelectItems = clientesSelectItems;
	}


	public ClienteFornecedorPO getClienteInsert() {
		return clienteInsert;
	}


	public void setClienteInsert(ClienteFornecedorPO clienteInsert) {
		this.clienteInsert = clienteInsert;
	}


	public EnderecoPO getEnderecoPOInsert() {
		return enderecoPOInsert;
	}


	public void setEnderecoPOInsert(EnderecoPO enderecoPOInsert) {
		this.enderecoPOInsert = enderecoPOInsert;
	}


	public boolean isHabilitaVenda() {
		return habilitaVenda;
	}


	public void setHabilitaVenda(boolean habilitaVenda) {
		this.habilitaVenda = habilitaVenda;
	}
	
	
	
}
