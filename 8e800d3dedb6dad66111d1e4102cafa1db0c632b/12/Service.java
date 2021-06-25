package com.ciandt.techgallery.sample.service;

import java.io.Serializable;
import java.util.List;

import com.ciandt.techgallery.sample.persistence.model.SampleBaseEntity;
import com.ciandt.techgallery.sample.service.model.Response;
import com.googlecode.objectify.Key;

/**
 * Service Interface.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface Service<R extends Response, T extends SampleBaseEntity, ID extends Serializable> {
  
  /**
   * Method that return the List of Entities.
   * 
   * @return list of samples.
   */
  public List<T> findAll();
  
  /**
   * Method that add Entity into datastore.
   * 
   * @param entity to be persisted.
   * @return key generated by datastore.
   */
  public Key<T> add(T entity);
  
  public List<Response> listAll();

  public Response add(R responseEntity);
}
