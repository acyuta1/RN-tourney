package com.acyuta.rf.tournament.core.model;

import java.util.List;

public enum AtpType {

    GRAND_SLAM(List.of(200, 175, 150, 100, 75, 50, 25), 5, 3),
    ATP1000(List.of(100, 75, 60, 50, 35, 30, 15),3, 2),
    ATP500(List.of(50, 40, 30, 25, 12, 5),3,2);

    public List<Integer> points;

    public int maxSet;

    public int minSet;

    AtpType(List<Integer> p, int maxSet, int minSet) {
        points = p;
        this.maxSet = maxSet;
        this.minSet = minSet;
    }
}
