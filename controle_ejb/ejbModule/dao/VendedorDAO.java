package dao;

import javax.ejb.Stateless;

import model.VendedorPO;



@Stateless
public class VendedorDAO extends GenericDAO<VendedorPO> {
	
	public VendedorDAO() {
		super(VendedorPO.class);
	}
	
	
	 
}