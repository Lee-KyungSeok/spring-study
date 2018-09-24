package com.brms.book.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.brms.book.Book;

// InitializingBean, DisposableBean 을 이용하여 bean 객체가 생성되고 소멸하는 시점에 사용할 수 있다.
public class BookDao implements InitializingBean, DisposableBean {

	private Map<String, Book> bookDB = 
			new HashMap<String, Book>();
	
	public void insert(Book book) {
		bookDB.put(book.getbNum(), book);
	}
	
	public Book select(String bNum) {
		return bookDB.get(bNum);
	}
	
	public void update(Book book) {
		
	}
	
	public void delete() {
		
	}
	
	public Map<String, Book> getBookDB() {
		return bookDB;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("빈(Bean)객체 생성 단계");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("빈(Bean)객체 소멸 단계");
	}
	
}
