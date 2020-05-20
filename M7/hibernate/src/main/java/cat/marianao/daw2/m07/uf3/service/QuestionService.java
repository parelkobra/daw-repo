package cat.marianao.daw2.m07.uf3.service;

import java.util.Set;

import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.Question;

public interface QuestionService {
    Set<Question> getAllQuestions(Integer userId);

    Question answerQuestion(Answer answer, Integer questionId);

    void create(Question question);

    Boolean hasUserAnsweredTheQuestion(Integer questionId, Integer userId);
}