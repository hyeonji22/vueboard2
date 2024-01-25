package com.vue.vueBoard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface boardMapper {

	List<Map<String, Object>> getboardlist(Map<String, Object> dataMap);

	int getTotalCnt(Map<String, Object> dataMap);

	Map<String, Object> getboard(Long id);

	int insertBoard(Map<String, Object> dataMap);

	int deleteBoard(Long id);

	int updateBoard(Map<String, Object> dataMap);






}
