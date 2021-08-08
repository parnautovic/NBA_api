package rs.edu.raf.msa.game.entity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Player {

	@Id
	Long playerId;

	Long externalId;

	String firstName;
	String lastName;
}
