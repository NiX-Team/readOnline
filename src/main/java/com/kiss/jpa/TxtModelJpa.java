package com.kiss.jpa;

import com.kiss.model.TxtModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 11723
 */
public interface TxtModelJpa extends JpaRepository<TxtModel, String> {
}
