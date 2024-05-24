package com.akeel.library.repository;

import com.akeel.library.model.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronJpaRepository extends JpaRepository<Patron, Long> {
    Page<Patron> findByNameContaining(String name, Pageable pageable);
}
