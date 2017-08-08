package ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import model.VendaPO;
import model.VendedorPO;

@Local
public interface VendaBO {

	public abstract void save(VendaPO entity) throws Exception;
	public abstract List<VendaPO> consulta(Date dateIni, Date dateFim,List<VendedorPO> vendedorPOs) throws Exception;

}
