package com.acyuta.rf.tournament.dto;

import com.acyuta.rf.tournament.model.AtpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDto {
    private Long id;

    private String tournamentName;

    private AtpType atpType;

    private String latestWinner;

    private Set<OccurrenceDto> occurrences = new HashSet<>();

    private LocalDate recentYear;

    private LocalDateTime updatedAt;

}
