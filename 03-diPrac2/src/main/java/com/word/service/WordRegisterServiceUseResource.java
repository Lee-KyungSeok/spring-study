package com.word.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordRegisterServiceUseResource {

	// Resource 는 생성자에 사용할 수 없고 property 나 메서드에만 사용할 수 있다.
	@Resource
	private WordDao wordDao;
	
	public WordRegisterServiceUseResource() {
		// 프로퍼티에서 주입하므로 디폴트 생성자는 필수이다.
	}

	// 아래처럼 생성자에는 사용 불가능하다.
	//@Resource
	public WordRegisterServiceUseResource(WordDao wordDao) {
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
	
	//@Resource
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
}