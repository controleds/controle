package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="produto")
@NamedQueries(
{@NamedQuery(name="ProdutoPO.FIND_BY_CODIGO",
		     query="select p from ProdutoPO p where p.codigoProduto = :codigoProduto ")
})
public class ProdutoPO implements Serializable {

	public static final String FIND_BY_CODIGO = "ProdutoPO.FIND_BY_CODIGO";
	
	private static final long serialVersionUID = -2569293721055195937L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="produto_id")
	private Long produtoId;
	
	@Column(name="nome",nullable=false, unique=true)
	private String nome;
	
	@Column(name="codigo_produto",nullable=true)
	private String codigoProduto;
	
	@Lob
	@Column(name="foto",nullable=true)
	private byte[] foto;
	
	@Column(name="preco_compra",nullable=true)
	private Double precoCompra;
	
	@Column(name="preco_venda",nullable=false)
	private Double precoVenda;
	
	@Column(name="lucro", nullable=false)
	private Double lucro;
	
	@Column(name="quant_estoque", nullable=false)
	private Integer quantidadeEstoque;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id",nullable=true)
	private ClienteFornecedorPO fornecedorPO;
	
	@Column(name="produzido",nullable=false)
	private Boolean produzido;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="produto_id")
	private List<ProdutoComponentePO> produtoComponentePO;

	
	@Transient
	private boolean foiAdicionado;
	
	@Transient
	private int quantidade = 1;
	
	
	
	
	
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
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

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Double getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(Double precoCompra) {
		this.precoCompra = precoCompra;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Double getLucro() {
		return lucro;
	}

	public void setLucro(Double lucro) {
		this.lucro = lucro;
	}

	
	public ClienteFornecedorPO getFornecedorPO() {
		return fornecedorPO;
	}

	public void setFornecedorPO(ClienteFornecedorPO fornecedorPO) {
		this.fornecedorPO = fornecedorPO;
	}

	public Boolean getProduzido() {
		return produzido;
	}

	public void setProduzido(Boolean produzido) {
		this.produzido = produzido;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ProdutoComponentePO> getProdutoComponentePO() {
		return produtoComponentePO;
	}

	public void setProdutoComponentePO(List<ProdutoComponentePO> produtoComponentePO) {
		this.produtoComponentePO = produtoComponentePO;
	}

	public static String getFindByCodigo() {
		return FIND_BY_CODIGO;
	}

	
	
	
	
	
}
