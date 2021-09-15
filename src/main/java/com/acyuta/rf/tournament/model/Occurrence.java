package com.acyuta.rf.tournament.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "occurrence")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Occurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne()
    private Tournament tournament;

    private LocalDate occurrenceDate;

    private Long winnerId;

    private Long runnerUpId;

    @Enumerated(EnumType.STRING)
    private OccurrenceStatus occurrenceStatus = OccurrenceStatus.ONGOING;
}
