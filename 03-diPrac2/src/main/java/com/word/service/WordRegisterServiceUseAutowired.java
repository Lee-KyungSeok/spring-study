package com.word.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordRegisterServiceUseAutowired {

    // property 에도 autowired 를 넣을 수 있다.
    // 만약 동일한 타입이 여러개 존재하는 경우 qualifier 를 통해서 넣어주도록 한다.
    // - 하지만 이름이 동일할 때는 찾아서 넣어주기는 하지만 qualifier 를 사용하는 것이 좋다.
	@Autowired
//    @Autowired(required = false) // 객체가 필수가 아니라면 false 를 줄 수 있다. (하지만 잘 쓰이지는 않는다. 디폴트가 true 이다.)
	@Qualifier("usedDao")
	private WordDao wordDao;

    // property 에 autowired 를 쓰고 싶다면 디폴트 생성자를 반드시 명시해주어야 한다. (late init 때문)
	public WordRegisterServiceUseAutowired() {
		// TODO Auto-generated constructor stub
	}

	// 아래처럼 autowird 를 이용하면 xml 에서 설정해주지 않더라도 생성자를 주입시킬 수 있다.
//	@Autowired
	public WordRegisterServiceUseAutowired(WordDao wordDao) {
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

	// property(setter) 에도 autowired 를 넣을 수 있다.
//	@Autowired
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
}
