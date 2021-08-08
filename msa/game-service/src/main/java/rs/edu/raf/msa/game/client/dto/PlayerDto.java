package rs.edu.raf.msa.game.client.dto;

import lombok.Data;

@Data
public class PlayerDto {

    private String playerId;

    private String firstName;

    private String lastName;

    private Long externalId;
}
