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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orcamento")
public class OrcamentoPO implements Serializable {

	
	private static final long serialVersionUID = 3452805489380534239L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="orcamento_id")
	private Long orcamentoId;
	
	@Column(name="valor_total",nullable=false)
	private Double valorTotal;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id",nullable=true)
	private ClienteFornecedorPO clientePO;
	
	@ManyToOne
	@JoinColumn(name="usuario_id",nullable=true)
	private UsuarioPO usuarioPO;
	
	@Column(name="data_orcamento",nullable=false)
	private Date dataOrcamento;
	
	@Column(name="validade",nullable=false)
	private Integer validade;
	
	@Column(name="status",nullable=false)
	private String status;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="orcamento_id")
	private List<OrcamentoProdutoPO> produtos;


	public Long getOrcamentoId() {
		return orcamentoId;
	}


	public void setOrcamentoId(Long orcamentoId) {
		this.orcamentoId = orcamentoId;
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


	public Date getDataOrcamento() {
		return dataOrcamento;
	}


	public void setDataOrcamento(Date dataOrcamento) {
		this.dataOrcamento = dataOrcamento;
	}


	public Integer getValidade() {
		return validade;
	}


	public void setValidade(Integer validade) {
		this.validade = validade;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<OrcamentoProdutoPO> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<OrcamentoProdutoPO> produtos) {
		this.produtos = produtos;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
