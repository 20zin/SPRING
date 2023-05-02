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



