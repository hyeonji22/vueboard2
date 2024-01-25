package com.vue.vueBoard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vue.vueBoard.mapper.boardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class boardService {
	
	
	private final boardMapper boardmapper;

	public List<Map<String, Object>> getboardlist(Map<String, Object> dataMap) {
		// TODO Auto-generated method stub
		return boardmapper.getboardlist(dataMap);
	}

	public int getTotalCnt(Map<String, Object> dataMap) {
		// TODO Auto-generated method stub
		return boardmapper.getTotalCnt(dataMap);
	}

	public Map<String, Object> getboard(Long id) {
		// TODO Auto-generated method stub
		return boardmapper.getboard(id);
	}

	public int register(Map<String, Object> dataMap) {
		// TODO Auto-generated method stub
		return boardmapper.insertBoard(dataMap);
	}

	public int deleteBoard(Long id) {
		// TODO Auto-generated method stub
		return boardmapper.deleteBoard(id);
	}

	public int updateBoard(Map<String, Object> dataMap) {
		// TODO Auto-generated method stub
		return boardmapper.updateBoard(dataMap);
	}

}
