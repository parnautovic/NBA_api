package rs.edu.raf.msa.pbp.model;

import java.util.*;

import lombok.Data;
import rs.edu.raf.msa.pbp.utils.PlayUtil;

@Data
public class PlayByPlay {
	public Map<Long, Player> players = new LinkedHashMap<>();

	public List<Quarter> quarters = new ArrayList<>(4);

	public String endTime;

	public Integer status;

	private static final int QUARTER_IN_SECS = 12 * 60;

	public List<Play> play(String fromMin, String toMin) {
		List<Play> wantedHighlits = new LinkedList<>();

		
		double fromInSec = PlayUtil.convertTimeToSeconds(fromMin);
		double toInSec = PlayUtil.convertTimeToSeconds(toMin);

		for (int q = PlayUtil.getQuarterIdx(fromMin); q <= Math.min(PlayUtil.getQuarterIdx(toMin), 3); ++q)
		{
			for (Play play : quarters.get(q).getPlays())
			{
				
				double highlitInSecs = QUARTER_IN_SECS - PlayUtil.convertTimeToSeconds(play.getTime());

				
				highlitInSecs += q * QUARTER_IN_SECS;

				play.setQuarter(q + 1);
				if (highlitInSecs >= fromInSec && highlitInSecs <= toInSec)
					wantedHighlits.add(play);
			}
		}

		return wantedHighlits;
	}
}
