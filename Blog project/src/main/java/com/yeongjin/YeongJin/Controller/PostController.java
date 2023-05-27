package com.yeongjin.YeongJin.Controller;

//SPA -> vue
            // -> javascript + <-> API(JSON)

import com.yeongjin.YeongJin.Request.PostCreate;
import com.yeongjin.YeongJin.Request.PostEdit;
import com.yeongjin.YeongJin.Request.PostSearch;
import com.yeongjin.YeongJin.Service.PostService;
import com.yeongjin.YeongJin.response.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    //데이터형식에 대해 클래스를 따로 만들자
    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request){

        request.validate();
        postService.write(request);
        //request 클래스

    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
        //response 클래스
    }

    @GetMapping("/posts")
    public List<PostResponse>getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId , @RequestBody @Valid PostEdit request){
        {
            postService.edit(postId, request);
        }
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){

            postService.delete(postId);
    }
}










//    @GetMapping("/posts/{postId}/rss")
//        public Post getRss(@PathVariable(name = "postId") Long id){
//            Post post = postService.getRss(id);
//            return post;
//        }

//데이터를 검증하는 이유
//1.CLIENT 개발자가 깜빡할수있다 실수로 값을 안보낼수있다
//2.client bug로 값이 누락될수도
//3.외부에 나쁜사람이 값을 임의로 조작해서 보낼수있다.
//4.db에 값을 저장할때 의도치않은 오류가 발생할수있다.
//5.서버 개발자의 편안함을 위해서

//수십개의 데이터가 들오오면 if문 언제다쓰노 = 노가다임
//개발팁 - 무언가 3번이상 반복작업을 할때 잘못된게 아닐까? -의심!
//누락가능성
//생각보다 검증해야 할게 많다(꼼꼼하지 않을수 있다)
//뭔가 개발자스럽지 않다 = 간지x

//컨트롤러가 여러개 생긴다면...? -> 매번 이과정반복
//            if (result.hasErrors()){
//                List<FieldError> fieldErrors = result.getFieldErrors();
//                FieldError firstFielderror = fieldErrors.get(0);//첫번째 에러 여러개라면?
//                firstFielderror.getField();
//                String fieldName = firstFielderror.getField(); //title
//                String errorMessage = firstFielderror.getDefaultMessage();//..에러메시지
//
//                Map<String,String> error = new HashMap<>();
//                error.put(fieldName,errorMessage);
//                return error;
//            }
    //public String post(@RequestParam Map<String,String> params){
    //log.info("params={}",params);


    //public String post(@RequestParam String title, @RequestParam String content) {
    //log.info("title={}","content={}", title,content);
    //String title = params.get("title");

//          {"title":"타이틀 값이 없습니다") 보여주고싶음
            //db.save(params) 2023.4.28
            //레포지토리를 만들고 세이브하는것보다는 서비스레이어를 만들고 레포지토리를 호출하는것이 더 좋다

//           Case1. 저장한 데이터 Entity -> response로 응답하기
          // Case2. 저장한 데이터의 primary_id -> response로 응답하기
          //          Client에서는 수신한 id를 글 조회 API를 통해서 글 데이터를 수신받음
          // Case 3. 응답한 필요없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함
          // Bad case: 서버에서 -> 반드시 이렇게 할겁니다 fix!
          //                  ->서버에서 차라리 유연하게 대응 -> 코드를 잘 짜야죠....
          //                  ->한 번에 일괄적으로 잘 처리하는 케이스가 없다 -> 잘관리하는
          //                      형태가 중요하다!!!!

/**
     * /posts -> 글전체조회(검색+페이징)
     * /posts/{postId} -> 글 한개만 조회
     */