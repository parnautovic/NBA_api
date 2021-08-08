package rs.edu.raf.msa.pbp.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Player {

	@Getter(onMethod = @__(@JsonGetter("playerId")))
	@Setter(onMethod = @__(@JsonSetter("c")))
	private String playerId;

	@Getter(onMethod = @__(@JsonGetter("firstName")))
	@Setter(onMethod = @__(@JsonSetter("f")))
	private String firstName;

	@Getter(onMethod = @__(@JsonGetter("lastName")))
	@Setter(onMethod = @__(@JsonSetter("l")))
	private String lastName;

	@Getter(onMethod = @__(@JsonGetter("externalId")))
	@Setter
	private Long externalId;
}
