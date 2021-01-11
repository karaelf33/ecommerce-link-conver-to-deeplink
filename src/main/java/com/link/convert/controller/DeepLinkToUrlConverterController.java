package com.link.convert.controller;

import com.link.convert.service.DeepLinkToUrlConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/yb:/")
public class DeepLinkToUrlConverterController {

    @Autowired
    DeepLinkToUrlConverterService deepLinkToUrlConverterService;


    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deepLinkToUrl(@RequestParam String page,
                                @RequestParam(required = false) String contentId,
                                @RequestParam(required = false) String campaignId,
                                @RequestParam(required = false) String merchantId,
                                @RequestParam(required = false) String query,
                                HttpServletRequest request) {

        return deepLinkToUrlConverterService.convertDeepLinkToUrl(page, contentId, campaignId, merchantId, query,request.getRequestURI());

    }


}
