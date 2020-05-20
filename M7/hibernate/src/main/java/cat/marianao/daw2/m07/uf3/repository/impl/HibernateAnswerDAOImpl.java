package cat.marianao.daw2.m07.uf3.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.repository.AnswerDAO;

import javax.transaction.Transactional;

@Transactional
@Repository("answerHibernateDAO")
public class HibernateAnswerDAOImpl implements AnswerDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Answer getById(Integer questionId) {
        return getSession().get(Answer.class, questionId);
    }

    @Override
    public void save(Answer answer) {
        getSession().saveOrUpdate(answer);
    }


    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
