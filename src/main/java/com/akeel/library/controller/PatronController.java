package com.akeel.library.controller;


import com.akeel.library.controller.api.PatronApi;
import com.akeel.library.dto.PatronDto;
import com.akeel.library.service.PatronService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patrons")
@Validated
public class PatronController implements PatronApi {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public ResponseEntity<Page<PatronDto>> getAllPatrons(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        Page<PatronDto> patrons = patronService.findByNameContaining(name.orElse(""), pageable);
        return ResponseEntity.ok(patrons);
    }


    @GetMapping("/all")
    @Cacheable(value = "patrons", key = "", sync = true)
    public ResponseEntity<List<PatronDto>> getAllPatrons() {
        List<PatronDto> patrons = patronService.findAll();
        return ResponseEntity.ok(patrons);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "patrons", key = "#id")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable Long id) {
        Optional<PatronDto> patron = patronService.findById(id);
        return patron.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    @CacheEvict(value = "patrons", allEntries = true)
    public ResponseEntity<PatronDto> createPatron(@Valid @RequestBody PatronDto patron) {
        PatronDto savedPatron = patronService.save(patron);
        return ResponseEntity.ok(savedPatron);
    }


    @PutMapping("/{id}")
    @CacheEvict(value = "patrons", allEntries = true)
    public ResponseEntity<PatronDto> updatePatron(@PathVariable Long id, @Valid @RequestBody PatronDto patronDetails) {
        return patronService.update(id, patronDetails).map(patron -> {
            return ResponseEntity.ok(patron);
        }).orElseGet(
                () -> ResponseEntity.notFound()
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "patrons", allEntries = true)
    public ResponseEntity<Object> deletePatron(@PathVariable Long id) {
        return (patronService.delete(id)?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build());
    }
}