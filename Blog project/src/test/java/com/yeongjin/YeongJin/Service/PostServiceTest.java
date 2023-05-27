package com.yeongjin.YeongJin.Service;

import com.yeongjin.YeongJin.Domain.Post;
import com.yeongjin.YeongJin.Exception.PostNotFound;
import com.yeongjin.YeongJin.Repository.PostRepository;
import com.yeongjin.YeongJin.Request.PostCreate;
import com.yeongjin.YeongJin.Request.PostEdit;
import com.yeongjin.YeongJin.Request.PostSearch;
import com.yeongjin.YeongJin.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L,postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.",post.getTitle());
        assertEquals("내용입니다.",post.getContent());
    }
    @Test
    @DisplayName("글 1개 조회")
        void test2() {
            // given
            Post requestPost = Post.builder()
                    .title("foo")
                    .content("bar")
                    .build();
            postRepository.save(requestPost);

            // when
            PostResponse response = postService.get(requestPost.getId());

            // then
            assertNotNull(response);
            assertEquals(1L, postRepository.count());
            assertEquals("foo", response.getTitle());
            assertEquals("bar", response.getContent());
        }

    @Test
    @DisplayName("글 여러개 조회")
    void test3(){
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

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

            //when
            List<PostResponse> posts = postService.getList(postSearch);

            //then
            assertEquals(10L,posts.size());
            assertEquals("영지니 제목 19",posts.get(0).getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        //given
        Post post = Post.builder()
                .title("영지니 제목 " )
                .content("첼시 " )
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("영지니 수정 ")
                .content("첼시 ")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post postchanged = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다" + post.getId()));
        assertEquals("영지니 수정 ",postchanged.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        //given
        Post post = Post.builder()
                .title("영지니 제목 ")
                .content("첼시 ")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("영지니 제목 ")
                .content("첼시 ")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post postchanged = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다" + post.getId()));
        assertEquals("아스날 ", postchanged.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test6() {
        //given
        Post post = Post.builder()
                .title("영지니 제목 ")
                .content("첼시 ")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("아스날 ")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post postchanged = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다" + post.getId()));
        assertEquals("영지니 제목 ", postchanged.getTitle());
        assertEquals("아스날 ", postchanged.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test7(){
        //given
        Post post = Post.builder()
                .title("영지니 제목 ")
                .content("첼시 ")
                .build();
        postRepository.save(post);
        //when

        postService.delete(post.getId());

        //then
        assertEquals(0,postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test8() {
        // given
        Post post = Post.builder()
                .title("영지니")
                .content("첼시")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, ()->{
            postService.get(post.getId()+1L);
        });
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test9() {
        //given
        Post post = Post.builder()
                .title("영지니 제목 ")
                .content("첼시 ")
                .build();
        postRepository.save(post);

        //expected
        assertThrows(PostNotFound.class, ()->{
                    postService.delete(post.getId()+1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지않는글")
    void test10() {
        //given
        Post post = Post.builder()
                .title("영지니 제목")
                .content("첼시")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("영지니 제목")
                .content("첼시")
                .build();
        //expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId()+1L, postEdit);
        });
    }
}