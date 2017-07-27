package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.ClienteFornecedorBO;
import ejb.ProdutoBO;
import ejb.VendaBO;
import ejb.VendedorBO;
import model.VendaPO;
import model.VendaProdutoPO;
import model.VendedorPO;




@ManagedBean(name="consultaVendaMB")
@ViewScoped
public class ConsultaVendaMB extends AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdutoBO produtoBO;
	@EJB
	private ClienteFornecedorBO clienteBO;
	@EJB
	private VendaBO vendaBO;
	@EJB
	private VendedorBO vendedorBO;
	
	private List<VendaPO>  vendasList = new ArrayList<>();
	private Date dataIni = null;
	private Date dataFim = null;
	
	private double totalVlSemDesconto =0;
	private double totalDesconto =0;
	private double totalVenda=0;
	
	private double totalGastoProd=0;
	private double totalComissao=0;
	private double totalLucro=0;
	
	private List<VendedorPO> vendedoresSelectList = new ArrayList<>();
	private List<VendedorPO> vendedoresList = new ArrayList<>();
	
	private boolean vendedorSN = false;
	
	@PostConstruct
	public void ini(){
		vendasList = new ArrayList<>();
		dataIni = null;
		dataFim = null;
		totalVlSemDesconto =0;
		totalDesconto =0;
		totalVenda=0;
		totalGastoProd=0;
		totalComissao=0;
		totalLucro=0;
		vendedoresSelectList = new ArrayList<>();
		vendedoresList = vendedorBO.findAll();
		vendedorSN = false;
	}


	public void consutarVendas(){
		try {
			vendasList = vendaBO.consulta(dataIni, dataFim);
			if (vendasList == null || vendasList.isEmpty()){
				menssagemErro("Nenhuma Venda encontrada neste Período");
			}else {
				calculaTotais();
			}
		} catch (Exception e) {
			menssagemErro("Nenhuma Venda encontrada neste Período");
		}
	}
	
	
	
	private void calculaTotais() {
		if(vendasList != null && !vendasList.isEmpty()){
			for (VendaPO vendaPO : vendasList) {
				totalVlSemDesconto =  totalVlSemDesconto + (vendaPO.getValorTotal() != null ? vendaPO.getValorTotal() : 0);
				totalDesconto =  totalDesconto + (vendaPO.getValorDesc() != null ? vendaPO.getValorDesc() : 0);
				totalVenda =  totalVenda + (vendaPO.getValorFinalVenda() != null ? vendaPO.getValorFinalVenda() : 0);
				totalGastoProd = totalGastoProd + this.gastosProd(vendaPO);
				totalComissao = totalComissao + this.gastosComissao(vendaPO);
				totalLucro = totalVenda - (totalGastoProd+totalComissao);
			}
		}
		
	}


	public double gastosProd(VendaPO vendaPO){
		double vr = 0;
		if (vendaPO != null && vendaPO.getProdutos() != null){
			for (VendaProdutoPO produtoPO : vendaPO.getProdutos()) {
				if (produtoPO.getQuantidade() != null && produtoPO.getValorCompraUni() !=null)
				vr = vr + (produtoPO.getQuantidade() * produtoPO.getValorCompraUni());
			}
		}
		return vr;
	}
	
	
	public double gastosComissao(VendaPO vendaPO){
		double vr = 0;
		if (vendaPO != null && vendaPO.getVendedorPO() != null && vendaPO.getVendedorPO().getPercentualVenda() != null){
			vr = vendaPO.getValorFinalVenda() * vendaPO.getVendedorPO().getPercentualVenda() / 100;  
		}
		return vr;
	}
	
	public double lucro(VendaPO vendaPO){
		double vr = 0;
		if (vendaPO != null && vendaPO.getValorFinalVenda() !=null){
			vr =   vendaPO.getValorFinalVenda() - (gastosComissao(vendaPO) + gastosProd(vendaPO));
		}
		return vr;
	}
	
	
	public boolean isVendedorSN() {
		return vendedorSN;
	}


	public void setVendedorSN(boolean vendedorSN) {
		this.vendedorSN = vendedorSN;
	}


	public VendedorBO getVendedorBO() {
		return vendedorBO;
	}


	public void setVendedorBO(VendedorBO vendedorBO) {
		this.vendedorBO = vendedorBO;
	}


	public List<VendedorPO> getVendedoresSelectList() {
		return vendedoresSelectList;
	}


	public void setVendedoresSelectList(List<VendedorPO> vendedoresSelectList) {
		this.vendedoresSelectList = vendedoresSelectList;
	}


	public List<VendedorPO> getVendedoresList() {
		return vendedoresList;
	}


	public void setVendedoresList(List<VendedorPO> vendedoresList) {
		this.vendedoresList = vendedoresList;
	}


	public ProdutoBO getProdutoBO() {
		return produtoBO;
	}


	public void setProdutoBO(ProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}


	public ClienteFornecedorBO getClienteBO() {
		return clienteBO;
	}


	public void setClienteBO(ClienteFornecedorBO clienteBO) {
		this.clienteBO = clienteBO;
	}


	public VendaBO getVendaBO() {
		return vendaBO;
	}


	public void setVendaBO(VendaBO vendaBO) {
		this.vendaBO = vendaBO;
	}



	public List<VendaPO> getVendasList() {
		return vendasList;
	}


	public void setVendasList(List<VendaPO> vendasList) {
		this.vendasList = vendasList;
	}


	public Date getDataIni() {
		return dataIni;
	}


	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}


	public Date getDataFim() {
		return dataFim;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public double getTotalVlSemDesconto() {
		return totalVlSemDesconto;
	}


	public void setTotalVlSemDesconto(double totalVlSemDesconto) {
		this.totalVlSemDesconto = totalVlSemDesconto;
	}


	public double getTotalDesconto() {
		return totalDesconto;
	}


	public void setTotalDesconto(double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}


	public double getTotalVenda() {
		return totalVenda;
	}


	public void setTotalVenda(double totalVenda) {
		this.totalVenda = totalVenda;
	}


	public double getTotalGastoProd() {
		return totalGastoProd;
	}


	public void setTotalGastoProd(double totalGastoProd) {
		this.totalGastoProd = totalGastoProd;
	}


	public double getTotalComissao() {
		return totalComissao;
	}


	public void setTotalComissao(double totalComissao) {
		this.totalComissao = totalComissao;
	}


	public double getTotalLucro() {
		return totalLucro;
	}


	public void setTotalLucro(double totalLucro) {
		this.totalLucro = totalLucro;
	}

	
	
}
