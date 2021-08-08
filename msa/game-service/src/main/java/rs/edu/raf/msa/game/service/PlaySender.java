package rs.edu.raf.msa.game.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import rs.edu.raf.msa.game.entity.Game;
import rs.edu.raf.msa.game.entity.Play;
import rs.edu.raf.msa.game.message.GameScore;
import rs.edu.raf.msa.game.message.PlayerScore;
import rs.edu.raf.msa.game.repository.GameRepository;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(
        value = "game-service.component.include",
        havingValue = "true",
        matchIfMissing = true
)
public class PlaySender {

    final RabbitTemplate rabbitTemplate;
    final GameRepository gameRepository;

    public void sendGameScore(Play p) {
        Game game = gameRepository.findById(p.getGameId())
                .orElseThrow(() -> new IllegalStateException("Game of play not found, gameId=" + p.getGameId()));

        GameScore gs = GameScore.builder()
                .gameId(p.getGameId())
                .homeTeam(p.getHomeTeam())
                .visitorTeam(p.getAwayTeam())
                .homeScore(p.getHomeScore())
                .visitorScore(p.getAwayScore())
                .gameTime(p.getTime())
                .quarter(p.getQuarterNo())
                .build();

        rabbitTemplate.convertAndSend("game-score", null, gs);
    }

    public void sendPlayerScore(Play p) {
        PlayerScore ps = PlayerScore.builder()
                .gameId(p.getGameId())
                .build();

        rabbitTemplate.convertAndSend("player-score", null, ps);
    }

}
