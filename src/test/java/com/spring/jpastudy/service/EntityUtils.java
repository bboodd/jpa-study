package com.spring.jpastudy.service;

import java.util.Collection;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.spring.jpastudy.model.BaseEntity;

public abstract class EntityUtils {

	public static <T extends BaseEntity> T getById(Collection<T> entities, Class<T> entityClass, int entityId)
		throws ObjectRetrievalFailureException {
		for (T entity : entities) {
			if (entity.getId() == entityId && entityClass.isInstance(entity)) {
				return entity;
			}
		}
		throw new ObjectRetrievalFailureException(entityClass, entityId);
	}
}
