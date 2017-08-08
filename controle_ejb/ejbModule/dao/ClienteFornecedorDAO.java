package dao;

import javax.ejb.Stateless;

import model.ClienteFornecedorPO;



@Stateless
public class ClienteFornecedorDAO extends GenericDAO<ClienteFornecedorPO> {
	
	public ClienteFornecedorDAO() {
		super(ClienteFornecedorPO.class);
	}
	
	
	 
}