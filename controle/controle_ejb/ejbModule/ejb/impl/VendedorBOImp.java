package ejb.impl;



import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;

import dao.VendedorDAO;
import ejb.VendedorBO;
import exceptions.ControleException;
import model.VendedorPO;

@Stateless
public class VendedorBOImp extends AbstractBO implements VendedorBO {

	
	@EJB
	private VendedorDAO vendedorDAO; 
	
	@Override
	public void save(VendedorPO vendedorPO) throws Exception { 
		try {
			if (vendedorPO.getVendedorId() == null){
				vendedorDAO.save(vendedorPO);
			} else {
				this.update(vendedorPO);
			}
		} catch (EJBTransactionRolledbackException  e) {
			super.verificaUnique(e);
			throw new ControleException();
		}
	}

	

	@Override
	public VendedorPO update(VendedorPO vendedorPO) throws Exception{
			VendedorPO po = vendedorDAO.find(vendedorPO.getVendedorId());
			this.parseVendedor(po,vendedorPO);
			return vendedorDAO.update(po);
	}

	private void parseVendedor(VendedorPO po, VendedorPO vendedorPO) {
		po.setFoto(vendedorPO.getFoto());
		po.setNome(vendedorPO.getNome());
		po.setPercentualVenda(vendedorPO.getPercentualVenda());
	}



	@Override
	public void delete(VendedorPO vendedorPO) {
		
	}

	@Override
	public VendedorPO find(Long entityID) {
		return vendedorDAO.find(entityID);
	}

	@Override
	public List<VendedorPO> findAll() {
		return vendedorDAO.findAll();
	}

	
	
}
