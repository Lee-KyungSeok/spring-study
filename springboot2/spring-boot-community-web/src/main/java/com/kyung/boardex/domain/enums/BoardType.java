package com.kyung.boardex.domain.enums;

public enum BoardType {
    notice("공지사항"),
    fee("자유게시판");

    private String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
