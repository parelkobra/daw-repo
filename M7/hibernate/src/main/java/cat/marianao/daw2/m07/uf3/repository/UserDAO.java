package cat.marianao.daw2.m07.uf3.repository;

import java.util.List;
import java.util.Set;

import cat.marianao.daw2.m07.uf3.domain.Answer;
import cat.marianao.daw2.m07.uf3.domain.User;

public interface UserDAO {
    User getById(Integer id);

    void save(User user);

    User edit(User user);

    void remove(User user);

    User findUserByUsername(String username);

    User findUserWithHighestRank();

    List<User> findActiveUsers();

    Set<Answer> findAnswers(Integer userId);
}
