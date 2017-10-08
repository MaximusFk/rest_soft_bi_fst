package com.mxf.rest.entity;

public class Author extends ABaseEntity {
	
	private int id;
	private String name;
	private String email;
	
	public Author() {
		this.name = "";
		this.email = "";
	}

	public Author(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Author { ")
				.append("id=" + id)
				.append(", name=" + name)
				.append(", email=" + email)
				.append('}')
				.toString();
	}

}
