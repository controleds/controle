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
@Table(name="produto_componente")

public class ProdutoComponentePO implements Serializable {

	private static final long serialVersionUID = -2569293721055195937L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="produto_componente_id")
	private Long produtoComponenteId;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private ProdutoPO produtoPO;
	
	
	@ManyToOne
	@JoinColumn(name="componente_id")
	private ComponentePO componentePO;
	
	@Column(name="quantidade")
	private Integer quantidade;

	public Long getProdutoComponenteId() {
		return produtoComponenteId;
	}

	public void setProdutoComponenteId(Long produtoComponenteId) {
		this.produtoComponenteId = produtoComponenteId;
	}

	public ProdutoPO getProdutoPO() {
		return produtoPO;
	}

	public void setProdutoPO(ProdutoPO produtoPO) {
		this.produtoPO = produtoPO;
	}

	public ComponentePO getComponentePO() {
		return componentePO;
	}

	public void setComponentePO(ComponentePO componentePO) {
		this.componentePO = componentePO;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	
	
	
}