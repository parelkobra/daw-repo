package cat.marianao.daw2.m07.uf3.repository;

import cat.marianao.daw2.m07.uf3.domain.Answer;

public interface AnswerDAO {
    Answer getById(Integer answerId);
    void save(Answer answer);
}
