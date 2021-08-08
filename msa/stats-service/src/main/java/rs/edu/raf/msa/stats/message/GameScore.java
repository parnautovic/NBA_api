package rs.edu.raf.msa.stats.message;

import lombok.Data;

@Data
public class GameScore {

	Long gameId;

	String homeTeam;
	String visitorTeam;

	int quarter;
	String gameTime;

	int homeScore;
	int visitorScore;
}
