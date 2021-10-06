package com.acyuta.rf.tournament.core.messaging;

import com.acyuta.rf.rafantasyShared.dto.participation.ParticipationInitDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundFinishedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePublisher {

    private static final String QUEUE_ROUND_INIT = "round-finished-queue";

    private final RabbitTemplate rabbitTemplate;

    public void publishRoundFinishedInit(RoundFinishedDto roundFinishedDto) {
        System.out.println(roundFinishedDto);
        rabbitTemplate.convertAndSend(QUEUE_ROUND_INIT, roundFinishedDto);
    }
}
