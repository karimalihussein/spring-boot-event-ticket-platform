package com.personal.tickets.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.personal.tickets.domain.Enums.EventStatusEnum;   
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {
    @NotBlank(message = "Event name is required")
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotBlank(message = "Venue information is required")
    private String venue;
    private LocalDateTime salesStartDate;
    private LocalDateTime salesEndDate;
    @NotBlank(message = "Event status is required")
    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;
    @NotEmpty(message = "at least one ticket type is required")
    @Valid
    private List<CreateTicketTypeRequestDto> ticketTypes;
}
