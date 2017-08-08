package ejb.impl;

import javax.ejb.EJBTransactionRolledbackException;

import exceptions.ControleRegistroJaEncontradoException;

public abstract class AbstractBO {

	public void verificaUnique(EJBTransactionRolledbackException e) throws ControleRegistroJaEncontradoException {
		if (e != null && e.getMessage() != null) {
			e.getMessage().equals("org.hibernate.exception.ConstraintViolationException: could not execute statement");
			throw new ControleRegistroJaEncontradoException();
		}
	}
}
