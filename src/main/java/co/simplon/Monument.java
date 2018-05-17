package co.simplon;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MONUMENTS")
public class Monument {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monument_seq")
	@SequenceGenerator(name = "monument_seq", sequenceName = "monument_sequence", allocationSize = 1)
	@Column(name = "Id")
	private Long id;
	@Column(name = "Name", nullable = false, length = 255)
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_cities_id", foreignKey= @ForeignKey(name = "fk_cities_id"))
	private City city;
	@ManyToMany(mappedBy="monuments")
	private Set<User> users = new HashSet<User>();

	public Monument() {
	}

	public Monument(String name, City city) {
		this(null, name, city);
	}

	public Monument(Long id, String name, City city) {
		super();
		this.id = id;
		this.name = name;
		this.city= city;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Monument [id=" + id + ", name=" + name + ", city=" + city + "]";
	}

	
}