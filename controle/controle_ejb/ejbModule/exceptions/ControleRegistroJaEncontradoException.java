package exceptions;

public class ControleRegistroJaEncontradoException extends Exception {

	private static final long serialVersionUID = -3046137053472362712L;

	public ControleRegistroJaEncontradoException(){
		super("N�o Foi poss�vel salvar, J� existe um registro com esse Nome.");
	}
}
