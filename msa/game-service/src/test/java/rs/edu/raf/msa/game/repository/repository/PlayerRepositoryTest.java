package rs.edu.raf.msa.game.repository.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import rs.edu.raf.msa.game.entity.Game;
import rs.edu.raf.msa.game.entity.Play;
import rs.edu.raf.msa.game.entity.PlayPlayer;
import rs.edu.raf.msa.game.entity.Player;
import rs.edu.raf.msa.game.repository.GameRepository;
import rs.edu.raf.msa.game.repository.PlayRepository;
import rs.edu.raf.msa.game.repository.PlayerRepository;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@Transactional
public class PlayerRepositoryTest {
	@Autowired
    GameRepository gameRepository;

	@Autowired
    PlayerRepository playerRepository;

	@Autowired
    PlayRepository playRepository;

	@Test
	void testAll() {
		Game g = Game.builder()
				.gameId("20200101LALDEN")
				.homeTeam("LAL")
				.awayTeam("DEN")
				.gameTime(0)
				.status(Game.STATUS_ONOGING)
				.build();

		g = gameRepository.save(g);

		assertNotNull(g);
		assertThat(g.getGameId()).isEqualTo("20200101LALDEN");
	}

	@Test
	void savePlayer() {
		Player player = Player.builder()
				.firstName("Nikola")
				.lastName("Jokic")
				.externalId(2020L)
				.build();

		player = playerRepository.save(player);
		assertNotNull(player);
	}

	@Test
	void savePlay() {
		Game g = Game.builder()
				.gameId("20200101LALDEN")
				.homeTeam("LAL")
				.awayTeam("DEN")
				.gameTime(0)
				.status(Game.STATUS_ONOGING)
				.build();
		g = gameRepository.save(g);
		assertNotNull(g);

		Player player = Player.builder()
				.firstName("Nikola")
				.lastName("Jokic")
				.externalId(2020L)
				.build();
		player = playerRepository.save(player);
		assertNotNull(player);


		Play play = Play.builder()
				.team("LAL")
				.description("Test")
				.gameId(g.getId())
				.quarterNo(1)
				.homeTeam("LAL")
				.awayTeam("DEN")
				.homeScore(0)
				.awayScore(0)
				.time("00:00")
				.build();

		play.addPlayer(player);
		play = playRepository.save(play);
		assertNotNull(play);
		assertThat(play.getPlayers().stream().map(PlayPlayer::getPlayerId).toArray()).contains(player.getPlayerId());
	}

	@Test
	void findByExternalId() {
		Player player = Player.builder()
				.firstName("Nikola")
				.lastName("Jokic")
				.externalId(2020L)
				.build();

		player = playerRepository.save(player);
		player = playerRepository.findByExternalId(2020L);

		assertNotNull(player);
		assertThat(player.getFirstName()).isEqualTo("Nikola");
		assertThat(player.getLastName()).isEqualTo("Jokic");
	}
}
