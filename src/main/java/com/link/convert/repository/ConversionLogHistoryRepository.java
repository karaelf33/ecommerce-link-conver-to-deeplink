package com.link.convert.repository;


import com.link.convert.entity.ConversionLogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionLogHistoryRepository  extends JpaRepository<ConversionLogHistory,Integer> {
}
