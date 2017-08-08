package dao;

import javax.ejb.Stateless;

import model.ProdutoComponentePO;



@Stateless
public class ProdutoComponenteDAO extends GenericDAO<ProdutoComponentePO> {
	
	public ProdutoComponenteDAO() {
		super(ProdutoComponentePO.class);
	}
	
	
	 
}