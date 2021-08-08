package rs.edu.raf.msa.game.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class Play
{
    @Id
    private Long playId;

    private String team;

    private String time;

    private String description;

    private Long gameId;

    private Integer quarterNo;

    private Integer homeScore;

    private Integer awayScore;

    private String homeTeam;

    private String awayTeam;

    @Column("play_id")
    @Builder.Default
    private Set<PlayPlayer> players = new HashSet<>();

    public void addPlayer(Player playPlayer) {
        players.add(createPlayPlayer(playPlayer));
    }

    private PlayPlayer createPlayPlayer(Player player) {
        PlayPlayer playPlayer = PlayPlayer.builder().playerId(player.getPlayerId()).build();

        return playPlayer;
    }
}
