package co.simplon;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import daojpa.CityDaoJpa;
import daojpa.MonumentDaoJpa;

/**
 * Hello world!
 *
 */
public class App implements AutoCloseable {
	private static final String PERSITENCE_UNIT_NAME = "demo-jpa-1";
	private EntityManagerFactory entityManagerFactory;

	App() {
		entityManagerFactory = createEntityManagerFactory();

	}

	public static void main(String[] args) {
		System.out.println("Hello JPA Demo");

//		User user1 = new User("Stephane");
//		User user2 = new User("Fabien");
//		User user3 = new User("Fred");
//		user1.addMonument(monument1);
//		user1.addMonument(monument3);
//		user1.addMonument(monument2);
//		user2.addMonument(monument3);
//		user2.addMonument(monument2);
//		user3.addMonument(monument3);
		//Long searchedId = 2L;
 		
		try (App app = new App()) {
			EntityManager em = app.entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			//creation d'une ville
			
			
			
			CityDaoJpa cityDaoJpa = new CityDaoJpa(em);
			City city1 = new City("Atlantis", 0, 0);
			cityDaoJpa.createCity(city1);
			System.out.println("A la création \n" + city1);
			
			
			City city2 = new City("New York", 0, 0);
			cityDaoJpa.createCity(city2);
			System.out.println("A la création \n" + city2);
			
			
			
			//Update d'une ville
			
			city1.setName("Paris");
			city1.setLatitude(10.);
			city1.setLongitude(5.);
			cityDaoJpa.updateCity(city1);
			System.out.println("A la modification \n" + city1);
			
			//lecture d'un enregistrement Table ville
			
			Long  myCityId = city1.getId();
			System.out.println("lecture d'un enregistrement \n" + cityDaoJpa.getCityById(myCityId));
			
			//creation d'un monument
			
			MonumentDaoJpa monumentDaoJpa = new MonumentDaoJpa(em);
			Monument monument1 = new Monument("Tour Eiffel", city1);
			monumentDaoJpa.createMonument(monument1);
			System.out.println("A la création \n" + monument1);
			
			//Update d'un monument
			
			monument1.setName("Statue de la liberté");
			monument1.setCity(city2);
			monumentDaoJpa.updateMonument(monument1);
			System.out.println("A la modification \n" + monument1);
			
			//lecture d'un enregistrement Table monument
			
			Long myMonumentId = monument1.getId();
			System.out.println("lecture d'un enregistrement \n" + monumentDaoJpa.getMonumentById(myMonumentId));
			
			//Suppression d'un monument par son Id
			
			monumentDaoJpa.deleteMonumentById(myMonumentId);
			
			//Suppression d'une ville par Id
			
			cityDaoJpa.deleteCityById(myCityId);      
			
			em.getTransaction().commit();
			em.close();
//			System.out.println(app.createCity(city1));
//			System.out.println(app.createCity(city2));
//			System.out.println(app.createCity(city3));
//			
//			System.out.println(app.createMonument(monument1));
//			System.out.println(app.createMonument(monument2));
//			System.out.println(app.createMonument(monument3));
//			
//			System.out.println(app.createUser(user1));
//			System.out.println(app.createUser(user2));
//			System.out.println(app.createUser(user3));
//			
//			app.readCity();				
//			
//			System.out.println();
//			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void close() {
		entityManagerFactory.close();
	}

	private EntityManagerFactory createEntityManagerFactory() {

		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		for (String envName : env.keySet()) {
			if (envName.contains("DB_USER")) {
				configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
			}
			if (envName.contains("DB_PASS")) {
				configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
			}
			if (envName.contains("DB_URL")) {
				configOverrides.put("javax.persistence.jdbc.url", env.get(envName));
			}
			if (envName.contains("DB_SCHEMA")) {
				// configOverrides.put("hibernate.default_schema", env.get(envName));
			}
		}
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSITENCE_UNIT_NAME,
				configOverrides);

		return entityManagerFactory;
	}


	public User createUser(User user) {
	    EntityManager em= entityManagerFactory.createEntityManager();
	    user= create(em, user);
	    em.close();
	    return user;
	}
	public User create(EntityManager em, User user) {
	    em.getTransaction().begin();
	    em.persist(user);
	    em.getTransaction().commit();
	    return user;
	}
	
	

}