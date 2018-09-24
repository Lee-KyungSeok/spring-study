package com.word.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordRegisterServiceUseInject {

	// inject 를 사용할 때 같은 타입의 객체가 여러개인 경우 Named 를 사용해 id 값을 주어 사용할 수 있다.
	@Inject
	@Named(value="wordDao1")
	private WordDao wordDao;
	
	public WordRegisterServiceUseInject() {
		
	}
	
//	@Inject
	public WordRegisterServiceUseInject(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
	public void register(WordSet wordSet) {
		String wordKey = wordSet.getWordKey();
		if(verify(wordKey)) {
			wordDao.insert(wordSet);
		} else {
			System.out.println("The word has already registered.");
		}
	}
	
	public boolean verify(String wordKey){
		WordSet wordSet = wordDao.select(wordKey);
		return wordSet == null ? true : false;
	}
	
//	@Inject
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
}
