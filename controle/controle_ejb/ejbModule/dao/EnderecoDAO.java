package dao;

import javax.ejb.Stateless;

import model.EnderecoPO;



@Stateless
public class EnderecoDAO extends GenericDAO<EnderecoPO> {
	
	public EnderecoDAO() {
		super(EnderecoPO.class);
	}
	
	
	 
}