package com.ChrisSun.model;

/**
 * Created by Chris on 2016/8/28.
 */
public enum EntityType {
    QUESTION_TYPE("questionType"),
    COMMENT_TYPE("commentType");

    private String type;
    private EntityType(String type){
        this.type = type;
    }
}
