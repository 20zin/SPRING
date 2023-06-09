package com.yeongjin.YeongJin.response;


import com.yeongjin.YeongJin.Domain.Post;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 클래스를 생성
 */


@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    //생성자 오버로딩 값을 생성자에서 조회해오는걸로...
    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0,Math.min(title.length(),10));
        this.content = content;
    }

}
