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
@Table(name="produto_interno")

public class ProdutoInternoPO implements Serializable {

	private static final long serialVersionUID = -2569293721055195937L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="produto_interno_id")
	private Long produtoInternoId;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private ProdutoPO produtoPO;
	
	@ManyToOne
	@JoinColumn(name="produto_ineterno")
	private ProdutoPO produtoInterno;
	
	
	@Column(name="quantidade")
	private Integer quantidade;


	public Long getProdutoInternoId() {
		return produtoInternoId;
	}


	public void setProdutoInternoId(Long produtoInternoId) {
		this.produtoInternoId = produtoInternoId;
	}


	public ProdutoPO getProdutoPO() {
		return produtoPO;
	}


	public void setProdutoPO(ProdutoPO produtoPO) {
		this.produtoPO = produtoPO;
	}


	public ProdutoPO getProdutoInterno() {
		return produtoInterno;
	}


	public void setProdutoInterno(ProdutoPO produtoInterno) {
		this.produtoInterno = produtoInterno;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produtoInterno == null) ? 0 : produtoInterno.hashCode());
		result = prime * result + ((produtoInternoId == null) ? 0 : produtoInternoId.hashCode());
		result = prime * result + ((produtoPO == null) ? 0 : produtoPO.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoInternoPO other = (ProdutoInternoPO) obj;
		if (produtoInterno == null) {
			if (other.produtoInterno != null)
				return false;
		} else if (!produtoInterno.equals(other.produtoInterno))
			return false;
		if (produtoInternoId == null) {
			if (other.produtoInternoId != null)
				return false;
		} else if (!produtoInternoId.equals(other.produtoInternoId))
			return false;
		if (produtoPO == null) {
			if (other.produtoPO != null)
				return false;
		} else if (!produtoPO.equals(other.produtoPO))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}

	
	

	
	
	
}