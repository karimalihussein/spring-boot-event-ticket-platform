package com.personal.tickets.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.personal.tickets.domain.Entities.Event;
import java.util.UUID;

@Repository 
public interface EventRepository extends JpaRepository<Event, UUID> {}
