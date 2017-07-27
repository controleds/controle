package ejb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.UsuarioDAO;
import ejb.UsuarioBO;
import model.UsuarioPO;

@Stateless
public class UsuarioBOImp implements UsuarioBO {

	@EJB
	private UsuarioDAO usuarioDAO; 
	
	@Override
	public void save(UsuarioPO UsuarioPO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UsuarioPO update(UsuarioPO UsuarioPO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UsuarioPO UsuarioPO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UsuarioPO find(Long entityID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioPO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioPO> listarPorEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioPO> listarPorLogin(String login){
		Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("login",login);     
	    return usuarioDAO.executeNamedQuery(UsuarioPO.FIND_BY_LOGIN,parameters);
	}

	@Override
	public UsuarioPO login(UsuarioPO UsuarioPO) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
