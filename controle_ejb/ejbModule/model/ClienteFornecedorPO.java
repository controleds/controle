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
@Table(name="cliente_fornecedor")
@NamedQueries(
{@NamedQuery(name="ClienteFornecedorPO.FIND_BY_TIPO",
		     query="select c from ClienteFornecedorPO c where c.tipo = :tipo ")
})

public class ClienteFornecedorPO implements Serializable{
	
	public static final String FIND_BY_TIPO = "ClienteFornecedorPO.FIND_BY_TIPO";
	
	private static final long serialVersionUID = -2491924949044134306L;

	@Id
	@Column(name="cliente_fornecedor_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long clienteFornecedorId;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="site")
	private String site;
	
	@Column(name="telefone")
	private String telefone;
	
	@Column(name="celular")
	private String celular;
	
	@Column(name="email")
	private String email;
	
	@Column(name="observacao")
	private String observacao;
	
	@Column(name="tipo")
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name="endereco_id",nullable=true)
	private EnderecoPO enderecoPO;

	public Long getClienteFornecedorId() {
		return clienteFornecedorId;
	}

	public void setClienteFornecedorId(Long clienteFornecedorId) {
		this.clienteFornecedorId = clienteFornecedorId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public EnderecoPO getEnderecoPO() {
		return enderecoPO;
	}

	public void setEnderecoPO(EnderecoPO enderecoPO) {
		this.enderecoPO = enderecoPO;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((clienteFornecedorId == null) ? 0 : clienteFornecedorId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((enderecoPO == null) ? 0 : enderecoPO.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		ClienteFornecedorPO other = (ClienteFornecedorPO) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (clienteFornecedorId == null) {
			if (other.clienteFornecedorId != null)
				return false;
		} else if (!clienteFornecedorId.equals(other.clienteFornecedorId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enderecoPO == null) {
			if (other.enderecoPO != null)
				return false;
		} else if (!enderecoPO.equals(other.enderecoPO))
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
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
	
	
	
}
