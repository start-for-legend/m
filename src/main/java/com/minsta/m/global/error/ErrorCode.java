package com.minsta.m.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //TOKEN
    TOKEN_IS_EXPIRED("토큰이 만료 되었습니다.", 401),
    TOKEN_NOT_VALID("토큰이 유효 하지 않습니다.", 401),

    //USER
    NOT_AUTHENTICATION_PHONE("Not Authenticated phone number, retrying after authenticate", 403),
    AUTH_KEY_IS_NOT_MATCHED("Auth Key is not matched", 403),
    USER_PHONE_EXIST("Duplicated phone value", 409),
    USER_NOT_FOUND("User not found", 404),
    INVALID_PASSWORD("Password mismatch", 403),
    USER_NICK_NAME_EXIST("Duplicated nickName value", 409),

    //SERVER
    SERVER_ERROR("SERVER ERROR", 500),

    //AWS
    FILE_UPLOAD_FAIL("파일 업로드에 실패했습니다.", 500),
    NOT_ALLOWED_FILE("허용되지 않은 파일 형식입니다.", 400),
    INVALID_FORMAT_FILE("잘못된 형식의 파일입니다.", 400),

    //Leels
    LEELS_NOT_FOUND("릴스가 없습니다", 404),
    DELETE_LEELS_DENIED("작성자만 삭제할 수 있습니다", 403),

    //Leels Comment
    LEELS_COMMENT_NOT_FOUND("릴스 댓글이 없습니다.", 404),
    DELETE_PERMISSION_DENIED("작성자만 삭제가 가능", 403),
    UPDATE_PERMISSION_DENIED("작성자만 수정이 가능", 403),
    REPLY_COMMENT_NOT_FOUND("답글이 존재하지않습니다.", 404),
    CANCEL_PERMISSION_DENIED("좋아요 취소는 본인만 가능합니다", 403),

    //Client
    BAD_REQUEST("잘못된 요청",400),
    DENIED_TOW_TOUCH("좋아요 누르기나 취소는 2번 터지할 수 없습니다", 403),

    //CHAT
    EXIST_CHAT_ROOM("이미 채팅방이 존재합니다", 400),
    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다", 404),
    PERMISSION_DENIED_CHAT_ROOM("채팅방에 속하지 않음", 403),
    PERMISSION_DENIED_DELETE_CHAT("채팅 작성자가 아님", 403),
    CHAT_NOT_FOUND("채팅이 없음", 404),

    //FOLLOW
    NOT_FOLLOW_MYSELF("자기 자신은 영원한 친구입니다", 403),

    //STORY
    STORY_NOT_FOUND("스토리가 없습니다", 404),

    //FEED
    FEED_NOT_FOUND("피드가 없습니다", 404),
    FEED_NOT_LIKE("좋아요를 누르지 않음", 403),
    FEED_EXIST_LIKE("좋아요를 이미 누름", 403),
    NOT_OWNER_FEED("피드 주인이 아님", 403);

    private final String message;
    private final int status;
}
