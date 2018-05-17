package daojpa;



import java.util.List;

import javax.persistence.EntityManager;



public abstract class DaoJpa<T> {

	private EntityManager em ;
	private Class<T> objClass ;
	

	public DaoJpa(EntityManager em, Class<T>objClass) {
		this.em = em;
		this.objClass = objClass;
	}
	
	public T create(T obj) {
		em.persist(obj);
		return obj;
	}
	public T getById(Long id) {
		return em.find(objClass, id);
	}

	public T update(T obj) {
		T res= em.merge(obj);
		return res;
	}

	public void deleteById(Long id) {
		em.remove(getById(id));		
	
	}
	
	public List<T> findAll(int first, int size) {
	    return em.createNamedQuery(objClass.getSimpleName() + ".findAll", objClass)
		  .setFirstResult(first).setMaxResults(size).getResultList();
	}

	
	
}
