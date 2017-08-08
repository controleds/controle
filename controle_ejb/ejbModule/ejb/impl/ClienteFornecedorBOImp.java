package ejb.impl;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import dao.ClienteFornecedorDAO;
import dao.EnderecoDAO;
import ejb.ClienteFornecedorBO;
import model.ClienteFornecedorPO;
import model.EnderecoPO;
import type.ClienteFornecedorEnum;

@Stateless
public class ClienteFornecedorBOImp implements ClienteFornecedorBO {

	@EJB
	private ClienteFornecedorDAO clienteFornecedorDAO;
	@EJB
	private EnderecoDAO enderecoDAO;
	
	@Override
	@TransactionAttribute
	public void save(ClienteFornecedorPO clifor) throws Exception {
		
		if (clifor.getClienteFornecedorId() == null){
			
			enderecoDAO.save(clifor.getEnderecoPO());
			clienteFornecedorDAO.save(clifor);
			
		} else {
			update(clifor);
		}
	}

	@Override
	public ClienteFornecedorPO update(ClienteFornecedorPO entity) {
		
		ClienteFornecedorPO clienteFornecedorParse = clienteFornecedorDAO.find(entity.getClienteFornecedorId());
		this.parseClienteFornecedor(clienteFornecedorParse, entity);
		
		EnderecoPO enderecoParse = enderecoDAO.find(entity.getEnderecoPO().getEnderecoId());
		this.enderecoParse(enderecoParse, entity.getEnderecoPO());
		
		enderecoDAO.update(enderecoParse);
		clienteFornecedorDAO.update(clienteFornecedorParse);
		
		return clienteFornecedorParse;
	}

	private void enderecoParse(EnderecoPO oldEnd, EnderecoPO newEnd ) {
		 oldEnd.setCep(newEnd.getCep());
		 oldEnd.setUf(newEnd.getUf());
		 oldEnd.setCidade(newEnd.getCidade());
		 oldEnd.setBairro(newEnd.getBairro());
		 oldEnd.setRua(newEnd.getRua());
		 oldEnd.setNumero(newEnd.getNumero());
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
	public void delete(ClienteFornecedorPO entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClienteFornecedorPO find(Long entityID) {
		return clienteFornecedorDAO.find(entityID);
	}

	@Override
	public List<ClienteFornecedorPO> findClientes() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tipo", ClienteFornecedorEnum.CLIENTE.getValue());
		return clienteFornecedorDAO.executeNamedQuery(ClienteFornecedorPO.FIND_BY_TIPO, map);
	}

	@Override
	public List<ClienteFornecedorPO> findFornecedores() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tipo", ClienteFornecedorEnum.FORNECEDOR.getValue());
		return clienteFornecedorDAO.executeNamedQuery(ClienteFornecedorPO.FIND_BY_TIPO, map);
	}


	
	
	
	
}
