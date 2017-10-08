package com.mxf.rest.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mxf.rest.entity.ABaseEntity;

/**
 * This interface defines methods for manipulation data in database
 * @author maximusfk
 *
 * @param <Entity>
 */
public interface IBaseDAOService <Entity extends ABaseEntity> {
	
	/**
	 * Build Entity object from ResultSet
	 * @param resultSet
	 * @return builded Entity
	 * @throws SQLException 
	 */
	Entity buildObject(ResultSet resultSet) throws SQLException;

	/**
	 * This method return all entities for represent table of database
	 * @return List all entities in database
	 */
	List<Entity> selectAll();
	
	/**
	 * This method looking for entity in database by ID
	 * @param id Entity ID
	 * @return Entity object if founded, else null
	 */
	Entity select(Integer id);
	
	/**
	 * This method create new instance of Entity in database
	 * @param entity
	 * @return id of created instance
	 */
	Integer create(Entity entity);
	
	/**
	 * This method update information of an exiting Entity
	 * @param entity
	 * @return id of updated instance, null if does not exist in database
	 */
	Integer update(Entity entity);
	
	/**
	 * This method remove exiting Entity from database
	 * @param id
	 * @return id of deleted instance, null if does not exist in database
	 */
	Integer delete(Integer id);
	
	public static String implode(String glue, String ...strings) {
		return String.join(glue, strings);
	}
	
}
