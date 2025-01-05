package com.tka.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Matches;

@Repository
public class MatchDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Matches save(Matches matches) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(matches); // Use save instead of saveOrUpdate
		session.getTransaction().commit(); // Commit the transaction
		return matches;
	}

	public Matches findById(Long id) {
		Session session = sessionFactory.openSession();
		Matches matches = session.get(Matches.class, id);

		if (matches != null) {
			return matches; // Return the Match if found
		} else {
			throw new RuntimeException("Team with ID: " + id + " not found"); // Return a message if not found
		}
	}

	public List<Matches> getAllMatches() {
		Session session = sessionFactory.openSession();
		TypedQuery<Matches> query = session.createQuery("FROM Matches", Matches.class);
		return query.getResultList();
	}
}
