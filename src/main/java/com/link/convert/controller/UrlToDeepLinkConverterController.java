package com.link.convert.controller;


import com.link.convert.common.CommonConstantsAndOperations;
import com.link.convert.service.ConversionLogHistoryService;
import com.link.convert.service.UrlToDeepLinkConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/" + CommonConstantsAndOperations.HOME_PAGE_FOR_URL)
public class UrlToDeepLinkConverterController {


    @Autowired
    UrlToDeepLinkConverterService urlToDeepLinkConverterService;

    @Autowired
    ConversionLogHistoryService conversionLogHistoryService;

    @GetMapping(value = "/{brandNameOrCategoryName}/{productName}" + CommonConstantsAndOperations.PRODUCT_DETAIL_PREFIX + "{contentId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductDetail(@PathVariable String brandNameOrCategoryName,
                                   @PathVariable String productName,
                                   @PathVariable String contentId,
                                   @RequestParam(required = false) String boutiqueId,
                                   @RequestParam(required = false) String merchantId,
                                   HttpServletRequest request) {

        return urlToDeepLinkConverterService.convertUrlToDeeplink(brandNameOrCategoryName,
                productName, contentId, boutiqueId, merchantId, request.getRequestURI());
    }

    @GetMapping(value = "/tum--urunler",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductDetail(@RequestParam String q,
                                   HttpServletRequest request) {

        return urlToDeepLinkConverterService.convertUrlDeepLinkForSearchPage(q, request.getRequestURI());
    }

    @GetMapping(value = "/{notValidPath}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String goToHomePAge(@PathVariable String notValidPath,
                               HttpServletRequest request) {
        conversionLogHistoryService.saveConversionLogHistory(request.getRequestURI(), CommonConstantsAndOperations.HOME_PAGE_FOR_URL);
        return CommonConstantsAndOperations.HOME_PAGE_FOR_URL;
    }

}
