package com.acyuta.rf.tournament.model;

public enum AtpType {

    GRAND_SLAM(2000), ATP1000(1000), ATP500(500);

    private int points;

    AtpType(int p) {
        points = p;
    }
}
