package dao;

import javax.ejb.Stateless;

import model.UsuarioPO;



@Stateless
public class UsuarioDAO extends GenericDAO<UsuarioPO> {
	
	public UsuarioDAO() {
		super(UsuarioPO.class);
	}
	
	
	 
}