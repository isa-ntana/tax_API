package com.zup.br.taxes_management.repositories;

import com.zup.br.taxes_management.models.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, Long> {
}
