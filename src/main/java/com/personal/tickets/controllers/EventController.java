package com.personal.tickets.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.tickets.mappers.EventMapper;
import com.personal.tickets.services.EventService;
import com.personal.tickets.dtos.CreateEventRequestDto;
import com.personal.tickets.dtos.CreateEventResponseDto;
import com.personal.tickets.domain.Requests.CreateEventRequest;
import com.personal.tickets.domain.Entities.Event;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;


    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
        @AuthenticationPrincipal Jwt jwt,
        @Valid @RequestBody CreateEventRequestDto createEventRequestDto
    ) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID organizerId = UUID.fromString(jwt.getSubject());
        Event event = eventService.createEvent(organizerId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(event);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }
}
