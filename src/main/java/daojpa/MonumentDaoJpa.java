package daojpa;

import javax.persistence.EntityManager;

import co.simplon.Monument;
import dao.MonumentDao;

public class MonumentDaoJpa extends DaoJpa<Monument> implements MonumentDao{
	
	public MonumentDaoJpa(EntityManager em ) {
		super(em, Monument.class);
		
	}


}
