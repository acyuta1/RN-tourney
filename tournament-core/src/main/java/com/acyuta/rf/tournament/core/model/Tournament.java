package com.acyuta.rf.tournament.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tournament")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tournamentName;

    @Enumerated(value = EnumType.STRING)
    private AtpType atpType;

    private String latestWinner;

    @OneToMany(mappedBy = "tournament", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Occurrence> occurrences = new HashSet<>();

    private LocalDate recentYear;

    private LocalDateTime updatedAt;

    private String imageUniqueName;

    private String uniqueIdentityName;

}
