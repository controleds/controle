package ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import model.VendaPO;

@Local
public interface VendaBO {

	public abstract void save(VendaPO entity) throws Exception;
	public abstract List<VendaPO> consulta(Date dateIni, Date dateFim) throws Exception;

}
