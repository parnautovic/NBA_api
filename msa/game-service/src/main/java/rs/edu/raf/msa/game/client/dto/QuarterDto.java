package rs.edu.raf.msa.game.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuarterDto {

    private int quarterNo;

    private List<PlayDto> plays;
}