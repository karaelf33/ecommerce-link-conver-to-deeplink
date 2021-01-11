package com.link.convert.service.serviceImpl;


import com.link.convert.entity.ConversionLogHistory;
import com.link.convert.repository.ConversionLogHistoryRepository;
import com.link.convert.service.ConversionLogHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversionLogHistoryServiceImpl implements ConversionLogHistoryService {

    ConversionLogHistoryRepository conversionLogHistoryRepository;

    @Autowired
    ConversionLogHistoryServiceImpl(ConversionLogHistoryRepository conversionLogHistoryRepository) {
        this.conversionLogHistoryRepository = conversionLogHistoryRepository;
    }


    @Override
    @Transactional
    public void saveConversionLogHistory(String request, String response) {

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);

        ConversionLogHistory conversionLogHistory = new ConversionLogHistory();
        conversionLogHistory.setRequestHeader(request);
        conversionLogHistory.setResponseBody(response);
        conversionLogHistory.setConversionDate(date);
        conversionLogHistoryRepository.save(conversionLogHistory);

    }
}
