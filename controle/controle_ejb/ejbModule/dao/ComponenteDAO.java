package dao;

import javax.ejb.Stateless;

import model.ComponentePO;



@Stateless
public class ComponenteDAO extends GenericDAO<ComponentePO> {
	
	public ComponenteDAO() {
		super(ComponentePO.class);
	}
	
	
	 
}