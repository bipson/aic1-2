package db;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import db.interfaces.IEntity;
import db.interfaces.IEntityDAO;

public class GenericDAO<EntityType extends IEntity<EntityKeyType>, EntityKeyType extends Serializable>
		implements IEntityDAO<EntityType, EntityKeyType> {

	private static EntityManager em;
	private static EntityManagerFactory emf;

	private Class<EntityType> entityClass;

	public GenericDAO(Class<EntityType> entityClass) {
		this.entityClass = entityClass;
	}

	public static void init(String persistenceUnitName) {
		if (emf == null || !emf.isOpen()) {
			GenericDAO.emf = Persistence
					.createEntityManagerFactory(persistenceUnitName);
		}
	}

	public static EntityManager getEntityManager() {
		if (emf == null) {
			throw new RuntimeException(
					"EntitmanagerFactory not initialized (call init before using)!");
		}
		if (em == null || !em.isOpen()) {
			GenericDAO.em = GenericDAO.emf.createEntityManager();
		}
		return GenericDAO.em;
	}

	@Override
	public void delete(EntityType entity) {
		EntityManager em = GenericDAO.getEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			rollBackTransaction();
			throw e;
		}
	}

	@Override
	public EntityType get(EntityKeyType key) {
		EntityManager em = GenericDAO.getEntityManager();
		return em.find(entityClass, key);
	}

	@Override
	public void persist(EntityType entity) {
		EntityManager em = GenericDAO.getEntityManager();
		em.getTransaction().begin();
		try {
			em.persist(entity);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			rollBackTransaction();
			throw e;
		}
	}

	@Override
	public EntityType update(EntityType entity) {
		EntityType merged;
		EntityManager em = GenericDAO.getEntityManager();

		em.getTransaction().begin();
		try {
			merged = em.merge(entity);
			em.getTransaction().commit();
			return merged;
		} catch (RuntimeException e) {
			rollBackTransaction();
			throw e;
		}
	}

	@Override
	public void refresh(EntityType entity) {
		EntityManager em = GenericDAO.getEntityManager();
		try {
			em.refresh(entity);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	private void rollBackTransaction() {
		if (em.getTransaction().isActive())
			em.getTransaction().rollback();
	}

	public static void shutdown() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}

	public static void close_emf() {
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}
