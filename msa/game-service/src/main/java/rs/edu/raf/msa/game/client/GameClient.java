package rs.edu.raf.msa.game.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rs.edu.raf.msa.game.client.dto.PlayDto;
import rs.edu.raf.msa.game.client.dto.PlayerDto;

@FeignClient(value = "pbp-service", url = "http://${pbp-service.url:localhost}:8080")
public interface GameClient {

	@RequestMapping(method = RequestMethod.GET, value = "/games")
	public List<String> games();

	@RequestMapping(method = RequestMethod.GET, value = "/players/{gameId}")
	public List<PlayerDto> players(@PathVariable String gameId);

	@RequestMapping(method = RequestMethod.GET, value= "/plays/{gameId}/{fromMin}/{toMin}")
	public List<PlayDto> plays(@PathVariable("gameId") String gameId,@PathVariable("fromMin") String fromMin,@PathVariable("toMin") String toMin);
}
