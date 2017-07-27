package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="venda_produto")
@NamedQueries(
{@NamedQuery(name="VendaProdutoPO.FIND_BY_DATA",
		     query="select v from VendaProdutoPO v where v.vendaPO.dataVenda >= :dateIni and v.vendaPO.dataVenda <= :dateFim ")
})
public class VendaProdutoPO implements Serializable {

	public static final String FIND_BY_DATA = "VendaProdutoPO.FIND_BY_DATA";
	
	private static final long serialVersionUID = -2569293721055195937L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="venda_produto_id")
	private Long vendaProdutoId;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private ProdutoPO produtoPO;
	
	
	@ManyToOne
	@JoinColumn(name="venda_id")
	private VendaPO vendaPO;
	
	@Column(name="quantidade")
	private Integer quantidade;

	@Column(name="valor_venda_uni")
	private Double valorVendaUni;
	
	@Column(name="valor_compra_uni")
	private Double valorCompraUni;

	public Long getVendaProdutoId() {
		return vendaProdutoId;
	}

	public void setVendaProdutoId(Long vendaProdutoId) {
		this.vendaProdutoId = vendaProdutoId;
	}

	public ProdutoPO getProdutoPO() {
		return produtoPO;
	}

	public void setProdutoPO(ProdutoPO produtoPO) {
		this.produtoPO = produtoPO;
	}

	public VendaPO getVendaPO() {
		return vendaPO;
	}

	public void setVendaPO(VendaPO vendaPO) {
		this.vendaPO = vendaPO;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	

	public Double getValorVendaUni() {
		return valorVendaUni;
	}

	public void setValorVendaUni(Double valorVendaUni) {
		this.valorVendaUni = valorVendaUni;
	}

	public Double getValorCompraUni() {
		return valorCompraUni;
	}

	public void setValorCompraUni(Double valorCompraUni) {
		this.valorCompraUni = valorCompraUni;
	}

	public static String getFindByData() {
		return FIND_BY_DATA;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}