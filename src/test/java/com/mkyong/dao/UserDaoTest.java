package com.mkyong.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.mkyong.model.User;

public class UserDaoTest {

    private EmbeddedDatabase db;

    UserDao userDao;
    
    @Before
    public void setUp() {
        //db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.addScript("db/sql/create-db.sql")
    		.addScript("db/sql/insert-data.sql")
    		.build();
    }

    @Test
    public void testFindByname() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	User user = userDao.findByName("mkyong");
  
    	Assert.assertNotNull(user);
    	Assert.assertEquals(1, user.getId().intValue());
    	Assert.assertEquals("mkyong", user.getName());
    	Assert.assertEquals("mkyong@gmail.com", user.getEmail());

    }

    @Test
    public void testFindAll() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	List<User> userList = userDao.findAll();
  
    	Assert.assertNotNull(userList);
    	Assert.assertEquals(3, userList.size() );

    }

    @Test
    public void testFindError() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);

    	User user = userDao.findByName("mkyong");
  
    	Assert.assertNotNull(user);
    	Assert.assertEquals(1, user.getId().intValue());
    	Assert.assertEquals("mkyong2", user.getName());
    	Assert.assertEquals("mkyong@gmail.com", user.getEmail());

    }

    @After
    public void tearDown() {
        db.shutdown();
    }

}