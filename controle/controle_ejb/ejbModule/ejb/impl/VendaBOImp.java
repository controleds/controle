package ejb.impl;

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
	public List<VendaPO> consulta(Date dateIni, Date dateFim) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("dateIni", dateIni);
		map.put("dateFim", dateFim);
		List<VendaPO> list = vendaDAO.executeNamedQuery(VendaPO.FIND_BY_DATA, map);
		return list;
	}

	
}
