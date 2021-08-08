package rs.edu.raf.msa.game.client.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PlayDto {

    private String description;

    private String team;

    private String time;

    private Integer quarterNo;

    private List<Long> players = new LinkedList<>();

    private Integer homeScore;

    private Integer awayScore;
}
