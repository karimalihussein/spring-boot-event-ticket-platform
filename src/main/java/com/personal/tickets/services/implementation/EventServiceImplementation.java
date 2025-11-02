package com.personal.tickets.services.implementation;

import com.personal.tickets.services.EventService;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.personal.tickets.domain.Requests.CreateEventRequest;
import com.personal.tickets.repositories.UserRepository;
import com.personal.tickets.repositories.EventRepository;
import com.personal.tickets.domain.Entities.Event;
import com.personal.tickets.domain.Entities.User;
import com.personal.tickets.domain.Entities.TicketType;
import java.util.List;
import com.personal.tickets.exceptions.UserNotFoundException;


import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EventServiceImplementation implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    
    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest) {
        User organizer = userRepository.findById(organizerId).orElseThrow(() -> new UserNotFoundException(String.format("Organizer with id %s not found", organizerId)));

        List<TicketType> ticketTypesToCreate = createEventRequest.getTicketTypes().stream().map(ticketType -> {
            TicketType ticketTypeToCreate = new TicketType();
            ticketTypeToCreate.setName(ticketType.getName());
            ticketTypeToCreate.setPrice(ticketType.getPrice());
            ticketTypeToCreate.setDescription(ticketType.getDescription());
            ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
            return ticketTypeToCreate;
        }).toList();

        Event eventToCreate = new Event();
        eventToCreate.setName(createEventRequest.getName());
        eventToCreate.setStartDate(createEventRequest.getStartDate());
        eventToCreate.setEndDate(createEventRequest.getEndDate());
        eventToCreate.setVenue(createEventRequest.getVenue());
        eventToCreate.setSalesStartDate(createEventRequest.getSalesStartDate());
        eventToCreate.setSalesEndDate(createEventRequest.getSalesEndDate());
        eventToCreate.setStatus(createEventRequest.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);       
    }
}
