package com.link.convert.service;

import org.springframework.stereotype.Service;

@Service
public interface DeepLinkToUrlConverterService  {
    String convertDeepLinkToUrl(String page,
                                String contentId,
                                String campaignId,
                                String merchantId,String query,
                                String request);
}
