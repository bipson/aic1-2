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
		GenericDAO.emf = Persistence
				.createEntityManagerFactory(persistenceUnitName);
		GenericDAO.em = GenericDAO.emf.createEntityManager();
	}

	public static EntityManager getEntityManager() {
		return GenericDAO.em;
	}

	@Override
	public void delete(EntityType entity) {
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
		return em.find(entityClass, key);
	}

	@Override
	public void persist(EntityType entity) {
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
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}
