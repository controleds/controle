package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import reports.Clientes;




@ManagedBean(name="orcamentosMB")
@ViewScoped
public class OrcamentosMB extends AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
		
	@PostConstruct
	public void ini(){
	}
	
	
	
	public void teste(){
		
		Clientes clientes = new Clientes("x");
		Clientes clientes1 = new Clientes("xx");
		Clientes clientes2 = new Clientes("xxx");
		
		List<Clientes> list = new ArrayList<>();
		list.add(clientes);
		list.add(clientes1);
		list.add(clientes2);
		
		
		this.imprimeRelatorio(list, null, "report2");
	}


	
	
}
