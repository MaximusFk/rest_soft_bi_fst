package com.mxf.rest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.log4j.Logger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.mxf.rest.entity.Author;

@Repository
@ComponentScan
public class AuthorDAOService implements IBaseDAOService<Author> {
	
	private static final Logger LOGGER = Logger.getLogger(AuthorDAOService.class);
	
	@Value("${jdbc.url}") private String DB_URL;
	@Value("${jdbc.username}") private String DB_USER;
	@Value("${jdbc.password}") private String DB_PASSWORD;
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String EMAIL = "email";
	
	private static final String DB_TABLE = "Author";
	
	private static final String QUERY_SELECT_ALL = (new StringBuilder())
			.append("SELECT ")
			.append(IBaseDAOService.implode(", ", ID, NAME, EMAIL))
			.append(" FROM ")
			.append(DB_TABLE)
			.append(';')
			.toString();
	
	private static final String QUERY_SELECT_BY_ID = (new StringBuilder())
			.append("SELECT ")
			.append(IBaseDAOService.implode(", ", ID, NAME, EMAIL))
			.append(" FROM ")
			.append(DB_TABLE)
			.append(" WHERE " + ID + "=?")
			.append(';')
			.toString();
	
	private static final String QUERY_UPDATE = (new StringBuilder())
			.append("UPDATE ")
			.append(DB_TABLE)
			.append(" SET ")
			.append(NAME + "=?, ")
			.append(EMAIL + "=?")
			.append(" WHERE " + ID + "=?")
			.append(';')
			.toString();
	
	private static final String QUERY_CREATE = (new StringBuilder())
			.append("INSERT INTO ")
			.append(DB_TABLE)
			.append('(').append(IBaseDAOService.implode(", ", NAME, EMAIL)).append(')')
			.append(" VALUES (?, ?)")
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
	public Author buildObject(ResultSet resultSet) throws SQLException {
		return new Author(
					resultSet.getInt(ID),
					resultSet.getString(NAME),
					resultSet.getString(EMAIL)
				);
	}
	
	@Override
	public List<Author> selectAll() {
		List<Author> result = new ArrayList<>();
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
	public Author select(Integer id) {
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
	public Integer create(Author entity) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setString(2, entity.getEmail());
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
	public Integer update(Author entity) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setString(2, entity.getEmail());
			preparedStatement.setInt(3, entity.getId());
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
