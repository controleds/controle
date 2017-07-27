package ejb;

import java.util.List;

import javax.ejb.Local;

import model.UsuarioPO;

@Local
public interface UsuarioBO {

	public abstract void save(UsuarioPO UsuarioPO);

	public abstract UsuarioPO update(UsuarioPO UsuarioPO);
	
	public abstract void delete(UsuarioPO UsuarioPO);

	public abstract UsuarioPO find(Long entityID);
	
	public abstract List<UsuarioPO> findAll();
	
	public abstract List<UsuarioPO> listarPorEmail(String email);
	
	public abstract List<UsuarioPO> listarPorLogin(String login);
	
	public abstract UsuarioPO login(UsuarioPO UsuarioPO);

	
}
