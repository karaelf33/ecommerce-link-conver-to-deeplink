package com.link.convert.service.serviceImpl;


import com.link.convert.common.CommonConstantsAndOperations;
import com.link.convert.entity.Category;
import com.link.convert.entity.Product;
import com.link.convert.repository.BrandRepository;
import com.link.convert.repository.CategoryRepository;
import com.link.convert.repository.ProductRepository;
import com.link.convert.service.ConversionLogHistoryService;
import com.link.convert.service.UrlToDeepLinkConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlToDeepLinkConverterServiceImpl implements UrlToDeepLinkConverterService {

    ProductRepository productRepository;
    BrandRepository brandRepository;
    CategoryRepository categoryRepository;
    ConversionLogHistoryService conversionLogHistoryService;

    @Autowired
    public UrlToDeepLinkConverterServiceImpl(ProductRepository productRepository,
                                      BrandRepository brandRepository,
                                      CategoryRepository categoryRepository,
                                      ConversionLogHistoryService conversionLogHistoryService) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.conversionLogHistoryService = conversionLogHistoryService;

    }

    @Override
    public String convertUrlToDeeplink(String brandNameOrCategoryName,
                                       String productName,
                                       String contentId,
                                       String boutiqueId,
                                       String merchantId,
                                       String request) {
        if (brandNameOrCategoryName != null && productName != null && contentId != null) {

            final Optional<Product> productOptional = productRepository.findByName(productName);

            if (!productOptional.isPresent()) {
                conversionLogHistoryService.saveConversionLogHistory(request, CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK);
                return CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK;
            }

            boolean isBoutiqueMatched = CommonConstantsAndOperations.isProductsBoutiqueIdMatched(productOptional.get(), boutiqueId);
            boolean isMerchantMatched = CommonConstantsAndOperations.isProductsMerchantIdMatched(productOptional.get(), merchantId);

            if (isProductsMandatoryFieldsMatched(productOptional.get(), brandNameOrCategoryName, contentId)) {

                String response = makeConversionToDeepLink(boutiqueId,
                        merchantId,
                        productOptional.get().getContentId(),
                        isBoutiqueMatched,
                        isMerchantMatched);
                conversionLogHistoryService.saveConversionLogHistory(request, response);
                return response;
            }
        }
        conversionLogHistoryService.saveConversionLogHistory(request, CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK);
        return CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK;
    }

    @Override
    public String convertUrlDeepLinkForSearchPage(String brandNameOrCategoryName, String request) {

        if (categoryRepository.findCategoryByCategoryName(brandNameOrCategoryName).isPresent() ||
                brandRepository.findBrandByBrandName(brandNameOrCategoryName).isPresent()
        ) {
            String response = convertUrlToDeepLinkForSearch(brandNameOrCategoryName);
            conversionLogHistoryService.saveConversionLogHistory(request, response);
            return response;
        }
        conversionLogHistoryService.saveConversionLogHistory(request, CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK);
        return CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK;

    }


    private String convertUrlToDeepLinkForSearch(String brandNameOrCategoryName) {

        return "yb://?Page=Search&Query=" + brandNameOrCategoryName;

    }

    private String makeConversionToDeepLink(String boutiqueId,
                                            String merchantId,
                                            String productsContentId,
                                            boolean isBoutiqueMatched,
                                            boolean isMerchantMatched) {
        StringBuilder deeplink = new StringBuilder("yb://?Page=Product&ContentId=");
        deeplink.append(productsContentId);
        if (isBoutiqueMatched) {
            deeplink.append("&CampaignId=");
            deeplink.append(boutiqueId);
        }
        if (isMerchantMatched) {
            deeplink.append("&MerchantId=");
            deeplink.append(merchantId);

        }

        return deeplink.toString();
    }

    private boolean isProductsMandatoryFieldsMatched(Product product,
                                                     String brandNameOrCategoryName,
                                                     String contentId) {
        boolean categoryMatched = product.getCategories().stream().
                map(Category::getCategoryName)
                .anyMatch(productsCategory -> productsCategory.equals(brandNameOrCategoryName));

        boolean isBrandMatched = product.getBrand().getBrandName().equals(brandNameOrCategoryName);

        if (categoryMatched || isBrandMatched) {
            return product.getContentId().equals(contentId);

        }
        return false;
    }
}
