package com.mxf.rest.entity;

import java.sql.Date;

public class Article extends ABaseEntity {
	
	private int id;
	private String title;
	private String label;
	private String text;
	private Date date;
	private Integer authorId;
	
	public Article() {
		this.title = "";
		this.label = "";
		this.text = "";
	}

	public Article(int id, String title, String label, String text, Date date, Integer authorId) {
		this.id = id;
		this.title = title;
		this.label = label;
		this.text = text;
		this.date = date;
		this.authorId = authorId;
	}

	@Override
	public Integer getId() {
		return id;
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
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
				.append(", date=" + date.toString())
				.append(", authorId=" + authorId)
				.append('}')
				.toString();
	}
	
}
