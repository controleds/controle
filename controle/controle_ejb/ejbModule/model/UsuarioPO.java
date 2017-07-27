package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
@NamedQuery(name="User.findByLogin", query="SELECT u from UsuarioPO u where u.login = :login")
public class UsuarioPO implements Serializable {

	public static final String FIND_BY_LOGIN = "User.findByLogin";	
	private static final long serialVersionUID = 2350652833255255445L;

	@Id
	@Column(name="usuario_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long usuarioId;
	
	@Column(name="nome",nullable=false)
	private String nome;
	
	@Column(name="login",nullable=false)
	private String login;
	
	@Column(name="senha",nullable=false)
	private String senha;
	
	@Lob
	@Column(name="foto",nullable=true)
	private byte[] foto;

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
	
}
