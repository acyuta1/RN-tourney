package com.acyuta.rf.tournament.core.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "occurrence", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Round> rounds = new ArrayList<>();
}
