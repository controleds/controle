package util.converter;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ejb.VendedorBO;
import model.VendedorPO;

@ManagedBean(name="converterService", eager = true)
@ApplicationScoped
public class ConverterService {

	@EJB
	private VendedorBO vendedorBO;
	
	public VendedorPO getVendendor(Long vendedorID){
		return vendedorBO.find(vendedorID);
	}
	
}
