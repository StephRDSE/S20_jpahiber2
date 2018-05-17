package daojpa;

import javax.persistence.EntityManager;

import co.simplon.User;
import dao.UserDao;

public class UserDaoJpa extends DaoJpa<User> implements UserDao {

	public UserDaoJpa(EntityManager em) {
		super(em, User.class);

	}

}