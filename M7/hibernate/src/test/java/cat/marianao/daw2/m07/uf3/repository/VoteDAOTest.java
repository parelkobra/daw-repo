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
import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.Question;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.domain.Vote;
import cat.marianao.daw2.m07.uf3.utils.Mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServicesConfig.class, HibernateH2DatabaseConfig.class })
public class VoteDAOTest {
    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private VoteDAO voteDAO;

    @Test
    public void voteTrueOk() {
	/* Setup */
	Timestamp currentTime = new Timestamp(new Date().getTime());

	/* userQ, which did the question */
	User userQ = Mock.getUser(null, "jack");
	userQ.setCreatedOn(currentTime);
	userDAO.save(userQ);

	Question question = new Question();
	question.setText("This is a question");
	question.setUser(userQ);
	questionDAO.save(question);

	/* userA, which did the answer related to the question before */
	User userA = Mock.getUser(null, "john");
	userA.setCreatedOn(currentTime);
	userDAO.save(userA);

	Answer answer = new Answer();
	answer.setText("This is an answer");
	answer.setUser(userA);
	answerDAO.save(answer);

	/* userV, which does the vote to the answer that userA did */
	String username = "wilson";
	User userV = Mock.getUser(null, username);
	userV.setCreatedOn(currentTime);
	userDAO.save(userV);

	Vote vote = new Vote();
	vote.setVote(true);
	vote.setUser(userV);
	vote.setAnswer(answer);

	/* Previous verification - There is no user with such id */
	assertNull(vote.getVoteId());

	/* Test */
	voteDAO.save(vote);

	/* Verification */
	assertNotNull(vote.getVoteId());

	Vote voteFromDb = voteDAO.getById(vote.getVoteId());

	assertEquals(vote.getVoteId(), voteFromDb.getVoteId());
	assertEquals(username, voteFromDb.getUser().getUsername());
	assertEquals(currentTime, voteFromDb.getUser().getCreatedOn());
    }
}