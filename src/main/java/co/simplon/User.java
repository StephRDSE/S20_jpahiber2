package co.simplon;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "Name", nullable = false, length = 100)
	private String name;

	@ManyToMany
	@JoinTable(name = "USER_MONUMENT", joinColumns = {
			@JoinColumn(name = "fk_user", referencedColumnName = "Id") }, inverseJoinColumns = {
					@JoinColumn(name = "fk_monument", referencedColumnName = "Id") })
	private Set<Monument> monuments = new HashSet<Monument>();

	public User() {
	}

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMonument(Monument m) {
		monuments.add(m);
		m.getUsers().add(this);
	}

	public Set<Monument> getMonuments() {
		return monuments;
	}

	public void setMonuments(Set<Monument> monuments) {
		this.monuments = monuments;
	}

	public String toString() {
		return "User :{ id= " + id + "\n name= " + name + "\n nb momunents" + monuments.size() + "\n}";
	}

}
