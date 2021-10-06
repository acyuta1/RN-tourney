package com.acyuta.rf.tournament.core.model;

public enum AtpType {

    GRAND_SLAM(2000, 5, 3), ATP1000(1000,3, 2), ATP500(500,3,2);

    public int points;

    public int maxSet;

    public int minSet;

    AtpType(int p, int maxSet, int minSet) {
        points = p;
        this.maxSet = maxSet;
        this.minSet = minSet;
    }
}
