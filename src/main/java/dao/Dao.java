package dao;


public interface Dao<T>{

	T create(T obj);
    T getById(Long id);
    T update(T obj);
    void deleteById(Long id);
	
}
