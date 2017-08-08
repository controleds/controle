package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="endereco")
public class EnderecoPO implements Serializable {

	private static final long serialVersionUID = 6128310670603903854L;
	@Id
	@Column(name="endereco_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long enderecoId;
	
	@Column(name="uf",nullable=true)
	private String uf;
	
	@Column(name="cidade",nullable=true)
	private String cidade;
	
	@Column(name="bairro",nullable=true)
	private String bairro;
	
	@Column(name="cep",nullable=true)
	private String cep;
	
	@Column(name="numero",nullable=true)
	private String numero;

	@Column(name="rua",nullable=false)
	private String rua;
	
	
	public Long getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Long enderecoId) {
		this.enderecoId = enderecoId;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	
	
}
