package cat.marianao.daw2.m07.uf3.repository;

import cat.marianao.daw2.m07.uf3.domain.Question;

public interface QuestionDAO {
	Question getById(Integer questionId);

	void save(Question question);

	Question update(Question question);
}
