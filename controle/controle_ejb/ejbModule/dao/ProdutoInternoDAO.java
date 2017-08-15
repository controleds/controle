package dao;

import javax.ejb.Stateless;

import model.ProdutoInternoPO;



@Stateless
public class ProdutoInternoDAO extends GenericDAO<ProdutoInternoPO> {
	
	public ProdutoInternoDAO() {
		super(ProdutoInternoPO.class);
	}
	
	
	 
}