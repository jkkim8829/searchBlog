# searchBlog

1. kakaoAPI 블로그 검색
   - URI : /searchBlog
   - 방식 : get
   - Request
     {
	     "query" : "String", // 검색어 필수 값
		 "sort" : "String", // 정렬 accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
		 "page" : Integer // 페이지 번호 1~50, 기본값 1
	 }
   - Respones
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
	 
2. 인기 검색어
   - URI : /popularSearch
   - 방식 : get
   - Request
     {
	     "date" : Integer, //인기 검색어 수집 기간 ex) 1 = 하루기간 인기검색어 수집, 0 = 2020년 후 인기검색어
	 }
   - Respones
     검색횟수 순서대로 최대 10개만 리턴
     [{"searchCount":Integer, //검색횟수
	   "searchWord":"String"  //검색어
	 }]