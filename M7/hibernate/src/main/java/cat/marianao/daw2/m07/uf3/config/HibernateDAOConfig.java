package cat.marianao.daw2.m07.uf3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.marianao.daw2.m07.uf3.repository.AnswerDAO;
import cat.marianao.daw2.m07.uf3.repository.QuestionDAO;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;
import cat.marianao.daw2.m07.uf3.repository.VoteDAO;
import cat.marianao.daw2.m07.uf3.repository.impl.HibernateAnswerDAOImpl;
import cat.marianao.daw2.m07.uf3.repository.impl.HibernateQuestionDAOImpl;
import cat.marianao.daw2.m07.uf3.repository.impl.HibernateUserDAOImpl;
import cat.marianao.daw2.m07.uf3.repository.impl.HibernateVoteDAOImpl;

/**
 * Provides the current implementation for each DAO
 *
 * @author Toni
 *
 */
@Configuration
public class HibernateDAOConfig {
	@Bean
	public AnswerDAO answerDAO() {
		return new HibernateAnswerDAOImpl();
	}

	@Bean
	public UserDAO userDAO() {
		return new HibernateUserDAOImpl();
	}

	@Bean
	public QuestionDAO questionDAO() {
		return new HibernateQuestionDAOImpl();
	}

	@Bean
	public VoteDAO voteDAO() {
		return new HibernateVoteDAOImpl();
	}
}
