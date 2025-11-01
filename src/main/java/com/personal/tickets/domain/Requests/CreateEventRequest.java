package com.personal.tickets.domain.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime; 

import com.personal.tickets.domain.Enums.EventStatusEnum;
import com.personal.tickets.domain.Entities.User;

import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequest {
    private String name;    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String venue;
    private LocalDateTime salesStartDate;
    private LocalDateTime salesEndDate;
    private EventStatusEnum status;
    private User organizer;
    private List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();
}
