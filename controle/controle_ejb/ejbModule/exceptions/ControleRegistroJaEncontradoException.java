package exceptions;

public class ControleRegistroJaEncontradoException extends Exception {

	private static final long serialVersionUID = -3046137053472362712L;

	public ControleRegistroJaEncontradoException(){
		super("Não Foi possível salvar, Já existe um registro com esse Nome.");
	}
}
