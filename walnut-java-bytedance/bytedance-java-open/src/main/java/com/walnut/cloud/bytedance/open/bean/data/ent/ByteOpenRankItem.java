package com.walnut.cloud.bytedance.open.bean.data.ent;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenRankItem implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String actors;

    private String maoyanId;

    private String name;

    private String nameEn;

    private String areas;

    private String directors;

    private int discussionHot;

    private String id;

    private int searchHot;

    private int influenceHot;

    private String releaseDate;

    private int topicHot;

    private int type;

    private int hot;

    private String poster;

    private String tags;
}
