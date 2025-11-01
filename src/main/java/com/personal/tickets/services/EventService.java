package com.personal.tickets.services;

import com.personal.tickets.domain.Requests.CreateEventRequest;

import java.util.UUID;

import com.personal.tickets.domain.Entities.Event;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest createEventRequest);
}
