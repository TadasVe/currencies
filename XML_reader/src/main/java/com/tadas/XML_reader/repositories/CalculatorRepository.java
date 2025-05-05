package com.tadas.XML_reader.repositories;

import com.tadas.XML_reader.models.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatorRepository extends JpaRepository<FxRate, Long> {

}
