package rs.edu.raf.msa.pbp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rs.edu.raf.msa.pbp.model.Play;
import rs.edu.raf.msa.pbp.model.PlayByPlay;
import rs.edu.raf.msa.pbp.model.Player;

@RestController
public class GameController {

	@Autowired
	ObjectMapper objectMapper;

	@GetMapping("/game/{gameId}")
	public PlayByPlay game(@PathVariable String gameId) throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("games/" + gameId + ".json");
		PlayByPlay result = objectMapper.readValue(input, PlayByPlay.class);
		return result;
	}
	
	@GetMapping("/games")
	public List<String> games() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
	    
		Resource[] games;
		try {
			games = resolver.getResources("classpath:games/*.json");
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error opening games!");
		}
	   
		List<String> result = new ArrayList<>(games.length);

		for (Resource game : games)
		{
			result.add(game.getFilename().split("\\.")[0]);
		}
		
		return result;
	}
	
	@GetMapping("/plays/{gameId}/{fromMin}/{toMin}")
	public List<Play> plays(@PathVariable String gameId, @PathVariable String fromMin, @PathVariable String toMin) throws IOException
	{
		PlayByPlay highlights = game(gameId);
		
		return highlights.play(fromMin, toMin);
	}


	@GetMapping("/players/{gameId}")
	public List<Player> players(@PathVariable String gameId) throws IOException
	{
		 PlayByPlay highlights = game(gameId);
		 for (Map.Entry<Long,Player> entry : highlights.players.entrySet())
		 {
		 	entry.getValue().setExternalId(entry.getKey());
		 }

		 return new ArrayList<>(highlights.players.values());
	}
	
}
