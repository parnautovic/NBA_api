package rs.edu.raf.msa.game.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.data.relational.core.mapping.event.AbstractRelationalEventListener;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import rs.edu.raf.msa.game.entity.Play;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "game-service.component.include",
        havingValue = "true",
        matchIfMissing = true
)
public class PlayListener extends AbstractRelationalEventListener<Play> {

    final PlaySender playSender;
    
    @Override
    protected void onAfterSave(AfterSaveEvent<Play> event) {
        super.onAfterSave(event);
        playSender.sendGameScore(event.getEntity());
    }

}