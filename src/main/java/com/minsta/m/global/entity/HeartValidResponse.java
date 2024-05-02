package com.minsta.m.global.entity;

public record HeartValidResponse(
        boolean isTrue
) {
    public static HeartValidResponse of (boolean isTrue) {
        return new HeartValidResponse(isTrue);
    }

    public HeartValidResponse {}
}
