package cat.marianao.daw2.m07.uf3.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import cat.marianao.daw2.m07.uf3.repository.AnswerDAO;
import cat.marianao.daw2.m07.uf3.repository.QuestionDAO;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;
import cat.marianao.daw2.m07.uf3.repository.VoteDAO;

/**
 * Provides Mock DAO classes to the Service Tests
 *
 * @author Toni
 *
 */
@Configuration
@Import(value = { ServicesConfig.class })
public class ServicesTestConfig {
	@Bean
	public UserDAO userDAO() {
		return Mockito.mock(UserDAO.class);
	}

	@Bean
	public QuestionDAO questionDAO() {
		return Mockito.mock(QuestionDAO.class);
	}

	@Bean
	public AnswerDAO answerDAO() {
		return Mockito.mock(AnswerDAO.class);
	}

	@Bean
	public VoteDAO voteDAO() {
		return Mockito.mock(VoteDAO.class);
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return Mockito.mock(PlatformTransactionManager.class);
	}

}