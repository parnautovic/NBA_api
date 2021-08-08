package rs.edu.raf.msa.pbp.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Data
public class Play {
    @Getter(onMethod = @__(@JsonGetter("description")))
    @Setter(onMethod = @__(@JsonSetter("d")))
    private String description;

    @Getter(onMethod = @__(@JsonGetter("team")))
    @Setter(onMethod = @__(@JsonSetter("t")))
    private String team;

    @Getter(onMethod = @__(@JsonGetter("time")))
    @Setter(onMethod = @__(@JsonSetter("c")))
    private String time;

    @Getter(onMethod = @__(@JsonGetter("players")))
    @Setter(onMethod = @__(@JsonSetter("players")))
    private List<Long> players = new LinkedList<>();

    @Getter(onMethod = @__(@JsonGetter("quarterNo")))
    private Integer quarter;

    @Getter(onMethod = @__(@JsonGetter("homeScore")))
    @Setter(onMethod = @__(@JsonSetter("vs")))
    private Integer homeScore;

    @Getter(onMethod = @__(@JsonGetter("awayScore")))
    @Setter(onMethod = @__(@JsonSetter("hs")))
    private Integer awayScore;
}
