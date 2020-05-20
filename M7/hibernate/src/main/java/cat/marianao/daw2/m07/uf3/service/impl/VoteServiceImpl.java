package cat.marianao.daw2.m07.uf3.service.impl;

import javax.transaction.Transactional;

import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.domain.Vote;
import cat.marianao.daw2.m07.uf3.repository.AnswerDAO;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;
import cat.marianao.daw2.m07.uf3.repository.VoteDAO;
import cat.marianao.daw2.m07.uf3.service.VoteService;

/*
 * A vote is made by an user over an answer of another user
 */
@Transactional
public class VoteServiceImpl implements VoteService {
	private VoteDAO voteDAO;
	private AnswerDAO answerDAO;
	private UserDAO userDAO;

	public VoteServiceImpl(VoteDAO voteDAO, AnswerDAO answerDAO, UserDAO userDAO) {
		this.voteDAO = voteDAO;
		this.answerDAO = answerDAO;
		this.userDAO = userDAO;
	}

	@Override
	public void votePositive(Integer answerId, Integer userId) {
		vote(answerId, userId, true);
	}

	@Override
	public void voteNegative(Integer answerId, Integer userId) {
		vote(answerId, userId, false);
	}

	private Vote vote(Integer userId, Integer answerId, Boolean value) {
		User user = userDAO.getById(userId);

		Answer answer = answerDAO.getById(answerId);

		Vote vote = new Vote();
		vote.setAnswer(answer);
		vote.setUser(user);
		vote.setVote(value);

		voteDAO.save(vote);

		return vote;
	}
}