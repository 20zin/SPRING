package com.yeongjin.YeongJin.Request;


import com.yeongjin.YeongJin.Exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
//@AllArgsConstructor lombok에서는 생성자 자동으로 생성해줌
//게시글을 작성할때 요청과 validation정책을 담아준 클래스
public class PostCreate {
    @NotBlank(message = "타이틀을 입력해주세요")//자동으로 빈값을 검증해줌
    private String title;


    @NotBlank(message = "콘텐츠를 입력해주세요")
    private String content;

    @Builder //클래스위에 달아도되지만 생성자위에다는것을 추천!! 매개변수가 없는 생성자가 있다면 빌더오류가 발생할수도있기때문에!!
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (title.contains("바보")) {
            throw new InvalidRequest("title","제목에 바보를 입력할 수 없습니다!");
        }
    }
}





//이상한 개발자가 생성자순서를 바꾸게 된다면 test에서 content에 제목이 들어가고
//title에 내용이 들어가게된다... -> 빌더를 어노테이션(롬복에서 제공)
//빌더의 장점
// - 가독성이 좋다(값 생성에 대한 유연함)
// - 필요한 값만 받을수있다. // -> 오버로딩 가능한 조건 찾아보기
// - 객체의 불변성