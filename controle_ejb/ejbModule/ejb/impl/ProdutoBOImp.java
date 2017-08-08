package ejb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import dao.ComponenteDAO;
import dao.ProdutoComponenteDAO;
import dao.ProdutoDAO;
import ejb.ClienteFornecedorBO;
import ejb.ProdutoBO;
import exceptions.ControleException;
import model.ClienteFornecedorPO;
import model.ComponentePO;
import model.ProdutoComponentePO;
import model.ProdutoPO;
import type.ClienteFornecedorEnum;

@Stateless
public class ProdutoBOImp extends AbstractBO implements ProdutoBO {

	@EJB
	private ComponenteDAO componenteDAO;

	@EJB
	private ClienteFornecedorBO clienteFornecedorBO;
	
	@EJB
	private ProdutoDAO produtoDAO;
	
	@EJB
	private ProdutoComponenteDAO produtoComponenteDAO;
	
	
	@Override
	@TransactionAttribute
	public void salvarProduto(ProdutoPO entity) throws Exception {
		try {
			if (entity.getProdutoId() == null){
				preSaveProduto(entity);
				produtoDAO.save(entity);
				if (entity.getProduzido())
				this.saveProdutoComponentes(entity);
			} else {
				this.updateProduto(entity);
			}
		} catch (EJBTransactionRolledbackException  e) {
			super.verificaUnique(e);
			throw new ControleException();
		}
		
	}

	private void updateProduto(ProdutoPO entityNew) {
		ProdutoPO produtoPOOld = this.find(entityNew.getProdutoId());
		parseProduto(produtoPOOld,entityNew);
		produtoDAO.update(produtoPOOld);
		crudProdutoComponente(entityNew, produtoPOOld);
	}

	private void crudProdutoComponente(ProdutoPO entityNew, ProdutoPO produtoPOOld) {
		if ((produtoPOOld.getProdutoComponentePO() != null &&
		    !produtoPOOld.getProdutoComponentePO().isEmpty()) 
			&&
			(entityNew.getProdutoComponentePO() == null)){
			deleteProdutoComponente(produtoPOOld.getProdutoComponentePO());
		} else if ((produtoPOOld.getProdutoComponentePO() != null &&
			    	!produtoPOOld.getProdutoComponentePO().isEmpty()) 
					&&
					(entityNew.getProdutoComponentePO() != null &&
					 !entityNew.getProdutoComponentePO().isEmpty())){
			deleteProdutoComponente(produtoPOOld.getProdutoComponentePO());
			saveProdutoComponente(entityNew.getProdutoComponentePO());
		} else if ((produtoPOOld.getProdutoComponentePO() == null) 
					&&
					(entityNew.getProdutoComponentePO() != null &&
					!entityNew.getProdutoComponentePO().isEmpty())){
			saveProdutoComponente(entityNew.getProdutoComponentePO());
		}
	}
	
	
	
	private void deleteProdutoComponente(List<ProdutoComponentePO> produtoComponentePO) {
		for (ProdutoComponentePO produtoComponentePO2 : produtoComponentePO) {
			this.produtoComponenteDAO.delete(produtoComponentePO2);
		}
	}

	private void saveProdutoComponente(List<ProdutoComponentePO> produtoComponentePO) {
		for (ProdutoComponentePO produtoComponentePO2 : produtoComponentePO) {
			this.produtoComponenteDAO.save(parseToNewInstance(produtoComponentePO2));
		}
	}

	private ProdutoComponentePO parseToNewInstance(ProdutoComponentePO produtoComponentePO2) {
		ProdutoComponentePO produtoComponentePO = new ProdutoComponentePO();
		produtoComponentePO.setComponentePO(produtoComponentePO2.getComponentePO());
		produtoComponentePO.setProdutoPO(produtoComponentePO2.getProdutoPO());
		produtoComponentePO.setQuantidade(produtoComponentePO2.getQuantidade());
		return produtoComponentePO;
	}

	private void parseProduto(ProdutoPO produtoPOOld, ProdutoPO entityNew) {
		produtoPOOld.setCodigoProduto(entityNew.getCodigoProduto());
		produtoPOOld.setFornecedorPO(entityNew.getFornecedorPO());
		produtoPOOld.setFoto(entityNew.getFoto());
		produtoPOOld.setLucro(entityNew.getLucro());
		produtoPOOld.setNome(entityNew.getNome());
		produtoPOOld.setPrecoCompra(entityNew.getPrecoCompra());
		produtoPOOld.setPrecoVenda(entityNew.getPrecoVenda());
		produtoPOOld.setProduzido(entityNew.getProduzido());
	}

	private void preSaveProduto(ProdutoPO entity) throws Exception {
		if (!entity.getProduzido() && entity.getFornecedorPO() !=null){
			ClienteFornecedorPO fornecedorPO = entity.getFornecedorPO();
			fornecedorPO.setTipo(ClienteFornecedorEnum.FORNECEDOR.getValue());
			clienteFornecedorBO.save(fornecedorPO);
			entity.setFornecedorPO(fornecedorPO);
		} else {
			entity.setFornecedorPO(null);
		}
		
		if (entity.getCodigoProduto() == null || 
				entity.getCodigoProduto().isEmpty())
			entity.setCodigoProduto(this.gerarCodigo());
	}

	private void saveProdutoComponentes(ProdutoPO entity) {
		
		if(entity.getProdutoComponentePO() != null && !entity.getProdutoComponentePO().isEmpty()){
			
		for (ProdutoComponentePO produtoComponentePO : entity.getProdutoComponentePO()) {
			produtoComponentePO.setProdutoPO(entity);
			produtoComponentePO.setComponentePO(this.findComponente(produtoComponentePO.getComponentePO().getComponenteId()));
			produtoComponenteDAO.save(produtoComponentePO);
		}
		}
	}

	

	

	@Override
	public ProdutoPO find(Long entityID) {
		return produtoDAO.find(entityID);
	}

	@Override
	public List<ProdutoPO> findAll() {
		return produtoDAO.findAll();
	}

	@Override
	public List<ComponentePO> findComponentes() {
		return componenteDAO.findAll();
	}

	@Override
	@TransactionAttribute
	public void salvarComponente(ComponentePO componentePO) throws Exception {
		try {
			if (componentePO.getFornecedor() != null){
				ClienteFornecedorPO fornecedorPO = componentePO.getFornecedor();
				fornecedorPO.setTipo(ClienteFornecedorEnum.FORNECEDOR.getValue());
				clienteFornecedorBO.save(fornecedorPO);
				componentePO.setFornecedor(fornecedorPO);
			}
			
			if (componentePO.getComponenteId() == null){
				componenteDAO.save(componentePO);
			} else {
				this.updateComponente(componentePO);
			}
		}  catch (EJBTransactionRolledbackException  e) {
			super.verificaUnique(e);
			throw new ControleException();
		}
		
	}
	
	
	private void updateComponente(ComponentePO componentePO){
		
		ComponentePO componentePOParse = findComponente(componentePO.getComponenteId());
		this.parseComponente(componentePOParse, componentePO);
		
		if (componentePO.getFornecedor() != null){
			ClienteFornecedorPO oldCli = clienteFornecedorBO.find(componentePO.getFornecedor().getClienteFornecedorId());
			this.parseClienteFornecedor(oldCli, componentePO.getFornecedor());
			clienteFornecedorBO.update(oldCli);
		}
		
		componenteDAO.update(componentePOParse);
	}

	private void parseComponente(ComponentePO componentePOParse, ComponentePO componentePO) {
		
		componentePOParse.setNome(componentePO.getNome());
		componentePOParse.setFoto(componentePO.getFoto());
		componentePOParse.setObservacao(componentePO.getObservacao());
		componentePOParse.setValorUnitarioCompra(componentePO.getValorUnitarioCompra());
	}

	@Override
	public ComponentePO findComponente(Long entityID) {
		return componenteDAO.find(entityID);
	} 
	
	
	private void parseClienteFornecedor(ClienteFornecedorPO oldCli, ClienteFornecedorPO newCli) {
		oldCli.setNome(newCli.getNome());
		oldCli.setTelefone(newCli.getTelefone());
		oldCli.setCelular(newCli.getCelular());
		oldCli.setEmail(newCli.getEmail());
		oldCli.setTipo(newCli.getTipo());
		oldCli.setObservacao(newCli.getObservacao());
	}

	@Override
	public String gerarCodigo() {
		
		String codigo = null;
		while (true) {
			Random gerador = new Random();
		    for (int i = 0; i < 10; i++) {
		    	codigo = new Integer((gerador.nextInt(1000000))).toString();
		 	}
		    System.out.println(codigo);
		    HashMap<String, Object> map = new HashMap<>();
		    map.put("codigoProduto",codigo);
		    List<ProdutoPO> list = produtoDAO.executeNamedQuery(ProdutoPO.FIND_BY_CODIGO,map);
			if(list == null  || list.isEmpty()) break;
		}
	    
		return codigo;
	}
	
}
