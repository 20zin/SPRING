### 2023.04.29  
게시글 조회 -> 응답 클래스 분리  
1.Post라는 엔티티 클래스에 서비스정책은 넣지말자!! -> 나중에 기능 추가시 충돌발생가능성 농후!  
2.클라이언트가 원하는 서비스정책을위한 응답 클래스를 따로 만들어 거기 생성자에 요구사항을 추가한다!  

게시글 조회 -> 게시글 여러개 조회  
1.PostController에 @GetMapping(/"POST")를 생성하고 여러건을 조회하기 위한 JSON형태를 리스트로 선언한다  
2.TEST코드에서 assert의 jsonpath를 단건 조회에서 "$.매개변수"값을 넣어주었지만 여러건 조회에서는 "$.length()", Matchers.is(갯수)로 치환한다  
3.map ,collect????  
4.properties.yml(spring boot)에 h2-db연결(1회성db확인용) + jdbc:h2:mem:yeongjin으로 url할당 + localhost:8080/posts에 리스트형태로 db저장확인  

### 2023.04.30  
게시글 조회 -> 페이징 처리
<핵심>  
조회하는 글이 너무 많다면? 1.DB가 뻗을수있다 2.애플리케이션으로 전달하는 시간,트래픽비용이 많이 발생할수있다.  
1.페이징처리 -> pageable pageRequest.of()메소드를 통해 페이징처리 자동실행  
2.페이징처리할때 getList 1페이지 요청시 ->파라미터를 0으로 받아온다 내부적으로 0으로 자동 매핑!  

### 2023.05.01  
게시글 조회 -> 페이징 처리(QueryDSL)  
Dependency설정 및 bulid.gralde class에 set설정 완료 -> generated에 Qpost클래스 설정  
1.postrepository implements설정완료

### 2023.05.02
게시글 수정  
현재 CRUD중 create와 read는 작업완료하였고 update해야겠쥬?(intellij 자동줄맞춤 c+s+a+l)   
Postcreate와 PostEdit의 빌드 구조가 똑같다고 해서 하나로 퉁쳐서 데이터보내겠다고 생각하면 큰일남!!  
1.posteditor생성(어려운부분) -> postservice test진행시 제목과 콘텐츠중 하나만 수정하고 싶어서 바꾸고 다른건 클라이언트가 null로 보내게되면  
기존의 db에 저장된 바뀌지않는부분을 저장된 상태를 그대로 넘겨줘야하는데 posteditor객체를 생성하지않았다면 edit객체에 일일이 명시해줘야한다  

게시글 삭제  
postservice에서 존재하는 경우와 존재하지않는 경우를 분리해서 메소드를 생성한다!!  

### 2023.05.03
예외처리 1  
Illegalargmentexception.class는 assertthrow에서 예상되는 문자까지 넘겨줘야하는데 postnotfound메소드를 response에 달아주면 그냥 찾지 못한다는 사실을 넘김  
PostNotFound는 자바에서 제공해주지 않기때문에 package 따로 생성  

예외처리 2  
HTTPstatuscode들의 종류별로 예외처리!!(추가적인 공부필요)  
페이지 예외처리에대한 클래스를 일일이 만들면 너무 힘들다... ->통합적인 클래스를 생성!!(abstract)  
그렇다면 execption에해당하는 statuscode가 다 다르잖아? 그러면 어떡해? -> exception종류에따라 물어보는것이 간편!  

### 2023.05.08
1.배포  
프로세스 자동화!! 클라우드 서비스 -> 확장성  
/gradlew build -> jar파일 생성 -> 개발서버에 던져줌/ 사용할 파일은 bootjar이므르로 build.gradle에서 enabled=true설정 / 일반jar는 enabled=false  
2.AWS EC2생성


### 2023.05.09  
CORS문제 해결 + 글 작성화면 만들기  

### 2023.05.15  
npm run dev  
proxy 설정 및 axios문제 해결(서버 + 클라이언트) + 블로그 화면 제작까지 완성  

### 2023.05.20  
기장 기본적인 인증요청방법  
1.get parameter 2.post(body)value 3.header 2번은 그닥 좋지않은방법론  
1번으로는 controller에 requestparam으로 인증을 요구  
3번은 관리자만 가능한 부분에 requesheader부분에 추가 하면 된다  
하지만 이걸 매번 api에 추가해줘야 한다면 불효율적이다  

intercepter활용하기  
webmvc config 클래스를 따로 만들어 authintercepter를 매개변수로 할당하고 prehandler에 accesstoken을 매개변수로 준다  
조건에 맞으면 보여주고 아니면 Unauthorized() 예외처리 클래스로 던져준다 (인증안된 statuscode -> 401)  
인증이 불필요한 루트에는 webmvconfig에 excludePathPatterns로 경로명추가  

ArgumentResolver  
webRequest.getparameter로 accesstoken을 가져와서 인증이 필요한 path를 설정  
충돌발생가능성도 있다  

Header로 인증할 필요!  
getheader로 변경 / intellij에서 제공하는 테스트 요청도 있다(directory 생성)  
/**
  GET http://localhost:8080/foo
  content-type:application/json
  Authorization: asdf
*/






