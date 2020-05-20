package cat.marianao.daw2.m07.uf3.service;

public interface VoteService {
    void votePositive(Integer answerId, Integer userId);
    void voteNegative(Integer answerId, Integer userId);
}
