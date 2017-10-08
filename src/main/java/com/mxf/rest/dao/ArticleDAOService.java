package com.mxf.rest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.mxf.rest.entity.Article;

@Repository
@ComponentScan
public class ArticleDAOService implements IBaseDAOService<Article> {

	private static final Logger LOGGER = Logger.getLogger(ArticleDAOService.class);
	
	@Value("${jdbc.url}") private String DB_URL;
	@Value("${jdbc.username}") private String DB_USER;
	@Value("${jdbc.password}") private String DB_PASSWORD;
	
	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String LABEL = "label";
	private static final String TEXT = "text";
	private static final String DATE = "date";
	private static final String AUTHOR_ID = "author_id";
	
	private static final String DB_TABLE = "Article";
	
	private static final String QUERY_SELECT_ALL = (new StringBuilder())
			.append("SELECT ")
			.append(IBaseDAOService.implode(", ", ID, TITLE, LABEL, TEXT, DATE, AUTHOR_ID))
			.append(" FROM ")
			.append(DB_TABLE)
			.append(';')
			.toString();
	
	private static final String QUERY_SELECT_BY_ID = (new StringBuilder())
			.append("SELECT ")
			.append(IBaseDAOService.implode(", ", ID, TITLE, LABEL, TEXT, DATE, AUTHOR_ID))
			.append(" FROM ")
			.append(DB_TABLE)
			.append(" WHERE " + ID + "=?")
			.append(';')
			.toString();
	
	private static final String QUERY_UPDATE = (new StringBuilder())
			.append("UPDATE ")
			.append(DB_TABLE)
			.append(" SET ")
			.append(TITLE + "=?, ")
			.append(LABEL + "=?, ")
			.append(TEXT + "=?, ")
			.append(DATE + "=?, ")
			.append(AUTHOR_ID + "=?")
			.append(" WHERE " + ID + "=?")
			.append(';')
			.toString();
	
	private static final String QUERY_CREATE = (new StringBuilder())
			.append("INSERT INTO ")
			.append(DB_TABLE)
			.append('(').append(IBaseDAOService.implode(", ", TITLE, LABEL, TEXT, DATE, AUTHOR_ID)).append(')')
			.append(" VALUES (?, ?, ?, ?, ?)")
			.toString();
	
	private static final String QUERY_DELETE = (new StringBuilder())
			.append("DELETE FROM ")
			.append(DB_TABLE)
			.append(" WHERE " + ID + "=?;")
			.toString();
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
	
	@Override
	public Article buildObject(ResultSet resultSet) throws SQLException {
		return new Article(
				resultSet.getInt(ID),
				resultSet.getString(TITLE),
				resultSet.getString(LABEL),
				resultSet.getString(TEXT),
				resultSet.getDate(DATE),
				resultSet.getInt(AUTHOR_ID)
			);
	}
	
	@Override
	public List<Article> selectAll() {
		List<Article> result = new ArrayList<>();
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_ALL);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				result.add(buildObject(resultSet));
			}
			
		} catch(SQLException | RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		return result;
	}

	@Override
	public Article select(Integer id) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				return buildObject(resultSet);
			}
			else {
				return null;
			}
		} catch(SQLException | RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Integer create(Article entity) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getTitle());
			preparedStatement.setString(2, entity.getLabel());
			preparedStatement.setString(3, entity.getText());
			preparedStatement.setDate(4, entity.getDate());
			preparedStatement.setInt(5, entity.getAuthorId());
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			
			if(resultSet.next()) {
				return resultSet.getInt(1);
			}
			else {
				return null;
			}
			
		} catch(SQLException | RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Integer update(Article entity) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
			preparedStatement.setString(1, entity.getTitle());
			preparedStatement.setString(2, entity.getLabel());
			preparedStatement.setString(3, entity.getText());
			preparedStatement.setDate(4, entity.getDate());
			preparedStatement.setInt(5, entity.getAuthorId());
			preparedStatement.setInt(6, entity.getId());
			preparedStatement.execute();
			
			return entity.getId();
			
		} catch(SQLException | RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			
			return id;
			
		} catch(SQLException | RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

}
