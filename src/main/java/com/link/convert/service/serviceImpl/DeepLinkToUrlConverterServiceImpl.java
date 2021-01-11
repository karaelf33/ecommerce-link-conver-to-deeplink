package com.link.convert.service.serviceImpl;


import com.link.convert.common.CommonConstantsAndOperations;
import com.link.convert.entity.Category;
import com.link.convert.entity.Product;
import com.link.convert.repository.CategoryRepository;
import com.link.convert.repository.ProductRepository;
import com.link.convert.service.ConversionLogHistoryService;
import com.link.convert.service.DeepLinkToUrlConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeepLinkToUrlConverterServiceImpl implements DeepLinkToUrlConverterService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ConversionLogHistoryService conversionLogHistoryService;

    @Autowired
    DeepLinkToUrlConverterServiceImpl(ProductRepository productRepository,
                                      CategoryRepository categoryRepository,
                                      ConversionLogHistoryService conversionLogHistoryService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.conversionLogHistoryService = conversionLogHistoryService;
    }


    @Override
    public String convertDeepLinkToUrl(String page, String contentId, String campaignId, String merchantId, String query, String request) {

        if (page.equals("Product")) {

            Optional<Product> optionalProduct = productRepository.findByContentId(contentId);

            if (!optionalProduct.isPresent()) {
                conversionLogHistoryService.saveConversionLogHistory(request, CommonConstantsAndOperations.HOME_PAGE_FOR_URL);
                return CommonConstantsAndOperations.HOME_PAGE_FOR_URL;
            }

            boolean isCampaignIdMatch = CommonConstantsAndOperations.isProductsBoutiqueIdMatched(optionalProduct.get(), campaignId);
            boolean isMerchantIdMatch = CommonConstantsAndOperations.isProductsMerchantIdMatched(optionalProduct.get(), merchantId);


            if (!isCampaignIdMatch && !isMerchantIdMatch) {
                conversionLogHistoryService.saveConversionLogHistory(request, CommonConstantsAndOperations.HOME_PAGE_FOR_URL);
                return CommonConstantsAndOperations.HOME_PAGE_FOR_URL;
            }

            String productsBrandName = optionalProduct.get().getBrand().getBrandName();
            String productsName = optionalProduct.get().getName();

            return makeConversionToUrl(contentId, merchantId, campaignId, productsBrandName, productsName);

        } else if (page.equals("Search")) {

            Optional<Category> categoryOptional = categoryRepository.findCategoryByCategoryName(query);

            if (categoryOptional.isPresent()) {
                String response = CommonConstantsAndOperations.HOME_PAGE_FOR_URL + "tum--urunler?q=" + query;
                conversionLogHistoryService.saveConversionLogHistory(request, response);
                return response;
            }
        }
        conversionLogHistoryService.saveConversionLogHistory(request, CommonConstantsAndOperations.HOME_PAGE_FOR_URL);
        return CommonConstantsAndOperations.HOME_PAGE_FOR_URL;
    }

    private String makeConversionToUrl(String contentId, String merchantId, String campaignId, String brandName, String productName) {

        StringBuilder result = new StringBuilder(CommonConstantsAndOperations.HOME_PAGE_FOR_URL);
        result.append(brandName);
        result.append("/");
        result.append(productName);
        result.append(CommonConstantsAndOperations.PRODUCT_DETAIL_PREFIX);
        result.append(contentId);
        result.append("?boutiqueId=").append(campaignId);
        result.append("&merchantId=").append(merchantId);

        return result.toString();

    }
}
