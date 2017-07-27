package dao;

import javax.ejb.Stateless;

import model.ProdutoPO;



@Stateless
public class ProdutoDAO extends GenericDAO<ProdutoPO> {
	
	public ProdutoDAO() {
		super(ProdutoPO.class);
	}
	
	
	 
}