package dao;



import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> {
	private final static String UNIT_NAME = "bdUnit";

	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager em;

	private Class<T> entityClass;

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		em.persist(entity);
	}
	
	
	public void delete(T entity) {
		T entityToBeRemoved = em.merge(entity);
		
		em.remove(entityToBeRemoved);
	}

	public T update(T entity) {
		T retorno = em.merge(entity);
		//em.flush();
		return retorno;
	}

	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}

	public T find(Long entityID) {
		return em.find(entityClass, entityID.longValue());
	}
	
	// Using the unchecked because JPA does not have a
	// em.getCriteriaBuilder().createQuery()<T> method
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		List<T> result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			Object o = query.getResultList();
			if (o != null) {
				 result =  (List<T>)o;
				 if (result.size() > 0) {
					 return result.get(0);
				 } else{
					 return null;
				 }
				
			} else{
				
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}
	
	@Deprecated
	protected int findOneIntResult(String namedQuery, Map<String, Object> parameters) {
		BigInteger result = null;

		try {
			String senha = (String) parameters.get("senha");
			String login = (String) parameters.get("login");
			String x = "select count(*) from cliente c where c.login = '"+login+"'   and c.senha = '"+senha+ "'";   
			Query query = em.createNativeQuery(x);
			// Method that will populate parameters if they are passed not null and empty
			//if (parameters != null && !parameters.isEmpty()) {
			//	populateQueryParameters(query, parameters);
			//}
			result  = (BigInteger) query.getSingleResult();
			
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result.intValue();
	}
	
	public void executeNativeQuery(String sql, Map<String, Object> parameters) {

		try {
			Query query = em.createNativeQuery(sql);
			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			query.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> executeNamedQuery(String namedQuery,  Map<String, Object> parameters) {
		List<T> result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);
			
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			result = (List<T>)  query.getResultList();
			
			
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	protected List<T> findListResult(String namedQuery,  Map<String, Object> parameters) {
		List<T> result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);
			
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			result = (List<T>)  query.getResultList();
			
			
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	
	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}
