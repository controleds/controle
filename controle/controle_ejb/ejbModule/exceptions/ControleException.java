package exceptions;

public class ControleException extends Exception {

	private static final long serialVersionUID = -3046137053472362712L;

	public ControleException(){
		super("N�o Foi Possivel Salvar. Problemas no BD. consulte o LOG");
	}
}
