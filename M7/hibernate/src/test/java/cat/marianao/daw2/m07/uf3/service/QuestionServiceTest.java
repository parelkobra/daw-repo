package cat.marianao.daw2.m07.uf3.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cat.marianao.daw2.m07.uf3.config.ServicesTestConfig;
import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.Question;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.repository.QuestionDAO;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;
import cat.marianao.daw2.m07.uf3.utils.Mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServicesTestConfig.class })
public class QuestionServiceTest {
    @Autowired
    private UserDAO userDAOMock;

    @Autowired
    private QuestionDAO questionDAOMock;

    @Autowired
    private QuestionService questionService;

    @Test
    public void getAllQuestionsOk() {
	/* Setup */
	Question question0 = Mock.getQuestion0();
	question0.setQuestionId(1);

	Question question1 = Mock.getQuestion1();
	question1.setQuestionId(2);

	Integer userId = 1;
	User user = Mock.getUser0();
	user.setUserId(userId);
	Set<Question> questions = new HashSet<>();
	questions.add(question0);
	questions.add(question1);
	user.setQuestions(questions);

	when(userDAOMock.getById(userId)).thenReturn(user);

	/* Test */
	Set<Question> questionsFromDb = questionService.getAllQuestions(userId);

	/* Verifiation */
	verify(userDAOMock, timeout(1)).getById(userId);
	assertEquals(2, questionsFromDb.size());
	assertTrue(questionsFromDb.contains(question0));
	assertTrue(questionsFromDb.contains(question1));
	verify(userDAOMock, times(1)).getById(userId);
    }

    @Test
    public void hasUserAnsweredTheQuestionOk() {
	/* Setup */
	Answer answer = Mock.getAnswer(1, "This the first answer");

	Integer questionId = 1;
	Question question = Mock.getQuestion0();
	question.setQuestionId(questionId);

	Integer userId = 1;
	User user = Mock.getUser0();
	user.setUserId(userId);
	userId = 2;
	User answerUser = Mock.getUser(userId, "Hannah");

	Set<Answer> answers = new HashSet<>();
	answers.add(answer);
	Set<Question> questions = new HashSet<>();
	questions.add(question);

	answer.setUser(answerUser);
	answer.setQuestion(question);
	question.setUser(user);
	question.setAnswers(answers);
	user.setQuestions(questions);

	when(questionDAOMock.getById(questionId)).thenReturn(question);
	when(userDAOMock.findAnswers(userId)).thenReturn(answers);

	/* Test */
	Boolean hasUserAnsweredTheQuestion = questionService.hasUserAnsweredTheQuestion(questionId, userId);

	/* Verification */
	verify(questionDAOMock, timeout(1)).getById(questionId);
	assertTrue(hasUserAnsweredTheQuestion);
	verify(userDAOMock, timeout(1)).findAnswers(userId);
    }

    @Test
    public void hasUserAnsweredTheQuestionNotOk() {
	/* Setup */
	Integer questionId = 1;
	Question question = Mock.getQuestion0();
	question.setQuestionId(questionId);

	Integer userId = 1;
	User user = Mock.getUser0();
	user.setUserId(userId);

	Set<Answer> answers = new HashSet<>();
	Set<Question> questions = new HashSet<>();
	questions.add(question);

	question.setUser(user);
	question.setAnswers(answers);
	user.setQuestions(questions);

	/* Test */
	Boolean hasUserAnsweredTheQuestion = questionService.hasUserAnsweredTheQuestion(questionId, userId);

	/* Verification */
	verify(questionDAOMock, times(2)).getById(questionId);
	assertFalse(hasUserAnsweredTheQuestion);
	verify(userDAOMock, times(1)).findAnswers(userId);
    }
}