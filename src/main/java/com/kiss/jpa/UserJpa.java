package com.kiss.jpa;
import com.kiss.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpa  extends JpaRepository<UserModel, Integer> {
    @Override
    UserModel getOne(Integer integer);
    @Query(value = "from UserModel u where u.username=:username")
    UserModel findUser(@Param("username") String username);
}
