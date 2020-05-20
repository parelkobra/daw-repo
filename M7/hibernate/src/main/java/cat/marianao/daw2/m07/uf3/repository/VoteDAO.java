package cat.marianao.daw2.m07.uf3.repository;

import cat.marianao.daw2.m07.uf3.domain.Vote;

public interface VoteDAO {
	Vote getById(Integer voteId);

	void save(Vote vote);
}
