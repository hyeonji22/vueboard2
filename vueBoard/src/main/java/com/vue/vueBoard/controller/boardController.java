package com.vue.vueBoard.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vue.vueBoard.service.boardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class boardController {
	
	private final boardService boardservice;
	
	//목록조회
	@GetMapping("/board/{currentPage}")
	public ResponseEntity<Map<String, Object>> getBoardList(@PathVariable int currentPage,@RequestParam Map<String, Object> inMap){
		log.debug("inMap ", inMap);
		
		int pageSize =10;
		int page = (currentPage-1)*pageSize;

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("pageSize", pageSize);
		dataMap.put("page", page);
		dataMap.put("searchType", inMap.get("searchType"));
		dataMap.put("keyword", inMap.get("keyword"));
		
	    
		List<Map<String, Object>> boardList = boardservice.getboardlist(dataMap);
		int totalCnt = boardservice.getTotalCnt(dataMap);
		
		//페이징 가져오기
		Map<String, Object> pageMap = getPageMaker(currentPage, pageSize, totalCnt);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("boardList", boardList);
		response.put("totalCnt", totalCnt);
		response.put("pageMap", pageMap);
		
		log.debug("boardList ", boardList);
		log.debug("totalCnt ", totalCnt);
		log.debug("pageMap ", pageMap);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	 //페이징
    private Map<String, Object> getPageMaker(int currentPage, int amount, int totalCount) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        int endPage = (int)(Math.ceil(currentPage / 10.0) * 10);
        int startPage = endPage - 9;
        int realEnd = (int)(Math.ceil(totalCount * 1.0 /amount));
        endPage = realEnd < endPage ? realEnd : endPage;
        boolean prev = startPage > 1;
        boolean next = realEnd > endPage;
        int[] pageNumberArr = Arrays.copyOfRange(new int[endPage - startPage + 1], 0, endPage - startPage + 1);
          for (int i = 0; i < pageNumberArr.length; i++) {
             pageNumberArr[i] = startPage + i;
          }
        pageMap.put("endPage", endPage);
        pageMap.put("startPage", startPage);
        pageMap.put("prev", prev);
        pageMap.put("next", next);
        pageMap.put("currentPage", currentPage);
        pageMap.put("pageNumberArr", pageNumberArr);
        return pageMap;
     }
    //상세조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<Map<String, Object>> getBoardDetail(@PathVariable Long id){
    	log.debug("id" +id);
    	
    	Map<String, Object> board = boardservice.getboard(id);
    	
    	 if (board != null) {
			 return new ResponseEntity<>(board, HttpStatus.OK);
		 }else{
			 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		 }
    }
    //글등록
    @PostMapping("/writer")
    public ResponseEntity<Integer> insertBoard(@RequestBody Map<String, Object> inMap){
    	log.debug("inMap" +inMap);
    	Map<String, Object> dataMap =  new HashMap<String, Object>();
    	dataMap.put("title", inMap.get("title"));
    	dataMap.put("content", inMap.get("content"));
    	dataMap.put("writer", inMap.get("writer"));
    	
    	int result = boardservice.register(dataMap);
    	
    	
    	if (result == 1 ) {
			 return new ResponseEntity<Integer>(result, HttpStatus.OK);
		 }else{
			 return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		 }
    	
    }
    //글삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Integer> deleteBoard(@PathVariable Long id){
    	log.debug("id" +id);
    	int result = boardservice.deleteBoard(id);
    	
    	if (result == 1 ) {
			 return new ResponseEntity<Integer>(result, HttpStatus.OK);
		 }else{
			 return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		 }
    	
    }
    //글수정 
    @PatchMapping("/update/{id}")
    public ResponseEntity<Integer> updateBoard(@PathVariable Long id,
    	@RequestBody Map<String, Object> inMap){
    	
    	log.debug("inMap" +inMap);
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("id", id);
    	dataMap.put("title", inMap.get("title"));
    	dataMap.put("writer", inMap.get("writer"));
    	dataMap.put("content", inMap.get("content"));
    	
    	int result = boardservice.updateBoard(dataMap);
    	
    	if (result == 1 ) {
			 return new ResponseEntity<Integer>(result, HttpStatus.OK);
		 }else{
			 return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		 }
   	
    }
    
    

}
