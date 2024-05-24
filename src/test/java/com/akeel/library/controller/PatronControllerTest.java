package com.akeel.library.controller;

import com.akeel.library.data.TestData;
import com.akeel.library.dto.PatronDto;
import com.akeel.library.mapping.PatronMapper;
import com.akeel.library.service.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;


import static com.akeel.library.data.TestData.*;
import static com.akeel.library.mapping.Mapping.toPatronDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatronControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;
    PatronService patronService;

    @BeforeEach
    void setUp() {
        patronService = mock(PatronService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PatronController(patronService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getPatronsWithFilter() throws Exception {

        String name = "ali";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("name", name);
        List ali_patrons = patronsDto().stream().filter(patronDto -> patronDto.getName().contains(name)).toList();
        Pageable pageable = PageRequest.of(0, 10);
        Page<PatronDto> patronDtoPage = new PageImpl<>(ali_patrons, pageable, ali_patrons.size());
        when(patronService.findByNameContaining(name, pageable)).thenReturn(patronDtoPage);
        mockMvc.perform(get("/api/patrons").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(ali_patrons.size()))

                .andDo(print());
    }

    @Test
    void getAllPatrons() throws Exception {
        when(patronService.findAll()).thenReturn(patronsDto());
        mockMvc.perform(get("/api/patrons/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(patronsDto().size()))
                .andDo(print());
    }

    @Test
    void getPatronById() throws Exception {
        long id = 1L;
        PatronDto patronDto = new PatronDto(1L, "ali", "aliakeel@yahoo.com");

        when(patronService.findById(id)).thenReturn(Optional.of(patronDto));
        mockMvc.perform(get("/api/patrons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(patronDto.getName()))
                .andExpect(jsonPath("$.contactInfo").value(patronDto.getContactInfo()))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundPatron() throws Exception {
        long id = 1L;

        when(patronService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/patrons/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createPatron() throws Exception {
        when(patronService.save(any())).thenReturn(PatronMapper.INSTANCE.PatronToPatronDto(patron()).setId(1L));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toPatronDto(patron()))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(toPatronDto(patron()).setId(1L))))
                .andDo(print());
    }

    @Test
    void updatePatron() throws Exception {
        long id = 1L;

        PatronDto patronDto = new PatronDto(id, "ali", "ali@g.com");
        PatronDto updatedpatronDto = new PatronDto(id, "ali Updated", "ali_Updated@g.com");


        when(patronService.findById(id)).thenReturn(Optional.of(patronDto));
        when(patronService.update(any(), any(PatronDto.class))).thenReturn(Optional.of(updatedpatronDto));

        mockMvc.perform(put("/api/patrons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedpatronDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedpatronDto.getName()))
                .andExpect(jsonPath("$.contactInfo").value(updatedpatronDto.getContactInfo()))
                .andDo(print());
    }

    @Test
    void deletePatron() throws Exception {
        long id = 1L;

        when(patronService.delete(any())).thenReturn(true);
        mockMvc.perform(delete("/api/patrons/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}