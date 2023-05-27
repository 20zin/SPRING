package com.yeongjin.YeongJin.Service;


import com.yeongjin.YeongJin.Domain.Post;
import com.yeongjin.YeongJin.Domain.PostEditor;
import com.yeongjin.YeongJin.Exception.PostNotFound;
import com.yeongjin.YeongJin.Repository.PostRepository;
import com.yeongjin.YeongJin.Request.PostCreate;
import com.yeongjin.YeongJin.Request.PostEdit;
import com.yeongjin.YeongJin.Request.PostSearch;
import com.yeongjin.YeongJin.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;




    public void write(PostCreate postCreate) {
        //postCreate -> entity (request형태지 엔티티가 아님 바꿔주는 작업필요)

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        postRepository.save(post);//레포지토리 인터페이스에 저장해야지 db에저장된다!!!!
//        post.title = postCreate.getTitle();
//        post.content = postCreate.getContent(); ->필드에 바로 꽂는형태는 별로...
    }


    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content((post.getContent()))
                .build();

    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

         PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

         PostEditor postEditor = editorBuilder
                   .title(postEdit.getTitle())
                   .content(postEdit.getContent())
                   .build();

         post.edit(postEditor);
    }

    public void delete(Long id){
        //존재하지 않는 경우
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());
        //존재하는 경우
        postRepository.delete(post);
    }
}

//에디터개념 개어렵...
//PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

// PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
//            .content(postEdit.getContent())
//            .build();









//    public Post getRss(Long id){
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
//
//    }


        /**
         * PostController -> WebPostService -> Repository
         *                PostService
         */
//글이 너무 많다면? -> 비용이너무 많이 든다
//글이 100,000,000? -> db글 모두 조회? -> 뻗음
//db -> 애플리케이션 서버로 전달하는 시간, 트래픽 비용이 많이 발생할수있다.