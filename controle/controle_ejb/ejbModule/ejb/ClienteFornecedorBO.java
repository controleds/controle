package ejb;

import java.util.List;

import javax.ejb.Local;

import model.ClienteFornecedorPO;

@Local
public interface ClienteFornecedorBO {

	public abstract void save(ClienteFornecedorPO entity) throws Exception;

	public abstract ClienteFornecedorPO update(ClienteFornecedorPO entity);
	
	public abstract void delete(ClienteFornecedorPO entity);

	public abstract ClienteFornecedorPO find(Long entityID);
	
	public abstract List<ClienteFornecedorPO> findClientes();
	
	public abstract List<ClienteFornecedorPO> findFornecedores();
	
}
