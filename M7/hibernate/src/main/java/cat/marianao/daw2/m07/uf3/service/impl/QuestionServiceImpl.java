package cat.marianao.daw2.m07.uf3.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.Question;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.repository.QuestionDAO;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;
import cat.marianao.daw2.m07.uf3.service.QuestionService;

@Transactional
public class QuestionServiceImpl implements QuestionService {
    private QuestionDAO questionDAO;
    private UserDAO userDAO;

    public QuestionServiceImpl(UserDAO userDAO, QuestionDAO questionDAO) {
	this.userDAO = userDAO;
	this.questionDAO = questionDAO;
    }

    @Override
    public Set<Question> getAllQuestions(Integer userId) {
	User user = userDAO.getById(userId);
	return user.getQuestions();
    }

    @Override
    public Question answerQuestion(Answer answer, Integer questionId) {
	Question question = questionDAO.getById(questionId);
	Set<Answer> answers = question.getAnswers();
	if (answers == null) {
	    answers = new HashSet<>();
	}
	answers.add(answer);
	return questionDAO.update(question);
    }

    @Override
    public void create(Question question) {
	questionDAO.save(question);
    }

    @Override
    public Boolean hasUserAnsweredTheQuestion(Integer questionId, Integer userId) {
	Question question = questionDAO.getById(questionId);
	Set<Answer> userAnswers = userDAO.findAnswers(userId);
	for (Answer answer : userAnswers) {
	    if (question.equals(answer.getQuestion())) return true;
	}
	return false;
    }
}