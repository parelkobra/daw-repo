package cat.marianao.daw2.m07.uf3.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cat.marianao.daw2.m07.uf3.config.HibernateH2DatabaseConfig;
import cat.marianao.daw2.m07.uf3.config.ServicesConfig;
import cat.marianao.daw2.m07.uf3.domain.Question;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.utils.Mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServicesConfig.class, HibernateH2DatabaseConfig.class })
public class QuestionDAOTest {
    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void saveOk() {
	/* Setup */
	Timestamp currentTime = new Timestamp(new Date().getTime());

	String username = "john";
	User user = Mock.getUser(null, username);
	user.setCreatedOn(currentTime);

	userDAO.save(user);

	Question question = new Question();
	question.setText("This is a question");
	question.setUser(user);

	/* Previous verification */
	assertNull(question.getQuestionId());

	/* Test */
	questionDAO.save(question);

	/* Verification */
	assertNotNull(question.getQuestionId());

	Question questionFromDb = questionDAO.getById(question.getQuestionId());

	assertEquals(question.getQuestionId(), questionFromDb.getQuestionId());
	assertEquals(username, questionFromDb.getUser().getUsername());
	assertEquals(currentTime, questionFromDb.getUser().getCreatedOn());
    }
}