package com.acyuta.rf.tournament.dto;

import com.acyuta.rf.tournament.model.OccurrenceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OccurrenceDto {

    private Long id;

    private Long tournamentId;

    private LocalDate occurrenceDate;

    private Long winnerId;

    private Long runnerUpId;

    private OccurrenceStatus occurrenceStatus;
}
