package com.mxf.rest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxf.rest.dao.AuthorDAOService;
import com.mxf.rest.entity.Author;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan
@EnableAutoConfiguration
public class AuthorDAOTest {
	
	@Autowired
	private AuthorDAOService service;

	@Test
	public void testSelectAll() {
		Author entity = new Author(0, "Name", "Email");
		Integer id;
		assertNotNull((id = service.create(entity)));
		List<Author> list = service.selectAll();
		assertFalse(list.isEmpty());
		assertNotNull(service.delete(id));
	}

	@Test
	public void testSelect() {
		Author entity = new Author(0, "Name", "Email");
		Integer id;
		assertNotNull((id = service.create(entity)));
		assertNotNull(service.select(id));
		assertNotNull(service.delete(id));
	}

	@Test
	public void testUpdate() {
		Author entity = new Author(0, "Name", "Email");
		Integer id;
		assertNotNull((id = service.create(entity)));
		entity = service.select(id);
		entity.setName("Test 2");
		assertNotNull(service.update(entity));
		assertNotNull(service.delete(id));
	}

}
