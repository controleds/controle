package dao;

import javax.ejb.Stateless;

import model.VendaProdutoPO;



@Stateless
public class VendaProdutoDAO extends GenericDAO<VendaProdutoPO> {
	
	public VendaProdutoDAO() {
		super(VendaProdutoPO.class);
	}
	
	
	 
}