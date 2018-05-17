package co.simplon;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
		
		City city1 = new City("Atlantis", 0, 0.5);
		City city2 = new City("Paris", 1, 1);
		City city3 = new City("Londres", 2, 0.5);
		Monument monument1 = new Monument("Tour Eiffel", new City(2L,"PaRiS", -1., -2.));
		Monument monument2 = new Monument("Arc de triomphe", new City(2L,"PaRiS", -1., -2.));
		Monument monument3 = new Monument("Big ben", new City(3L,"Londres", 2, 0.5));
		User user1 = new User("Stephane");
		User user2 = new User("Fabien");
		User user3 = new User("Fred");
		user1.addMonument(monument1);
		user1.addMonument(monument3);
		user1.addMonument(monument2);
		user2.addMonument(monument3);
		user2.addMonument(monument2);
		user3.addMonument(monument3);
		//Long searchedId = 2L;
 		
		try (App app = new App()) {
//			System.out.println(app.createCity(city1));
//			System.out.println(app.createCity(city2));
//			System.out.println(app.createCity(city3));
//			
			System.out.println(app.createMonument(monument1));
			System.out.println(app.createMonument(monument2));
			System.out.println(app.createMonument(monument3));
			
			System.out.println(app.createUser(user1));
			System.out.println(app.createUser(user2));
			System.out.println(app.createUser(user3));
			
			app.readCity();				
			
			System.out.println();
			
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

	/*public City createCity(City city) {
		EntityManager em = entityManagerFactory.createEntityManager();
		city = create(em, city);
		em.close();
		return city;
	}

	public City create(EntityManager em, City city) {
		em.getTransaction().begin();
		em.persist(city);
		em.getTransaction().commit();
		return city;
	}*/

	public City createCityAndUpdate() {
		EntityManager em = entityManagerFactory.createEntityManager();
		City city = new City("Paris", 0, 0.5);
		em.getTransaction().begin();
		em.persist(city);
		city.setLatitude(1000.);
		em.getTransaction().commit();// MAGIC HAPPENS HERE !
		em.close();
		return city;
	}

	public City readCity() {
		EntityManager em = entityManagerFactory.createEntityManager();
		City city = readCity(em, 2L);
/*		city.setLatitude(100.);
		em.getTransaction().begin();
		em.getTransaction().commit();*/
		System.out.println(city);
		em.close();
		return city;
	}

	public City readCity(EntityManager em, Long id) {
		return em.find(City.class, id);
	}
	
	public City updateCity() {
	    return update(new City(2L,"PaRiS", -1., -2.));
	}
	public City update(City city) {
	    EntityManager em= entityManagerFactory.createEntityManager();
	    em.getTransaction().begin();
	    City res= em.merge(city);
	    em.getTransaction().commit();
	    em.close();
	    return res;
	}
	
	public City deleteCityById() {
		EntityManager em= entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
	    City city =em.find(City.class, 2L);
	    em.remove(city);
	    em.getTransaction().commit();
	    em.close();
		return city;
	}
	
	public City deleteCityByEntry() {
		EntityManager em = entityManagerFactory.createEntityManager();
		City city = readCity(em, 1L);
		em.getTransaction().begin();
		em.remove(city);
	    em.getTransaction().commit();
	    em.close();
		return city;
				
	}
	
	public Monument createMonument(Monument monument) {
	    EntityManager em= entityManagerFactory.createEntityManager();
	    monument= create(em, monument);
	    em.close();
	    return monument;
	}
	public Monument create(EntityManager em, Monument monument) {
	    em.getTransaction().begin();
	    em.persist(monument);
	    em.getTransaction().commit();
	    return monument;
	}
	
	public Monument createMonumentAndUpdate() {
		EntityManager em = entityManagerFactory.createEntityManager();
		Monument monument = new Monument("Arc de triomphe", new City(2L,"PaRiS", -1., -2.));
		em.getTransaction().begin();
		em.persist(monument);
		monument.setName("Grande arche");
		em.getTransaction().commit();// MAGIC HAPPENS HERE !
		em.close();
		return monument;
	}

	public Monument readMonument() {
		EntityManager em = entityManagerFactory.createEntityManager();
		Monument monument = readMonument(em, 2L);
		monument.setName("Concorde");
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
		return monument;
	}

	public Monument readMonument(EntityManager em, Long id) {
		return em.find(Monument.class, id);
	}
	
	public Monument updateMonument() {
	    return update(new Monument("Louvre", new City(2L,"PaRiS", -1., -2.)));  // peut être une erreur
	}
	public Monument update(Monument monument) {
	    EntityManager em= entityManagerFactory.createEntityManager();
	    em.getTransaction().begin();
	    Monument res= em.merge(monument);
	    em.getTransaction().commit();
	    em.close();
	    return res;
	}
	
	public Monument deleteMonumentById() {
		EntityManager em= entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Monument monument =em.find(Monument.class, 2L);
	    em.remove(monument);
	    em.getTransaction().commit();
	    em.close();
		return monument;
	}
	
	public Monument deleteMonumentByEntry() {
		EntityManager em = entityManagerFactory.createEntityManager();
		Monument monument = readMonument(em, 1L);
		em.getTransaction().begin();
		em.remove(monument);
	    em.getTransaction().commit();
	    em.close();
		return monument;
				
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