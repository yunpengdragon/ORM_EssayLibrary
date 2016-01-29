package com.library.essay.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.library.essay.persistence.entities.Essay;
import com.library.essay.utils.HibernateUtil;

public class EssayServiceImp implements EssayService {

	@Override
	public Essay getEssay(long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Essay essay = (Essay) session.get(Essay.class, id);

		session.close();

		return essay;
	}

	@Override
	public List<Essay> getEssays() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Query essayQuery = session.createQuery("from Essay");
		List<Essay> essayList = essayQuery.list();

		session.close();

		return essayList;
	}

	@Override
	public Essay saveOrUpdate(Essay essay) {

		Essay savedEssay = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.saveOrUpdate(essay);
		savedEssay = essay;

		session.getTransaction().commit();
		session.close();

		return savedEssay;
	}

	@Override
	public void delete(Essay essay) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		Long essayId = essay.getId();
		Essay essayToDelete = (Essay) session.get(Essay.class, essayId);

		if (essay != null) {
			session.delete(essayToDelete);
		}
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void deleteAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.createQuery("delete from Essay").executeUpdate();

		session.getTransaction().commit();
		session.close();

	}

}
