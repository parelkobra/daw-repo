package cat.marianao.daw2.m07.uf3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.marianao.daw2.m07.uf3.repository.AnswerDAO;
import cat.marianao.daw2.m07.uf3.repository.QuestionDAO;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;
import cat.marianao.daw2.m07.uf3.repository.VoteDAO;
import cat.marianao.daw2.m07.uf3.service.QuestionService;
import cat.marianao.daw2.m07.uf3.service.VoteService;
import cat.marianao.daw2.m07.uf3.service.impl.QuestionServiceImpl;
import cat.marianao.daw2.m07.uf3.service.impl.UserServiceImpl;
import cat.marianao.daw2.m07.uf3.service.impl.VoteServiceImpl;

/**
 * Provides the current implementation of each service
 * 
 * @author Toni
 *
 */
@Configuration
public class ServicesConfig {
	@Bean
	public QuestionService questionService(UserDAO userDAO, QuestionDAO questionDAO) {
		return new QuestionServiceImpl(userDAO, questionDAO);
	}

	@Bean
	public UserServiceImpl userService(UserDAO userDAO) {
		return new UserServiceImpl(userDAO);
	}

	@Bean
	public VoteService voteService(VoteDAO voteDAO, AnswerDAO answerDAO, UserDAO userDAO) {
		return new VoteServiceImpl(voteDAO, answerDAO, userDAO);
	}
}
