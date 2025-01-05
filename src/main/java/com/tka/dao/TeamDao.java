package com.tka.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Team;

@Repository
public class TeamDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Team save(Team team) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(team); // Use save instead of saveOrUpdate
		session.getTransaction().commit(); // Commit the transaction
		return team;
	}

	public Team findById(Long id) {
		Session session = sessionFactory.openSession();
		Team team = session.get(Team.class, id);

		if (team != null) {
			return team; // Return the team if found
		} else {
			throw new RuntimeException("Team with ID: " + id + " not found"); // Return a message if not found
		}
	}

	public List<Team> findAll() {
		Session session = sessionFactory.openSession();
		String hql = "FROM Team ORDER BY id ASC"; // HQL query to fetch teams ordered by id
		Query<Team> query = session.createQuery(hql, Team.class);
		return query.list();
	}
}
