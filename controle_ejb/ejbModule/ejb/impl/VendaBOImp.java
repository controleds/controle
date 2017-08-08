package ejb.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import dao.ProdutoDAO;
import dao.VendaDAO;
import dao.VendaProdutoDAO;
import ejb.VendaBO;
import model.ProdutoPO;
import model.VendaPO;
import model.VendaProdutoPO;
import model.VendedorPO;

@Stateless
public class VendaBOImp implements VendaBO {

	@EJB
	private VendaDAO vendaDAO;
	
	@EJB
	private VendaProdutoDAO vendaProdutoDAO;
	
	@EJB
	private ProdutoDAO produtoDAO;

	@Override
	@TransactionAttribute
	public void save(VendaPO entity) {
		vendaDAO.save(entity);
		if (entity.getProdutos() != null && 
			!entity.getProdutos().isEmpty()){
			this.saveVendaProduto(entity);
			this.atualizaEstoque(entity);
		}
		
	}

	private void atualizaEstoque(VendaPO entity) {
		for (VendaProdutoPO vendaProdutoPO: entity.getProdutos()) {
			ProdutoPO produtoPO = produtoDAO.find(vendaProdutoPO.getProdutoPO().getProdutoId());
			Integer quantidadeEstoque = produtoPO.getQuantidadeEstoque();
			if (quantidadeEstoque > 0){
				Integer qtNew= quantidadeEstoque - vendaProdutoPO.getQuantidade();
				if(qtNew <= 0) qtNew = 0;
				produtoPO.setQuantidadeEstoque(qtNew);
				produtoDAO.update(produtoPO);
			}
		}
	}

	private void saveVendaProduto(VendaPO entity) {
		for (VendaProdutoPO vendaProdutoPO: entity.getProdutos()) {
			vendaProdutoPO.setVendaPO(entity);
			vendaProdutoDAO.save(vendaProdutoPO);	
		}
	}

	@Override
	public List<VendaPO> consulta(Date dateIni, Date dateFim, List<VendedorPO> vendedorPOs) throws Exception {
		List<VendaPO> list = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("dateIni", dateIni);
		map.put("dateFim", dateFim);
		if(vendedorPOs == null || vendedorPOs.isEmpty()){
			list = vendaDAO.executeNamedQuery(VendaPO.FIND_BY_DATA, map);
		}else {
			map.put("inclList", getInListVendedores(vendedorPOs));
			list = vendaDAO.executeNamedQuery(VendaPO.FIND_BY_DATA_VENDEDOR, map);
		}
		
		return list;
	}

	private List<Long> getInListVendedores(List<VendedorPO> vendedorPOs) {
		List<Long> ids = new ArrayList<>();
		for (VendedorPO v : vendedorPOs) ids.add(v.getVendedorId());
		return ids;
	}

	
}
