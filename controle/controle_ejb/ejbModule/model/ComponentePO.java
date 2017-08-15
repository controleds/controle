package model;


import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="componente")
public class ComponentePO extends ProdutoComponenteAbstract implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -42478900618329398L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="componente_id")
	private Long componenteId;
	
	@Column(name="nome",nullable=false,unique=true)
	private String nome;
	
	@Lob
	@Column(name="foto")
	private byte[] foto;
	
	@Column(name="valor_unitario_compra",nullable=false)
	private Double valorUnitarioCompra;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id",nullable=true)
	private ClienteFornecedorPO fornecedor;

	@Column(name="observacao",nullable=true)
	private String observacao;
	
	
	@Column(name="quant_estoque")
	private Integer quantidadeEstoque;
	
	@Transient
	private boolean foiAdicionado;
	
	@Transient
	private int quantidade = 1;
	
	public Long getComponenteId() {
		return componenteId;
	}

	public void setComponenteId(Long componenteId) {
		this.componenteId = componenteId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Double getValorUnitarioCompra() {
		return valorUnitarioCompra;
	}

	public void setValorUnitarioCompra(Double valorUnitarioCompra) {
		this.valorUnitarioCompra = valorUnitarioCompra;
	}

	public ClienteFornecedorPO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(ClienteFornecedorPO fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isFoiAdicionado() {
		return foiAdicionado;
	}

	public void setFoiAdicionado(boolean foiAdicionado) {
		this.foiAdicionado = foiAdicionado;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componenteId == null) ? 0 : componenteId.hashCode());
		result = prime * result + (foiAdicionado ? 1231 : 1237);
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + Arrays.hashCode(foto);
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + quantidade;
		result = prime * result + ((valorUnitarioCompra == null) ? 0 : valorUnitarioCompra.hashCode());
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
		ComponentePO other = (ComponentePO) obj;
		if (componenteId == null) {
			if (other.componenteId != null)
				return false;
		} else if (!componenteId.equals(other.componenteId))
			return false;
		if (foiAdicionado != other.foiAdicionado)
			return false;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (!Arrays.equals(foto, other.foto))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (quantidade != other.quantidade)
			return false;
		if (valorUnitarioCompra == null) {
			if (other.valorUnitarioCompra != null)
				return false;
		} else if (!valorUnitarioCompra.equals(other.valorUnitarioCompra))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComponentePO [componenteId=" + componenteId + ", nome=" + nome + ", foto=" + Arrays.toString(foto)
				+ ", valorUnitarioCompra=" + valorUnitarioCompra + ", fornecedor=" + fornecedor + ", observacao="
				+ observacao + ", quantidadeEstoque=" + quantidadeEstoque + ", foiAdicionado=" + foiAdicionado
				+ ", quantidade=" + quantidade + "]";
	}
	
	
	
}
