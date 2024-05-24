package com.akeel.library.service.impl;

import com.akeel.library.dto.PatronDto;
import com.akeel.library.mapping.Mapping;
import com.akeel.library.model.Patron;
import com.akeel.library.repository.PatronJpaRepository;
import com.akeel.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronJpaRepository patronRepository;

    @Override
    public List<PatronDto> findAll() {

        return patronRepository
                .findAll()
                .stream()
                .map((patron -> Mapping.toPatronDto(patron))).toList();
    }

    @Override
    public Optional<PatronDto> findById(Long id) {
        return Optional.of(Mapping.toPatronDto(patronRepository.findById(id).get()));
    }

    @Override
    public Page<PatronDto> findByNameContaining(String name, Pageable pageable) {
        List<PatronDto> patronsDto = patronRepository
                .findByNameContaining(name, pageable)
                .stream().map(patron-> Mapping.toPatronDto(patron)).toList();
        return new PageImpl<>(patronsDto, pageable, patronsDto.size());
    }

    @Transactional
    public PatronDto save(PatronDto patron) {
        return Mapping.toPatronDto(patronRepository.save(Mapping.toPatron(patron)));
    }

    @Transactional
    public Optional<PatronDto> update(Long id, PatronDto patronDto) {
        Optional<Patron> existingPatron = patronRepository.findById(id);
        if (existingPatron.isPresent()) {
            Patron patron = Mapping.toPatron(patronDto);
            return Optional.of(Mapping.toPatronDto(patronRepository.save(patron)));
        } else {
            // Handle not found
            return null;
        }
    }

    @Transactional
    public boolean delete(Long id) {
        try {
            Optional<Patron> patron = patronRepository.findById(id);
            patronRepository.delete(patron.get());
            return true;
        } catch (Exception e) {

        }
        return false;

    }
}
