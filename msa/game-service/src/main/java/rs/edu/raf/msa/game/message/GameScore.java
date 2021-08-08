package rs.edu.raf.msa.game.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameScore {

	Long gameId;

	String homeTeam;
	String visitorTeam;

	int quarter;
	String gameTime;

	int homeScore;
	int visitorScore;
	
	// TODO Publishing into messaging queue
		
}
