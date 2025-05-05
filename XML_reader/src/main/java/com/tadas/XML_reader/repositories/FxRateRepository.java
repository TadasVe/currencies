package com.tadas.XML_reader.repositories;

import com.tadas.XML_reader.models.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FxRateRepository extends JpaRepository<FxRate, Long> {

  @Query(value = "SELECT * FROM fx_Rate WHERE CONCAT(date, ' ', to_Currency, ' ', to_Amount) LIKE %?1% ", nativeQuery = true)
  List<FxRate> search(String keyword);

  Optional<FxRate> findByToCurrency(String toCurrency);

}
