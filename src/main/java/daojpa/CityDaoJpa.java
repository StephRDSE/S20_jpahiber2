package daojpa;

import javax.persistence.EntityManager;

import co.simplon.City;
import dao.CityDao;

public class CityDaoJpa extends DaoJpa<City> implements CityDao {

	public CityDaoJpa(EntityManager em) {
		super(em, City.class);
	}

}
