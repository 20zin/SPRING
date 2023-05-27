package com.yeongjin.YeongJin.Domain;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
    public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        private String title;

        @Lob //string형태로 저장되지만 db에서는 longtext로 생성됨
        private String content;

        @Builder
        public Post(String title, String content){
            this.title = title;
            this.content = content;
        }


        public PostEditor.PostEditorBuilder toEditor(){
            return PostEditor.builder()
                    .title(title)
                    .content(content);
        }

        public void edit(PostEditor postEditor){
            title = postEditor.getTitle();
            content = postEditor.getContent();
        }

}




//        public String getTitle(){
//            //엔티티에 getter를 만들때는 서비스 정책을 넣지말자!! 절대!!
//            return this.title;//수정이되서 결과가 내려오긴하지만...이것을 Post에서 수정하게되면 난중에 기능추가시 충돌이 발생할수도 있다!
//                                                //get()과getRss()가 같이 묶여지게 되어 둘다 10글자로 제한이되어버림!
//        }