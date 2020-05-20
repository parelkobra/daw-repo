package cat.marianao.daw2.m07.uf3.repository.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;

@Transactional
@Repository("userHibernateDAO")
public class HibernateUserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getById(Integer userId) {
	return getSession().get(User.class, userId);
    }

    @Override
    public void save(User user) {
	getSession().saveOrUpdate(user);
    }

    @Override
    public User edit(User user) {
	return (User) getSession().merge(user);
    }

    @Override
    public void remove(User user) {
	getSession().delete(user);
    }

    @Override
    public User findUserByUsername(String username) {
	Criteria criteria = createEntityCriteria();
	criteria.add(Restrictions.eq("username", username));
	return (User) criteria.uniqueResult();
    }

    @Override
    public User findUserWithHighestRank() {
	Criteria criteria = createEntityCriteria();
	criteria.addOrder(Order.desc("rank"));
	return (User) criteria.uniqueResult();
    }

    /**
     * Returns all the answers from the user with id {@code userId}
     *
     * @param userId
     */
    @Override
    public Set<Answer> findAnswers(Integer userId) {
	return getById(userId).getAnswers();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findActiveUsers() {
	Criteria criteria = createEntityCriteria();
	criteria.add(Restrictions.eq("active", true));
	return criteria.list();
    }

    protected Session getSession() {
	return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("deprecation")
    private Criteria createEntityCriteria() {
	return getSession().createCriteria(User.class);
    }

}