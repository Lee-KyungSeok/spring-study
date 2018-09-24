package com.word.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordSearchServiceUseResource {
	
	@Resource
	private WordDao wordDao;
	
	public WordSearchServiceUseResource() {
		
	}

	public WordSearchServiceUseResource(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
	public WordSet searchWord(String wordKey) {
		if(verify(wordKey)) {
			return wordDao.select(wordKey);
		} else {
			System.out.println("WordKey information is available.");
		}
		
		return null;
	}
	
	public boolean verify(String wordKey){
		WordSet wordSet = wordDao.select(wordKey);
		return wordSet != null ? true : false;
	}
	
	//@Resource
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
}
