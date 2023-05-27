package com.yeongjin.YeongJin.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeongjin.YeongJin.Domain.Post;
import com.yeongjin.YeongJin.Repository.PostRepository;
import com.yeongjin.YeongJin.Request.PostCreate;
import com.yeongjin.YeongJin.Request.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//mockMvcTest는 간단한 웹 레이어는 가능 but,서비스도 만들고 레포지토리도 만들어서 이걸론안되노
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectmapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Autowired
    private PostRepository postRepository;

    @BeforeEach //각각의 메소드들이 실행해주기전 먼저 실행함!
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 요청시 title값은 필수다")
    void test2() throws Exception {
        //given
            PostCreate request = PostCreate.builder()
                    .content("내용입니다.")
                    .build();

        String json = objectmapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("400"))
                        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                        .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요"))
                        .andDo(print());

    }

    @Test
    @DisplayName("글 작성 요청시 DB에 값이 저장된다")
    void test3() throws Exception {
        //given
                PostCreate request = PostCreate.builder()
                        .title("제목입니다.")
                        .content("내용입니다.")
                        .build();

                String json = objectmapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/posts")
                                .header("authorization","chelsea")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isOk())
                        .andDo(print());
        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());

    }


    @Test
    @DisplayName("글 1개조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);

        //expected(when + then)
        mockMvc.perform(get("/posts/{postId}",post.getId())
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(post.getId()))
                        .andExpect(jsonPath("$.title").value("1234567890"))
                        .andExpect(jsonPath("$.content").value("bar"))
                        .andDo(print());

        /**
         * {id:...,title:...}
         */

    }
    @Test
    @DisplayName("글 여러개조회")
    void test5() throws Exception {
            //given
            List<Post> requestPosts = IntStream.range(0,20)
                                    .mapToObj(i ->{
                                        return Post.builder()
                                                .title("영지니 제목 " + i)
                                                .content("첼시 " + i)
                                                .build();
                                    })
                            .collect(Collectors.toList());
            postRepository.saveAll(requestPosts);


            //expected(when + then)
            mockMvc.perform(get("/posts?page=1&size=10")
                            .contentType(APPLICATION_JSON))
                            .andExpect(jsonPath("$.length()", is(10)))
                            .andExpect(jsonPath("$[0].title").value("영지니 제목 19"))
                            .andExpect(status().isOk())
                            .andDo(print());

        /**
         * [{id:..., title:...}, {id:..., title:....}]
         */

    }

    @Test
    @DisplayName("글 제목수정")
    void test6() throws Exception {
        //given
        Post post = Post.builder()
                .title("영지니 제목 ")
                .content("첼시 ")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("영지니 제목 ")
                .content("아스날 ")
                .build();

        //expected(when + then)
        mockMvc.perform(patch("/posts/{postId}", post.getId()) //patch/post/{postid}
                        .contentType(APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(postEdit)))
                        .andExpect(status().isOk())
                        .andDo(print());
    }
    @Test
    @DisplayName("게시글 삭제")
    void test7() throws Exception {

        //given
        Post post = Post.builder()
                .title("영지니 제목 ")
                .content("첼시 ")
                .build();
        postRepository.save(post);

        //expected
        mockMvc.perform(delete("/posts/{postId}",post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test8() throws Exception{
        //expected
                mockMvc.perform(delete("/posts/{postId}",1L)
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void test9() throws Exception {
        //given
        PostEdit postEdit = PostEdit.builder()
                .title("영지니 제목 ")
                .content("아스날 ")
                .build();

        //expected(when + then)
        mockMvc.perform(patch("/posts/{postId}", 1L) //patch/post/{postid}
                        .contentType(APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성시 바보는 포함될수없다")
    void test10() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("나는 바보입니다")
                .content("첼시")
                .build();

        String json = objectmapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
