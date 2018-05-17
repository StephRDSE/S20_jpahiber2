package daojpa;

import javax.persistence.EntityManager;

import co.simplon.Monument;
import dao.MonumentDao;

public class MonumentDaoJpa implements MonumentDao{
	
	private EntityManager em ;

	public MonumentDaoJpa(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Monument createMonument(Monument monument) {
		em.persist(monument);
		return monument;
	}

	@Override
	public Monument getMonumentById(Long id) {
		return em.find(Monument.class, id);
	}

	@Override
	public Monument updateMonument(Monument monument) {
		Monument res= em.merge(monument);
		return res;
	}

	@Override
	public void deleteMonumentById(Long id) {
		em.remove(getMonumentById(id));		
	
	}

}
