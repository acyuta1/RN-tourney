package com.acyuta.rf.tournament.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "match")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne()
    private Round round;

    @Enumerated(EnumType.STRING)
    private OccurrenceStatus matchStatus = OccurrenceStatus.ONGOING;

    private String playerOne;

    private String playerTwo;

    private String resultOfMatch;

}
