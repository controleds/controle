package reports;

import java.util.ArrayList;
import java.util.List;

public class testemain {

	public static void main(String[] args) throws Exception {
		
		Clientes clientes = new Clientes("x");
		Clientes clientes1 = new Clientes("xx");
		Clientes clientes2 = new Clientes("xxx");
		
		List<Clientes> list = new ArrayList<>();
		list.add(clientes);
		list.add(clientes1);
		list.add(clientes2);
		
		ClienteREL rel = new ClienteREL();
		rel.imprimir(list);
	}

}
