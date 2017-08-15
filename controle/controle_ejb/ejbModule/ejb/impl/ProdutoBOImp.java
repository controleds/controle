package ejb.impl;

import java.util.ArrayList;
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
import dao.ProdutoInternoDAO;
import ejb.ClienteFornecedorBO;
import ejb.ProdutoBO;
import exceptions.ControleException;
import model.ClienteFornecedorPO;
import model.ComponentePO;
import model.ProdutoComponenteAbstract;
import model.ProdutoComponentePO;
import model.ProdutoInternoPO;
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
	
	@EJB
	private ProdutoInternoDAO produtoInternoDAO;
	
	
	@Override
	@TransactionAttribute
	public void salvarProduto(ProdutoPO entity) throws Exception {
		try {
			atualizaProdutoInterno(entity);
			if (entity.getProdutoId() == null){
				preSaveProduto(entity);
				produtoDAO.save(entity);
				if (entity.getProduzido())
					this.saveProdutoComponentes(entity);
					if(entity.getProdutoInternoPO() != null && !entity.getProdutoInternoPO().isEmpty())
						saveProdutoInterno(entity);
			} else {
				this.updateProduto(entity);
			}
		} catch (EJBTransactionRolledbackException  e) {
			super.verificaUnique(e);
			throw new ControleException();
		}
		
	}

	private void atualizaProdutoInterno(ProdutoPO produtoPO){
		if (produtoPO.getInterno()){
			produtoPO.setPrecoVenda(0d);
			produtoPO.setLucro(0d);
		}
	}
	
	private void updateProduto(ProdutoPO entityNew) {
		ProdutoPO produtoPOOld = this.find(entityNew.getProdutoId());
		parseProduto(produtoPOOld,entityNew);
		produtoDAO.update(produtoPOOld);
		
		atualizarEstoqueUpdateProduto(entityNew);
		
		crudProdutoComponente(entityNew, produtoPOOld);
		if (entityNew.getInterno()  && !entityNew.getProdutoInternoPO().isEmpty())
			crudProdutoInterno(entityNew, produtoPOOld);
		
	}

	private void atualizarEstoqueUpdateProduto(ProdutoPO prod) {
		if (prod.getProdutoComponentePO() != null && !prod.getProdutoComponentePO().isEmpty()){
			for (ProdutoComponentePO po : prod.getProdutoComponentePO()) {
				atualizaEstoqueComponente(prod.getQuantidadeEstoque(), po.getComponentePO());
			}
		}
		if (prod.getProdutoInternoPO() != null && !prod.getProdutoInternoPO().isEmpty()){
			for (ProdutoInternoPO po : prod.getProdutoInternoPO()) {
				atualizaEstoqueProdutoInterno(prod.getQuantidadeEstoque(), po.getProdutoInterno());
			}
		}
		
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
	
	
	private void crudProdutoInterno(ProdutoPO entityNew, ProdutoPO produtoPOOld) {
		if ((produtoPOOld.getProdutoInternoPO() != null &&
		    !produtoPOOld.getProdutoInternoPO().isEmpty()) 
			&&
			(entityNew.getProdutoInternoPO() == null)){
			deleteProdutoInterno(produtoPOOld.getProdutoInternoPO());
		} else if ((produtoPOOld.getProdutoInternoPO() != null &&
			    	!produtoPOOld.getProdutoInternoPO().isEmpty()) 
					&&
					(entityNew.getProdutoInternoPO() != null &&
					 !entityNew.getProdutoInternoPO().isEmpty())){
			deleteProdutoInterno(produtoPOOld.getProdutoInternoPO());
			saveProdutoInterno(entityNew.getProdutoInternoPO());
		} else if ((produtoPOOld.getProdutoInternoPO() == null) 
					&&
					(entityNew.getProdutoInternoPO() != null &&
					!entityNew.getProdutoInternoPO().isEmpty())){
			saveProdutoInterno(entityNew.getProdutoInternoPO());
		}
	}
	
	private void deleteProdutoComponente(List<ProdutoComponentePO> produtoComponentePO) {
		for (ProdutoComponentePO produtoComponentePO2 : produtoComponentePO) {
			this.produtoComponenteDAO.delete(produtoComponentePO2);
		}
	}
	
	private void deleteProdutoInterno(List<ProdutoInternoPO> produtoInternoPO) {
		for (ProdutoInternoPO produtoPO2 : produtoInternoPO) {
			this.produtoInternoDAO.delete(produtoPO2);
		}
	}
	

	private void saveProdutoComponente(List<ProdutoComponentePO> produtoComponentePO) {
		for (ProdutoComponentePO produtoComponentePO2 : produtoComponentePO) {
			this.produtoComponenteDAO.save(parseToNewInstance(produtoComponentePO2));
		}
	}
	
	private void saveProdutoInterno(List<ProdutoInternoPO> produtoInternoPO) {
		for (ProdutoInternoPO produtoPO2 : produtoInternoPO) {
			this.produtoInternoDAO.save(parseToNewInstanceProdutoInterno(produtoPO2));
		}
	}

	
	private ProdutoInternoPO parseToNewInstanceProdutoInterno(ProdutoInternoPO produtoPO2) {
		ProdutoInternoPO produtoPO = new ProdutoInternoPO();
		produtoPO.setProdutoInterno(produtoPO2.getProdutoInterno());
		produtoPO.setProdutoPO(produtoPO2.getProdutoPO());
		produtoPO.setQuantidade(produtoPO2.getQuantidade());
		return produtoPO;
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
		produtoPOOld.setInterno(entityNew.getInterno());
		produtoPOOld.setQuantidadeEstoque(entityNew.getQuantidadeEstoque());
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
		Integer quan = entity.getQuantidadeEstoque();
		if (entity.getProdutoComponentePO() != null && !entity.getProdutoComponentePO().isEmpty()) {
			for (ProdutoComponentePO produtoComponentePO : entity.getProdutoComponentePO()) {
				
				if (quan != null && quan > 0)
					atualizaEstoqueComponente(quan,produtoComponentePO.getComponentePO());
				
				produtoComponentePO.setProdutoPO(entity);
				produtoComponentePO
						.setComponentePO(this.findComponente(produtoComponentePO.getComponentePO().getComponenteId()));
				
				produtoComponenteDAO.save(produtoComponentePO);
			}
		}
	}
	
	
	private void saveProdutoInterno(ProdutoPO entity) {
		Integer quan = entity.getQuantidadeEstoque();
		if (entity.getProdutoInternoPO() != null && !entity.getProdutoInternoPO().isEmpty()) {
			for (ProdutoInternoPO produtointernoPO : entity.getProdutoInternoPO()) {
				
				if (quan != null && quan > 0)
					atualizaEstoqueProdutoInterno(quan,produtointernoPO.getProdutoInterno());
				
				produtointernoPO.setProdutoPO(entity);
				produtointernoPO
						.setProdutoInterno(this.find(produtointernoPO.getProdutoInterno().getProdutoId()));
				
				produtoInternoDAO.save(produtointernoPO);
			}
		}
	}
	

	

	private void atualizaEstoqueProdutoInterno(Integer quan, ProdutoPO produtoInterno) {
		ProdutoPO pPO2  = produtoDAO.find(produtoInterno.getProdutoId());
		Integer quantidadeEstoque = pPO2.getQuantidadeEstoque();
		if (quantidadeEstoque > 0){
			Integer qtNew= quantidadeEstoque - (produtoInterno.getQuantidade()*quan);
			if(qtNew < 0) qtNew = 0;
			pPO2.setQuantidadeEstoque(qtNew);
			produtoDAO.update(pPO2);
		}
	}

	private void atualizaEstoqueComponente(Integer quan, ComponentePO componentePO) {
		ComponentePO componentePO2  = componenteDAO.find(componentePO.getComponenteId());
		Integer quantidadeEstoque = componentePO2.getQuantidadeEstoque();
		if (quantidadeEstoque > 0){
			Integer qtNew= quantidadeEstoque - (componentePO.getQuantidade()*quan);
			if(qtNew < 0) qtNew = 0;
			componentePO2.setQuantidadeEstoque(qtNew);
			componenteDAO.update(componentePO2);
		}
		
	}

	@Override
	public ProdutoPO find(Long entityID) {
		return produtoDAO.find(entityID);
	}

	@Override
	public List<ProdutoPO> findAll() {
		return produtoDAO.executeNamedQuery(ProdutoPO.PRODUTOS_A_VENDA, null);
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
		componentePOParse.setQuantidadeEstoque(componentePO.getQuantidadeEstoque());
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
		    HashMap<String, Object> map = new HashMap<>();
		    map.put("codigoProduto",codigo);
		    List<ProdutoPO> list = produtoDAO.executeNamedQuery(ProdutoPO.FIND_BY_CODIGO,map);
			if(list == null  || list.isEmpty()) break;
		}
		return codigo;
	}

	@Override
	public List<? super ProdutoComponenteAbstract> findComponentesProdutosInternos() {
		List<? super ProdutoComponenteAbstract> list = new ArrayList<>();
		list.addAll(componenteDAO.findAll());
		list.addAll(produtoDAO.executeNamedQuery(ProdutoPO.PRODUTOS_INTERNOS, null));
		return list;
	}
	
}
