package com.mxf.rest.entity;

import java.sql.Date;

public class Article extends ABaseEntity {
	
	private int id;
	private Integer authorId;
	private String title;
	private String label;
	private String text;
	private Date created_date;
	
	public Article() {
		
	}

	public Article(int id, Integer authorId, String title, String label, String text, Date date) {
		this.id = id;
		this.title = title;
		this.label = label;
		this.text = text;
		this.created_date = date;
		this.authorId = authorId;
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return created_date;
	}

	public void setDate(Date date) {
		this.created_date = date;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("Author { ")
				.append("id=" + id)
				.append(", title=" + title)
				.append(", label=" + label)
				.append(", text=" + text)
				.append(", date=" + created_date.toString())
				.append(", authorId=" + authorId)
				.append('}')
				.toString();
	}
	
}
