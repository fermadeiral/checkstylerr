package com.ciandt.techgallery.persistence.dao;

import com.googlecode.objectify.Key;

import java.io.Serializable;
import java.util.List;


/**
 * GenericDAO methods.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public interface GenericDAO<T, ID extends Serializable> {

  /**
   * Method that return a list with all Entities.
   *
   * @return list of user entity.
   */
  List<T> findAll();

  /**
   * Method that return a list with all Entities actives.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 26/10/2015
   *
   * @return list of entities actives.
   */
  List<T> findAllActives();

  /**
   * Method that return a Entity by its Id/Name.
   *
   * @param id entity.
   * @return entity.
   */
  T findById(ID id);

  /**
   * Method that finds an entity by one of its properties.
   * 
   * @param property property name.
   * @param value property value.
   * @return entity.
   */
  T findByProperty(String property, Object value);
  
  /**
   * Method that adds a new entity.
   *
   * @param entity.
   * @return key generated by datastore.
   */
  Key<T> add(T entity);

  /**
   * Method that updates a entity.
   *
   * @param entity.
   * @return success or failure.
   */
  boolean update(T entity);

  /**
   * Method that deletes a entity.
   *
   * @param entity.
   * @return success or failure.
   */
  boolean delete(T entity);

}
