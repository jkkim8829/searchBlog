# searchBlog

jar파일 동작 후 127.0.0.1:8080 접근 시 간단한 프론트 구현

1. kakaoAPI 블로그 검색
   - URI : /searchBlog
   - 방식 : get
   - Request
     {
	     "query" : "String", // 검색어 필수 값
		 "sort" : "String", // 정렬 accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
		 "page" : Integer // 페이지 번호 1~50, 기본값 1
	 }
   - Respones(kakao)
     {"documents":
	     [{"blogname":"String", //블로그 이름
		   "contents":"String", //블로그 글 요약
		   "datetime":"ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]", //블로그 글 작성시간
		   "thumbnail":"String", //검색 시스템에서 추출한 대표 미리보기 이미지 URL
		   "title":"String", //블로그 글 제목
		   "url":"String" //블로그 글 URL
		   }], 
     "meta":{"is_end":Boolean, //현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
	       "pageable_count":Integer, //total_count 중 노출 가능 문서 수
	       "total_count":Integer //검색된 문서 수}
	 }
   - Respones(naver)
     {"lastBuildDate":"dateTime",  //검색결과를 생성한 시간 
	  "total":Integer,  //총 검색 결과 개수 
	  "start":Integer,  //검색시작 위치
	  "display":Integer,  //한번에 표시할 검색 결과 개수 
	  "items":
	     [{"title":"String", //블로그 포스트 제목
		   "link":"String", //블로그 포스트 URL
		   "description":"String", //블로그 포스트 내용 요약
		   "bloggername":"String", //검색 시스템에서 추출한 대표 미리보기 이미지 URL
		   "bloggerlink":"String", //블로그 글 제목
		   "postdate":"dateTime" //블로그 글 URL
		  }]
	 }
	 
2. 인기 검색어
   - URI : /popularSearch
   - 방식 : get
   - 추가사항 : 인기검색어는 기간제한 없이 조회할 경우 정확도도 떨어 질 수 있어서 date를 파라메터로 받아 기간 설정(조회 시간을 위해 DB인덱스 추가)
   - Request
     {
	     "date" : Integer, //인기 검색어 수집 기간 ex) 1 = 하루기간 인기검색어 수집, 0 = 2020년 후 인기검색어
	 }
   - Respones
     검색횟수 순서대로 최대 10개만 리턴
     [{"searchCount":Integer, //검색횟수
	   "searchWord":"String"  //검색어
	 }]

3. 추가사항
   - 멀티 모듈 구성
   - 동시성 이슈 처리
   - 카카오 검색 api 장애 시 네이버 api 호출

jar파일 구글 드라이브 주소 : https://drive.google.com/file/d/1UThKtFfnWhyfzQpr_OdEVOMpCtNKhJsK/view?usp=sharing
