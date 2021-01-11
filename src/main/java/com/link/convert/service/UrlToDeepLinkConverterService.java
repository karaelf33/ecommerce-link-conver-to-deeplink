package com.link.convert.service;

import org.springframework.stereotype.Service;

@Service
public interface UrlToDeepLinkConverterService {
    String convertUrlToDeeplink(String brandNameOrCategoryName, String productName, String contentId, String boutiqueId, String merchantId, String request);

    String convertUrlDeepLinkForSearchPage(String brandNameOrCategoryName,String request);
}
