package dao;

import javax.ejb.Stateless;

import model.VendaPO;



@Stateless
public class VendaDAO extends GenericDAO<VendaPO> {
	
	public VendaDAO() {
		super(VendaPO.class);
	}
	
	
	 
}