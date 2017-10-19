package com.kiss.jpa;

import com.kiss.model.TxtChapterMsgModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 11723
 */
public interface TxtChapterJpa  extends JpaRepository<TxtChapterMsgModel, Integer> {
}
