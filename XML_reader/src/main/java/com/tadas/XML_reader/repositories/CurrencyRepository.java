package com.tadas.XML_reader.repositories;

import com.tadas.XML_reader.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

  @Query(value = "SELECT * FROM currency WHERE CONCAT(code, ' ', nameEN, ' ', nameLT, ' ', number) LIKE %?1% ", nativeQuery = true)
  List<Currency> search(String keyword);

  Optional<Currency> findByCode(String code);

}
