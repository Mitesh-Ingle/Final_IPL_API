package com.tka.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
//		sessionFactory.close();
		return query.getResultList();
	}

	public List<Matches> getMatchByDate(String date) {
		Session session = sessionFactory.openSession();
		List<Matches> matches = null;
		try {
			// HQL query to get matches by date
			String hql = "FROM Matches m WHERE m.matchDate = :date";

			// Create the query and set the parameter
			Query<Matches> query = session.createQuery(hql, Matches.class);
			query.setParameter("date", date); // assuming date is in format "yyyy-MM-dd"

			// Execute the query and get the results
			matches = query.list();
		} catch (Exception e) {
			e.printStackTrace(); // Log error (you can use a logger here)
		} finally {
			session.close(); // Always close the session
		}

		return matches; // Return the list of matches
	}

}
