package com.kyung.batch.jobs.readers;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueItemReader<T> implements ItemReader<T> {

    private Queue<T> queue;

    public QueueItemReader(List<T> data) {
        this.queue = new LinkedList<>(data); // 지정될 타깃 데이터를 큐에 담아 놓는다.
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 큐에서 데이터를 하나씩 반환한다. (하나씩 반환하므로 비효율적일 수 있다. 하지만 기본 반환타입이 한개다... 다르게 사용하는 것을 추천!)
        return this.queue.poll();
    }
}
