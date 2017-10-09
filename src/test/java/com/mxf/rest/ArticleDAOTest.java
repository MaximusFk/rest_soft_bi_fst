package com.mxf.rest;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxf.rest.dao.ArticleDAOService;
import com.mxf.rest.entity.Article;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan
@EnableAutoConfiguration
public class ArticleDAOTest {

	@Autowired
	private ArticleDAOService service;

	@Test
	public void testSelectAll() {
		Article entity = new Article(0, "Title", "Label", "Test simple text", new Date(System.currentTimeMillis()), 2);
		Integer id;
		assertNotNull((id = service.create(entity)));
		List<Article> list = service.selectAll();
		assertFalse(list.isEmpty());
		assertNotNull(service.delete(id));
	}

	@Test
	public void testSelect() {
		Article entity = new Article(0, "Title", "Label", "Test simple text", new Date(System.currentTimeMillis()), 2);
		Integer id;
		assertNotNull((id = service.create(entity)));
		assertNotNull(service.select(id));
		assertNotNull(service.delete(id));
	}

	@Test
	public void testUpdate() {
		Article entity = new Article(0, "Title", "Label", "Test simple text", new Date(System.currentTimeMillis()), 2);
		Integer id;
		assertNotNull((id = service.create(entity)));
		entity = service.select(id);
		entity.setTitle("Title 2");
		assertNotNull(service.update(entity));
		assertNotNull(service.delete(id));
	}

}
