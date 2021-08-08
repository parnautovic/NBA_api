package rs.edu.raf.msa.stats.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

	public static final String GAME_SCORE = "game-score";
	public static final String GAME_SCORE_RUN = "game-score.run";
	public static final String GAME_SCORE_DROUGHT = "game-score.drought";

	public static final String PLAYER_SCORE = "player-score";

	@Bean
	public Declarables gameScoreBindings() {
		FanoutExchange exchange = new FanoutExchange(GAME_SCORE);

		Queue run = QueueBuilder.durable(GAME_SCORE_RUN).build();
		Queue drought = QueueBuilder.durable(GAME_SCORE_DROUGHT).build();

		return new Declarables(run, drought, exchange, 
		        BindingBuilder.bind(run).to(exchange),
				BindingBuilder.bind(drought).to(exchange));
	}

	@Bean
	public Queue playerScore() {
		return QueueBuilder.durable(PLAYER_SCORE).build();
	}

	//

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}
}
