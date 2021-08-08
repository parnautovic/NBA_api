package rs.edu.raf.msa.pbp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Quarter {

	@Getter(onMethod = @__(@JsonGetter("quarterNo")))
	@Setter(onMethod = @__(@JsonSetter("q")))
	private int quarterNo;

	@Getter(onMethod = @__(@JsonGetter("plays")))
	@Setter(onMethod = @__(@JsonSetter("plays")))
	private List<Play> plays = new ArrayList<>(100);

	@Getter(onMethod = @__(@JsonGetter("atin")))
	@Setter(onMethod = @__(@JsonSetter("atin")))
	private String atin;

	@Getter(onMethod = @__(@JsonGetter("filters")))
	@Setter(onMethod = @__(@JsonSetter("filters")))
	private List<String> filters = new ArrayList<>();
}