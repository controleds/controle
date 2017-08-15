package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="orcamento_produto")
public class OrcamentoProdutoPO implements Serializable {

	
	private static final long serialVersionUID = -2569293721055195937L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="orcamento_produto_id")
	private Long orcamentoProdutoId;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private ProdutoPO produtoPO;
	
	
	@ManyToOne
	@JoinColumn(name="orcamento_id")
	private OrcamentoPO orcamentoPO;
	
	@Column(name="quantidade")
	private Integer quantidade;

	@Column(name="valor_venda_uni")
	private Double valorVendaUni;
	
	@Column(name="valor_compra_uni")
	private Double valorCompraUni;

	public Long getOrcamentoProdutoId() {
		return orcamentoProdutoId;
	}

	public void setOrcamentoProdutoId(Long orcamentoProdutoId) {
		this.orcamentoProdutoId = orcamentoProdutoId;
	}

	public ProdutoPO getProdutoPO() {
		return produtoPO;
	}

	public void setProdutoPO(ProdutoPO produtoPO) {
		this.produtoPO = produtoPO;
	}

	public OrcamentoPO getOrcamentoPO() {
		return orcamentoPO;
	}

	public void setOrcamentoPO(OrcamentoPO orcamentoPO) {
		this.orcamentoPO = orcamentoPO;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

		
	
	
}