package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="venda")
@NamedQueries(
{@NamedQuery(name="VendaPO.FIND_BY_DATA",
		     query="select v from VendaPO v where v.dataVenda >= :dateIni and v.dataVenda <= :dateFim "),
@NamedQuery(name="VendaPO.FIND_BY_DATA_VENDEDOR",
    		query="select v from VendaPO v where v.dataVenda >= :dateIni and v.dataVenda <= :dateFim and vendedorPO.vendedorId IN (:inclList) ")	
})
public class VendaPO implements Serializable {

	public static final String FIND_BY_DATA = "VendaPO.FIND_BY_DATA";
	public static final String FIND_BY_DATA_VENDEDOR = "VendaPO.FIND_BY_DATA_VENDEDOR";
	
	private static final long serialVersionUID = 3452805489380534239L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="venda_id")
	private Long vendaId;
	
	@Column(name="valor_total",nullable=false)
	private Double valorTotal;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id",nullable=true)
	private ClienteFornecedorPO clientePO;
	
	@ManyToOne
	@JoinColumn(name="usuario_id",nullable=true)
	private UsuarioPO usuarioPO;
	
	@Column(name="data_venda",nullable=false)
	private Date dataVenda;

	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="venda_id")
	private List<VendaProdutoPO> produtos;

	@Column(name="forma_pagamento",nullable=true)
	private String formaPagamento;
	
	
	@ManyToOne
	@JoinColumn(name="vendedor_id",nullable=false)
	private VendedorPO vendedorPO;

	
	@Column(name="valor_final_venda",nullable=false)
	private Double valorFinalVenda;
	
	@Column(name="valor_desc",nullable=false)
	private Double valorDesc;
	
	
	@Column(name="perc_desc",nullable=false)
	private Double percDesc;

	@Column(name="perc_vendedor",nullable=false)
	private Double percVendedor;

	
	
	
	public Double getPercVendedor() {
		return percVendedor;
	}


	public void setPercVendedor(Double percVendedor) {
		this.percVendedor = percVendedor;
	}


	public Double getValorFinalVenda() {
		return valorFinalVenda;
	}


	public void setValorFinalVenda(Double valorFinalVenda) {
		this.valorFinalVenda = valorFinalVenda;
	}


	public Double getValorDesc() {
		return valorDesc;
	}


	public void setValorDesc(Double valorDesc) {
		this.valorDesc = valorDesc;
	}


	public Double getPercDesc() {
		return percDesc;
	}


	public void setPercDesc(Double percDesc) {
		this.percDesc = percDesc;
	}


	public static String getFindByData() {
		return FIND_BY_DATA;
	}


	public VendedorPO getVendedorPO() {
		return vendedorPO;
	}


	public void setVendedorPO(VendedorPO vendedorPO) {
		this.vendedorPO = vendedorPO;
	}


	public Long getVendaId() {
		return vendaId;
	}


	public void setVendaId(Long vendaId) {
		this.vendaId = vendaId;
	}


	public Double getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}


	public ClienteFornecedorPO getClientePO() {
		return clientePO;
	}


	public void setClientePO(ClienteFornecedorPO clientePO) {
		this.clientePO = clientePO;
	}


	public UsuarioPO getUsuarioPO() {
		return usuarioPO;
	}


	public void setUsuarioPO(UsuarioPO usuarioPO) {
		this.usuarioPO = usuarioPO;
	}


	public Date getDataVenda() {
		return dataVenda;
	}


	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}


	public List<VendaProdutoPO> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<VendaProdutoPO> produtos) {
		this.produtos = produtos;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getFormaPagamento() {
		return formaPagamento;
	}


	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	
	
}
