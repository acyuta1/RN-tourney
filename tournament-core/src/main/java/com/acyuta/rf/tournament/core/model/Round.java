package com.acyuta.rf.tournament.core.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "round")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne()
    private Occurrence occurrence;

    @Enumerated(EnumType.STRING)
    private RoundType roundType;

    @Enumerated(EnumType.STRING)
    private OccurrenceStatus roundStatus = OccurrenceStatus.ONGOING;

    private Integer roundValue;

}
