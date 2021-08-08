package rs.edu.raf.msa.pbp.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import rs.edu.raf.msa.pbp.model.Play;
import rs.edu.raf.msa.pbp.model.PlayByPlay;
import rs.edu.raf.msa.pbp.model.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
class GameControllerTest {

	@Autowired
	GameController gameController;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void gameLoaded() throws IOException {
		PlayByPlay pbp = gameController.game("20200924LALDEN");
		assertNotNull(pbp);

		String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pbp);
		log.debug(formattedJson);
	}

	@Test
	void gamesLoaded() throws IOException {
		List<String> games = gameController.games();
		assertNotNull(games);

		log.debug("{}", games);
		assertThat(games).contains("20200924LALDEN", "20200930MIALAL", "20201002MIALAL");
	}


	@Test
	void players() throws IOException
	{
		List<Player> players = gameController.players("20200924LALDEN");

		assertNotNull(players);

		log.debug("{}", players);

		Player rondo = new Player();
		Player jokic = new Player();

		rondo.setPlayerId("rajon_rondo");
		rondo.setFirstName("Rajon");
		rondo.setLastName("Rondo");
		rondo.setExternalId(200765L);

		jokic.setPlayerId("nikola_jokic");
		jokic.setFirstName("Nikola");
		jokic.setLastName("Jokic");
		jokic.setExternalId(203999L);

		assertThat(players).contains(jokic, rondo);
	}

	@Test
	public void plays() throws IOException
	{
		List<Play> highlights = gameController.plays("20200924LALDEN", "00:00", "12:00");

 		assertNotNull(highlights);

 		Play play = new Play();
 		play.setTime("03:12");
		play.setDescription("[LAL] Kuzma Driving Floating Jump Shot: Missed");
		play.setTeam("LAL");
		play.setPlayers(Arrays.asList(1628398L));

 		assertThat(highlights).anyMatch(p -> p.getDescription().equals(play.getDescription()));
	}

}
