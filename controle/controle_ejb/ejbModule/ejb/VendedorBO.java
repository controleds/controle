package ejb;

import java.util.List;

import javax.ejb.Local;

import model.VendedorPO;

@Local
public interface VendedorBO {

	public abstract void save(VendedorPO vendedorPO) throws Exception;

	public abstract VendedorPO update(VendedorPO vendedorPO) throws Exception;
	
	public abstract void delete(VendedorPO vendedorPO);

	public abstract VendedorPO find(Long entityID);
	
	public abstract List<VendedorPO> findAll();
	
	
	
}
