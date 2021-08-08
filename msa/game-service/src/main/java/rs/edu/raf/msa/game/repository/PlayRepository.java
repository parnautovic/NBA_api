package rs.edu.raf.msa.game.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import rs.edu.raf.msa.game.entity.Play;

public interface PlayRepository extends PagingAndSortingRepository<Play, Long> {

	//
	List<Play> findByGameId(Long id, Sort sort);

}
