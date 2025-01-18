package com.url_shortner.bitly.Repository;

import com.url_shortner.bitly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<User,Long> {
     User findByUsername(String username);
}
