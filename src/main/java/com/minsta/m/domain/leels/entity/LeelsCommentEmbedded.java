package com.minsta.m.domain.leels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LeelsCommentEmbedded implements Serializable {

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "leels_id")
    private Long leelsId;


    @Column(name = "leels_comment_id")
    private Long leelsCommentId;
}
