package com.acyuta.rf.tournament.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "occurence")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
