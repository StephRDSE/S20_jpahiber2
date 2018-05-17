package daojpa;

import javax.persistence.EntityManager;

import co.simplon.City;
import dao.CityDao;

public class CityDaoJpa implements CityDao {

	private EntityManager em ;
	
	public CityDaoJpa(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public City createCity(City city) {
		em.persist(city);
		return city;
	}


	@Override
	public City getCityById(Long id) {
		return em.find(City.class, id);
		
	}

	@Override
	public City updateCity(City city) {
		City res= em.merge(city);
		return res;
	}

	@Override
	public void deleteCityById(Long id) {
		em.remove(getCityById(id));
		
	}

}
