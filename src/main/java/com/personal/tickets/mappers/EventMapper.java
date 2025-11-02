package com.personal.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.personal.tickets.domain.Entities.Event;
import com.personal.tickets.domain.Requests.CreateEventRequest;
import com.personal.tickets.domain.Requests.CreateTicketTypeRequest;
import com.personal.tickets.dtos.CreateEventRequestDto;
import com.personal.tickets.dtos.CreateEventResponseDto;
import com.personal.tickets.dtos.CreateTicketTypeRequestDto;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);
}
