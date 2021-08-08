package rs.edu.raf.msa.game.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Builder
@Data
public class Game
{
    @Id
    private Long id;

    private String gameId;

    private String homeTeam;

    private String awayTeam;

    private String status;

    private Integer gameTime;


    public static final String STATUS_ONOGING = "ONGOING";
    public static final String STATUS_FINISHED = "FINISHED";
}
