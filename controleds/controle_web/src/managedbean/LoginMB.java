package managedbean;


import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ejb.UsuarioBO;
import model.UsuarioPO;



@ManagedBean(name="loginMB")
@SessionScoped
public class LoginMB extends AbstractMB  {

	
	private static final long serialVersionUID = -499054377859497459L;
	
		
	@EJB
	private UsuarioBO usuarioBO;
	
	private UsuarioPO usuarioPO = new UsuarioPO();
	private String login = new String();
	private String senha = new String();
	
	
	public String login(){
		List<UsuarioPO>  list = usuarioBO.listarPorLogin(this.login);
		if (list != null && !list.isEmpty()){
			UsuarioPO usuario = list.get(0);
			if (usuario.getSenha().equals(this.senha)){
				this.usuarioPO = usuario;
				return "/pages/venda.xhtml";
			} else {
				menssagemErro("Senha Inválida !");
			}
			
		} else {
			menssagemErro("Login Inválido !");
		}
		
		return null;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public UsuarioBO getUsuarioBO() {
		return usuarioBO;
	}


	public void setUsuarioBO(UsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
	}


	public UsuarioPO getUsuarioPO() {
		return usuarioPO;
	}


	public void setUsuarioPO(UsuarioPO usuarioPO) {
		this.usuarioPO = usuarioPO;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
