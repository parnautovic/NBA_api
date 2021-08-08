package rs.edu.raf.msa.game.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.PagingAndSortingRepository;

import rs.edu.raf.msa.game.entity.Game;

import java.sql.Date;
import java.util.Optional;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    Optional<Game> findByGameId(String gameId);
}
