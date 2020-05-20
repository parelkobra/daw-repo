package cat.marianao.daw2.m07.uf3.repository.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.marianao.daw2.m07.uf3.domain.Vote;
import cat.marianao.daw2.m07.uf3.repository.VoteDAO;

@Transactional
@Repository("VoteHibernateDAO")
public class HibernateVoteDAOImpl implements VoteDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Vote getById(Integer voteId) {
		return getSession().get(Vote.class, voteId);
	}

	@Override
	public void save(Vote vote) {
		getSession().saveOrUpdate(vote);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
