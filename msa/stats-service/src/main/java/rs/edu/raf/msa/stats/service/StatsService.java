package rs.edu.raf.msa.stats.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rs.edu.raf.msa.stats.config.MessagingConfig;
import rs.edu.raf.msa.stats.controller.GameEventController;
import rs.edu.raf.msa.stats.message.GameScore;
import rs.edu.raf.msa.stats.message.PlayerScore;

import java.util.HashMap;

@Service
@Slf4j
public class StatsService {

	private final HashMap<Long, Double> homeLastTimeScore = new HashMap<>();
	private final HashMap<Long, Double> awayLastTimeScore = new HashMap<>();

	private final HashMap<Long, Integer> homeScore = new HashMap<>();
	private final HashMap<Long, Integer> awayScore = new HashMap<>();

	private final HashMap<Long, Double> latestTime = new HashMap<>();

	private GameEventController gameEventController;

	@Autowired
	public StatsService(GameEventController gameEventController)
	{
		this.gameEventController = gameEventController;
	}

	/**
	 * <pre>
	 * { "gameId": 4, "homeTeam": "LAL", "visitorTeam": "DEN", "quarter": 4, "gameTime":"38:13", "homeScore": "87", "visitorScore": "98" }
	 * </pre>
	 */
	@RabbitListener(queues = MessagingConfig.GAME_SCORE_RUN)
	public void gameScoreRun(Message<GameScore> message) {
		log.info("gameScoreRun(): {}", message);
	}

	private static double convertTimeToSeconds(String mmss, int quarter)
	{
		String[] time = mmss.split(":");

		double quarterTime = 12 * 60.0 - (Double.parseDouble(time[0]) * 60 + Double.parseDouble(time[1]));

		return (quarter - 1) * 12 * 60.0 + quarterTime;
	}

	@RabbitListener(queues = MessagingConfig.GAME_SCORE_DROUGHT)
	public void gameScoreDrought(Message<GameScore> message) {
		log.info("gameScoreDrought(): {}", message);
		final int THRESHOLD = 80; // in seconds

		GameScore gs = message.getPayload();
		double latestTimeInSec = latestTime.getOrDefault(gs.getGameId(), 0.0);
		double receivedTimeInSec = convertTimeToSeconds(gs.getGameTime(), gs.getQuarter());
		if (latestTimeInSec > receivedTimeInSec)
			return;

		latestTime.put(gs.getGameId(), receivedTimeInSec);
		int homePreviousScore = homeScore.getOrDefault(gs.getGameId(), 0);
		int awayPreviousScore = awayScore.getOrDefault(gs.getGameId(), 0);

		if (homePreviousScore == gs.getHomeScore())
		{
			if (receivedTimeInSec - homeLastTimeScore.getOrDefault(gs.getGameId(), 0.0) >= THRESHOLD)
			{
				String event = String.format("[%s] has a drought of %f second(s) without scoring", gs.getHomeTeam(),
						receivedTimeInSec - homeLastTimeScore.getOrDefault(gs.getGameId(), 0.0));
				log.info(event);

				gameEventController.insertEvent(event);
			}
		}else {
			homeLastTimeScore.put(gs.getGameId(), convertTimeToSeconds(gs.getGameTime(), gs.getQuarter()));
			homeScore.put(gs.getGameId(), gs.getHomeScore());
		}


		if (awayPreviousScore == gs.getVisitorScore())
		{
			if (receivedTimeInSec - awayLastTimeScore.getOrDefault(gs.getGameId(), 0.0) >= THRESHOLD)
			{
				String event = String.format("[%s] has a drought of %f second(s) without scoring", gs.getVisitorTeam(),
						receivedTimeInSec - awayLastTimeScore.getOrDefault(gs.getGameId(), 0.0));
				log.info(event);
				gameEventController.insertEvent(event);
			}
		}else {
			awayLastTimeScore.put(gs.getGameId(), convertTimeToSeconds(gs.getGameTime(), gs.getQuarter()));
			awayScore.put(gs.getGameId(), gs.getHomeScore());
		}
	}

	@RabbitListener(queues = MessagingConfig.PLAYER_SCORE)
	public void playerScoreChanged(Message<PlayerScore> message) {
		log.info("playerScoreChanged(): {}", message);
	}

}
