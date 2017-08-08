package util.cep;



import org.json.JSONArray;
import org.json.JSONObject;

import model.EnderecoPO;

/**
 * Classe java para obter um CEP no ViaCEP
 *
 * @author Pablo Alexander da Rocha Gon�alves
 */
public class ViaCEP extends ViaCEPBase {

    // constantes
    public static final double VIACEP_VERSAO = 0.33;

    /**
     * Constr�i uma nova classe
     */
    public ViaCEP() {
        super();
    }

    /**
     * Constr�i uma nova classe
     *
     * @param events eventos para a classe
     */
    public ViaCEP(ViaCEPEvents events) {
        super();
        this.Events = events;
    }

    /**
     * Constr�i uma nova classe e busca um CEP no ViaCEP
     *
     * @param events eventos para a classe
     * @param cep
     * @throws br.com.parg.viacep.ViaCEPException caso ocorra algum erro
     */
    public ViaCEP(String cep, ViaCEPEvents events) throws ViaCEPException {
        super();
        this.Events = events;
        this.buscar(cep);
    }

    /**
     * Constr�i uma nova classe e busca um CEP no ViaCEP
     *
     * @param cep
     * @throws br.com.parg.viacep.ViaCEPException caso ocorra algum erro
     */
    public ViaCEP(String cep) throws ViaCEPException {
        super();
        this.buscar(cep);
    }

    /**
     * Busca um CEP no ViaCEP
     *
     * @param cep
     * @throws br.com.parg.viacep.ViaCEPException caso ocorra algum erro
     */
    @Override
    public final EnderecoPO buscar(String cep) throws ViaCEPException {
        
    	EnderecoPO enderecoPO = new EnderecoPO();
    	
    	// define o cep atual
        currentCEP = cep;

        // define a url
        String url = "http://viacep.com.br/ws/" + cep + "/json/unicode/";

        // define os dados
       
        try {
        	JSONObject obj = new JSONObject(getHttpGET(url));

            if (!obj.has("erro")) {
                CEP novoCEP = new CEP(obj.getString("cep"),
                        obj.getString("logradouro"),
                        obj.getString("complemento"),
                        obj.getString("bairro"),
                        obj.getString("localidade"),
                        obj.getString("uf"),
                        obj.getString("ibge"),
                        obj.getString("gia"));

                             
                
                
                enderecoPO.setCep(novoCEP.CEP);
                enderecoPO.setUf(novoCEP.Uf);
                enderecoPO.setCidade(novoCEP.Localidade);
                enderecoPO.setBairro(novoCEP.Bairro);
                enderecoPO.setRua(novoCEP.Logradouro);
                
                
                
                CEPs.add(novoCEP);

                // atualiza o index
                index = CEPs.size() - 1;

                // verifica os Eventos
                if (Events instanceof ViaCEPEvents) {
                    Events.onCEPSuccess(this);
                }
            } else {
                // verifica os Eventos
                if (Events instanceof ViaCEPEvents) {
                    Events.onCEPError(currentCEP);
                }

                throw new ViaCEPException("N�o foi poss�vel encontrar o CEP", cep, ViaCEPException.class.getName());
            }
	
            
           
            
		} catch (Exception e) {
			// TODO: handle exception
		}
		return enderecoPO;
        
     }
    
    /**
     * Busca um CEP usando um endere�o
     *
     * @param cep classe CEP com uf, localidade, logradouro
     * @throws ViaCEPException
     */
    @Override
    public void buscarCEP(CEP cep) throws ViaCEPException {
        buscarCEP(cep.Uf, cep.Localidade, cep.Logradouro);
    }

    /**
     * Busca um CEP usando um endere�o
     *
     * @param Uf Estado
     * @param Localidade Municipio
     * @param Logradouro Rua, Avenidade, Viela...
     * @throws ViaCEPException
     */
    @Override
    public void buscarCEP(String Uf, String Localidade, String Logradouro) throws ViaCEPException {
     
    	try {
    		// define o cep atual
            currentCEP = "?????-???";

            // define a url
            String url = "http://viacep.com.br/ws/" + Uf.toUpperCase() + "/" + Localidade + "/" + Logradouro + "/json/";

            // obtem a lista de CEP's
            JSONArray ceps = new JSONArray(getHttpGET(url));

            if (ceps.length() > 0) {
                for (int i = 0; i < ceps.length(); i++) {
                    JSONObject obj = ceps.getJSONObject(i);

                    if (!obj.has("erro")) {
                        CEP novoCEP = new CEP(obj.getString("cep"),
                                obj.getString("logradouro"),
                                obj.getString("complemento"),
                                obj.getString("bairro"),
                                obj.getString("localidade"),
                                obj.getString("uf"),
                                obj.getString("ibge"),
                                obj.getString("gia"));

                        // insere o novo CEP
                        CEPs.add(novoCEP);

                        // atualiza o index
                        index = CEPs.size() - 1;

                        // verifica os Eventos
                        if (Events instanceof ViaCEPEvents) {
                            Events.onCEPSuccess(this);
                        }
                    } else {
                        // verifica os Eventos
                        if (Events instanceof ViaCEPEvents) {
                            Events.onCEPError(currentCEP);
                        }

                        throw new ViaCEPException("N�o foi poss�vel validar o CEP", currentCEP, ViaCEPException.class.getName());
                    }
                }
            } else {
                throw new ViaCEPException("Nenhum CEP encontrado", currentCEP, getClass().getName());
            }

		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	}
}
