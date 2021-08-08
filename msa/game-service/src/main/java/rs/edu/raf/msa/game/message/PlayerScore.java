package rs.edu.raf.msa.game.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerScore {

	Long gameId;
	
	String player;

	// TODO How many points did player make, etc?
	
}
