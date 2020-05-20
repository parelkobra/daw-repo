package cat.marianao.daw2.m07.uf3.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cat.marianao.daw2.m07.uf3.config.HibernateH2DatabaseConfig;
import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.Question;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.utils.Mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateH2DatabaseConfig.class })
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Test
    @Transactional
    public void createOk() {
	/* Setup */
	Timestamp currentTime = new Timestamp(new Date().getTime());

	String username = "john";
	User user = Mock.getUser(null, username);
	user.setCreatedOn(currentTime);

	/* Previous verification - There is no user with such username */
	assertNull(user.getUserId());

	User userFromDb = userDAO.findUserByUsername(username);
	assertNull(userFromDb);

	/* Test method */
	userDAO.save(user);

	/* Verification */
	userFromDb = userDAO.findUserByUsername(username);
	assertNotNull(user.getUserId());
	assertEquals(user.getUserId(), userFromDb.getUserId());
	assertEquals(currentTime, userFromDb.getCreatedOn());
    }

    @Test
    @Transactional
    public void findAnswers() {
	/* Setup */
	User user = Mock.getUser(1, "John");

	Integer responseUserId = 2;
	User responseUser = Mock.getUser(responseUserId, "Steve");

	Answer answer = Mock.getAnswer(1, "Answer number 1");

	Question question = Mock.getQuestion(1, "Test question");

	Set<Answer> answers = new HashSet<>();
	answers.add(answer);
	Set<Question> questions = new HashSet<>();
	questions.add(question);

	answer.setUser(responseUser);
	answer.setQuestion(question);
	question.setUser(user);
	question.setAnswers(answers);
	user.setQuestions(questions);
	responseUser.setAnswers(answers);

	userDAO.save(user);
	userDAO.save(responseUser);

	/* Test method */
	Set<Answer> userAnswers = userDAO.findAnswers(responseUserId);

	/* Verification */
	assertNotNull(userAnswers);
	assertEquals(1, userAnswers.size());
    }
}