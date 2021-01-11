package com.link.convert.service;

import org.springframework.stereotype.Service;

@Service
public interface ConversionLogHistoryService {

    void saveConversionLogHistory(String request, String response);

}
