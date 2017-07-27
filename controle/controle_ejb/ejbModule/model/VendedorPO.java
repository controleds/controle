package model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="vendedor")
public class VendedorPO implements Serializable {

	private static final long serialVersionUID = 2350652833255255445L;

	@Id
	@Column(name="vendedor_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long vendedorId;
	
	@Column(name="nome",nullable=false,unique=true)
	private String nome;
	
	@Lob
	@Column(name="foto",nullable=true)
	private byte[] foto;

	@Column(name="percentual_venda",nullable=true)
	private Double percentualVenda;

	public Long getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(Long vendedorId) {
		this.vendedorId = vendedorId;
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

	public Double getPercentualVenda() {
		return percentualVenda;
	}

	public void setPercentualVenda(Double percentualVenda) {
		this.percentualVenda = percentualVenda;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(foto);
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((percentualVenda == null) ? 0 : percentualVenda.hashCode());
		result = prime * result + ((vendedorId == null) ? 0 : vendedorId.hashCode());
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
		VendedorPO other = (VendedorPO) obj;
		if (!Arrays.equals(foto, other.foto))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (percentualVenda == null) {
			if (other.percentualVenda != null)
				return false;
		} else if (!percentualVenda.equals(other.percentualVenda))
			return false;
		if (vendedorId == null) {
			if (other.vendedorId != null)
				return false;
		} else if (!vendedorId.equals(other.vendedorId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VendedorPO [vendedorId=" + vendedorId + ", nome=" + nome + ", foto=" + Arrays.toString(foto)
				+ ", percentualVenda=" + percentualVenda + "]";
	}
	
	
	
	
}
