package com.akeel.library.service;

import com.akeel.library.dto.PatronDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatronService {
    Page<PatronDto> findByNameContaining(String name, Pageable pageable);
    List<PatronDto> findAll();
    Optional<PatronDto> findById(Long id);
    PatronDto save(PatronDto patron);
    Optional<PatronDto> update(Long id, PatronDto patron);
    boolean delete(Long id);
}
