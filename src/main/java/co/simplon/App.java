package co.simplon;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import daojpa.CityDaoJpa;
import daojpa.MonumentDaoJpa;
import daojpa.UserDaoJpa;

public class App implements AutoCloseable {
	private static final String PERSITENCE_UNIT_NAME = "demo-jpa-1";
	private EntityManagerFactory entityManagerFactory;

	App() {
		entityManagerFactory = createEntityManagerFactory();

	}
 
	public static void main(String[] args) {
		System.out.println("Hello JPA Demo");
 		
		try (App app = new App()) {
			EntityManager em = app.entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			//creation d'une ville
			
			CityDaoJpa cityDaoJpa = new CityDaoJpa(em);
			City city1 = new City("Atlantis", 0, 0);
			cityDaoJpa.create(city1);
			System.out.println("A la création \n" + city1);
			
			
			City city2 = new City("New York", 0, 0);
			cityDaoJpa.create(city2);
			System.out.println("A la création \n" + city2);
			
			//Update d'une ville
			
			city1.setName("Paris");
			city1.setLatitude(10.);
			city1.setLongitude(5.);
			cityDaoJpa.update(city1);
			System.out.println("A la modification \n" + city1);
			
			//lecture d'un enregistrement Table ville
			
			Long  myCityId = city1.getId();
			System.out.println("lecture d'un enregistrement \n" + cityDaoJpa.getById(myCityId));
			
			//creation d'un monument
			
			MonumentDaoJpa monumentDaoJpa = new MonumentDaoJpa(em);
			Monument monument1 = new Monument("Tour Eiffel", city1);
			monumentDaoJpa.create(monument1);
			System.out.println("A la création \n" + monument1);
			
			Monument monument2 = new Monument("Concorde", city2);
			monumentDaoJpa.create(monument2);
			
			//Update d'un monument
			
			monument1.setName("Statue de la liberté");
			monument1.setCity(city2);
			monumentDaoJpa.update(monument1);
			System.out.println("A la modification \n" + monument1);
			
			//lecture d'un enregistrement Table monument
			
			Long myMonumentId = monument1.getId();
			System.out.println("lecture d'un enregistrement \n" + monumentDaoJpa.getById(myMonumentId));
			
			//creation d'un user
			
			UserDaoJpa userDaoJpa = new UserDaoJpa(em);
			User user1 = new User("Robert");
			user1.addMonument(monument1);
			userDaoJpa.create(user1);
			System.out.println("A la création \n" + user1);
			
			User user2 = new User("Louis");
			user2.addMonument(monument2);
			userDaoJpa.create(user2);
			
			//Update d'un user
			
			user1.setName("Michel");
			userDaoJpa.update(user1);
			System.out.println("A la modification \n" + user1);
			
			//lecture d'un enregistrement Table users
			
			Long myUserId = user1.getId();
			System.out.println("lecture d'un enregistrement \n" + userDaoJpa.getById(myUserId));
			
			//Suppression d'un user par son Id
			
			userDaoJpa.deleteById(myUserId);
			
			//Suppression d'un monument par son Id
			
			monumentDaoJpa.deleteById(myMonumentId);
			
			//Suppression d'une ville par Id
			
			cityDaoJpa.deleteById(myCityId);      
			
			em.getTransaction().commit();
			em.close();
		
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

	
}