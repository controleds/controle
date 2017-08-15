package ejb;

import java.util.List;

import javax.ejb.Local;

import model.ComponentePO;
import model.ProdutoComponenteAbstract;
import model.ProdutoPO;

@Local
public interface ProdutoBO {

	public abstract void salvarProduto(ProdutoPO entity) throws Exception;
	
	public abstract ProdutoPO find(Long entityID);
	
	public abstract ComponentePO findComponente(Long entityID);
	
	public abstract List<ProdutoPO> findAll();

	public abstract List<ComponentePO> findComponentes();

	public abstract void salvarComponente(ComponentePO componentePO) throws Exception;
		
	public abstract String gerarCodigo();

	public abstract List<? super ProdutoComponenteAbstract> findComponentesProdutosInternos();

	
}
