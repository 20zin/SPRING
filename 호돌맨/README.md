2023.4.29  
게시글 조회 -> 응답 클래스 분리  
1.Post라는 엔티티 클래스에 서비스정책은 넣지말자!! -> 나중에 기능 추가시 충돌발생가능성 농후!  
2.클라이언트가 원하는 서비스정책을위한 응답 클래스를 따로 만들어 거기 생성자에 요구사항을 추가한다!  

게시글 조회 -> 게시글 여러개 조회  
1.PostController에 @GetMapping(/"POST")를 생성하고 여러건을 조회하기 위한 JSON형태를 리스트로 선언한다  
2.TEST코드에서 assert의 jsonpath를 단건 조회에서 "$.매개변수"값을 넣어주었지만 여러건 조회에서는 "$.length()", Matchers.is(갯수)로 치환한다  
3.map ,collect????  
4.properties.yml(spring boot)에 h2-db연결(1회성db확인용) + jdbc:h2:mem:yeongjin으로 url할당 + localhost:8080/posts에 리스트형태로 db저장확인  

