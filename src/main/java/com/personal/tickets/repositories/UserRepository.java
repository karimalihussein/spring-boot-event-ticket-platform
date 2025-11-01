package com.personal.tickets.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.personal.tickets.domain.Entities.User;
import java.util.UUID;

@Repository 
public interface UserRepository extends JpaRepository<User, UUID> {}
