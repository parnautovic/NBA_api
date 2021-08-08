package rs.edu.raf.msa.game.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.edu.raf.msa.game.client.GameClient;
import rs.edu.raf.msa.game.client.dto.PlayDto;
import rs.edu.raf.msa.game.client.dto.PlayerDto;
import rs.edu.raf.msa.game.entity.Game;
import rs.edu.raf.msa.game.entity.Play;
import rs.edu.raf.msa.game.entity.PlayPlayer;
import rs.edu.raf.msa.game.entity.Player;
import rs.edu.raf.msa.game.repository.GameRepository;
import rs.edu.raf.msa.game.repository.PlayRepository;
import rs.edu.raf.msa.game.repository.PlayerRepository;

@Component
@ConditionalOnProperty(
        value = "game-service.component.include",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
@Slf4j
public class GamePlayByPlayJob {
    final GameClient gameClient;

    final GameRepository gameRepository;
    final PlayerRepository playerRepository;
    final PlayRepository playRepository;

    static final DateTimeFormatter MMSS = DateTimeFormatter.ofPattern("mm:ss");


    private void updatePlayers(Game game)
    {
        List<PlayerDto> players = gameClient.players(game.getGameId());

        for (PlayerDto playerDto : players)
        {
            if (!playerRepository.existsByExternalId(playerDto.getExternalId()))
            {
                Player player = Player.builder()
                        .externalId(playerDto.getExternalId())
                        .firstName(playerDto.getFirstName())
                        .lastName(playerDto.getLastName())
                        .build();

                playerRepository.save(player);
            }
        }
    }
    private void updateGames(List<String> games)
    {
        for (String gameId : games)
        {
            String homeTeam = gameId.substring(8, 11);
            String awayTeam = gameId.substring(11);
            Optional<Game> game = gameRepository.findByGameId(gameId);
            if (!game.isPresent())
            {
                Game gm = Game.builder()
                        .gameId(gameId)
                        .homeTeam(homeTeam)
                        .awayTeam(awayTeam)
                        .gameTime(0)
                        .status("ONGOING")
                        .build();

                game = Optional.ofNullable(gameRepository.save(gm));
            }

            updatePlayers(game.get());
        }
    }

    private void updatePlay(PlayDto playDto, Game game)
    {
        Set<Player> players = new HashSet<>();
        if (playDto.getPlayers() != null) {
            players = (playDto.getPlayers()).stream()
                    .map(playerRepository::findByExternalId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        Play play = Play.builder()
                .description(playDto.getDescription())
                .team(playDto.getTeam())
                .gameId(game.getId())
                .time(playDto.getTime())
                .quarterNo(playDto.getQuarterNo())
                .awayScore(playDto.getAwayScore())
                .homeScore(playDto.getHomeScore())
                .homeTeam(game.getHomeTeam())
                .awayTeam(game.getAwayTeam())
                .build();

        players.forEach(play::addPlayer);
        playRepository.save(play);
    }


    @Scheduled(fixedDelay = 10_000)
    @Transactional
    public void scanGames() {
        List<String> allGames = gameClient.games();
        log.info("Loaded games from pbp-service: {}", allGames);

        updateGames(allGames);
        List<Game> ongoingGames = allGames.stream()
                .map(gameRepository::findByGameId)
                .map(Optional::get)
                .filter(g -> g.getStatus().equals("ONGOING"))
                .collect(Collectors.toList());

        for (Game game : ongoingGames)
        {
            LocalTime start = LocalTime.of(0, game.getGameTime(), 0);
            LocalTime end = start.plusMinutes(1);

            List<PlayDto> ps = gameClient.plays(game.getGameId(),
                    start.format(DateTimeFormatter.ofPattern("mm:ss")),
                    end.format(DateTimeFormatter.ofPattern("mm:ss")));

            game.setGameTime(end.getMinute());
            if (ps.isEmpty())
            {
                game.setStatus(Game.STATUS_FINISHED);
            }
            gameRepository.save(game);

            for (PlayDto playDto : ps)
            {
                updatePlay(playDto, game);
            }
            log.info("Loaded {} plays from {}: {}", ps.size(), game.getGameId(), start);
        }

    }

    private void saveAllPlayers(Game game, List<PlayerDto> playerDtos) {
        // TODO Something like this can be useful
    }

    private void saveAllPlays(Game game, List<PlayDto> playDtos) {
        // TODO Save all plays, take care of duplicates
    }

}
