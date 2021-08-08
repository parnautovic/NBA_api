package rs.edu.raf.msa.game.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class PlayPlayer {
    @Id
    private Long id;

    private Long playerId;
}
