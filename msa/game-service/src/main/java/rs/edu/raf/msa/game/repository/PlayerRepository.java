package rs.edu.raf.msa.game.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.PagingAndSortingRepository;

import rs.edu.raf.msa.game.entity.Player;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Id> {
	Player findByExternalId(long externalId);

	boolean existsByExternalId(long externalId);
}
