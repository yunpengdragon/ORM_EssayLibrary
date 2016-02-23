package com.library.essay.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.library.essay.persistence.entities.Essay;

public class EssayServiceImp implements EssayService {

	@Override
	public Essay getEssay(long id) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
		EntityManager entityManager = factory.createEntityManager();

		Essay essay = entityManager.find(Essay.class, id);

		if (entityManager != null) {
			entityManager.close();
		}
		if (factory != null) {
			factory.close();
		}

		return essay;
	}

	@Override
	public List<Essay> getEssays() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
		EntityManager entityManager = factory.createEntityManager();

		TypedQuery<Essay> query = entityManager.createQuery("select e from Essay e", Essay.class);

		List<Essay> resultList = query.getResultList();

		if (entityManager != null) {
			entityManager.close();
		}
		if (factory != null) {
			factory.close();
		}

		return resultList;
	}

	@Override
	public List<Essay> getEssaysCriteriaQuery() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
		EntityManager entityManager = factory.createEntityManager();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Query for a List of objects.
		CriteriaQuery<Essay> criteriaQuery = criteriaBuilder.createQuery(Essay.class);
		Root<Essay> essayRoot = criteriaQuery.from(Essay.class);

		criteriaQuery.select(essayRoot).where(criteriaBuilder.greaterThan(essayRoot.get("id"), 1))
				.orderBy(criteriaBuilder.desc(essayRoot.get("id")));

		Query query = entityManager.createQuery(criteriaQuery);
		List<Essay> resultList = query.getResultList();

		if (entityManager != null) {
			entityManager.close();
		}
		if (factory != null) {
			factory.close();
		}

		return resultList;
	}

	@Override
	public Essay saveOrUpdate(Essay essay) {

		Essay savedEssay = null;

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
		EntityManager entityManager = factory.createEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

			Long essayId = essay.getId();
			if (essayId == null || essayId <= 0) {
				entityManager.persist(essay);
				savedEssay = essay;
			} else {
				savedEssay = entityManager.merge(essay);
			}

			transaction.commit();
		} catch (Exception e) {

			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

		if (entityManager != null) {
			entityManager.close();
		}
		if (factory != null) {
			factory.close();
		}
		return savedEssay;
	}

	@Override
	public void delete(Essay essay) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
		EntityManager entityManager = factory.createEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

			Long essayId = essay.getId();

			if (essayId > 0) {
				Essay essayToDelete = entityManager.find(Essay.class, essayId);
				entityManager.remove(essayToDelete);
			}

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();

			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

		if (entityManager != null) {
			entityManager.close();
		}
		if (factory != null) {
			factory.close();
		}

	}

	@Override
	public void deleteAll() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
		EntityManager entityManager = factory.createEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

			int deletedCount = entityManager.createQuery("delete from Essay").executeUpdate();

			transaction.commit();

			System.out.println("Total " + deletedCount + " essays have been deleted.");
		} catch (Exception e) {
			e.printStackTrace();

			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

		if (entityManager != null) {
			entityManager.close();
		}
		if (factory != null) {
			factory.close();
		}

	}

}
