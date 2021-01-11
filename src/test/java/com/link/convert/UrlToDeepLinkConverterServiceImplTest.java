package com.link.convert;

import com.link.convert.common.CommonConstantsAndOperations;
import com.link.convert.entity.Brand;
import com.link.convert.entity.Category;
import com.link.convert.entity.Product;
import com.link.convert.repository.BrandRepository;
import com.link.convert.repository.CategoryRepository;
import com.link.convert.repository.ProductRepository;
import com.link.convert.service.ConversionLogHistoryService;
import com.link.convert.service.serviceImpl.UrlToDeepLinkConverterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UrlToDeepLinkConverterServiceImplTest {

    ProductRepository productRepositoryMock;
    BrandRepository brandRepositoryMock;
    CategoryRepository categoryRepositoryMock;
    ConversionLogHistoryService conversionLogHistoryServiceMock;

    String notNullBrandOrCategoryName = "casio";
    String nullProductName = null;
    String notNullProductName = "casio-erkek-kol-saati";
    String notNullContentId = "42424";
    String notNullBoutiqueId = "7665757";
    String notNullMerchantId = "3453633643";
    String notNullRequest = "www.yusufbestas.com";
    CommonConstantsAndOperations commonConstantsAndOperationsMock = mock(CommonConstantsAndOperations.class);


    @BeforeEach
    void setUp() {
        productRepositoryMock = mock(ProductRepository.class);
        brandRepositoryMock = mock(BrandRepository.class);
        categoryRepositoryMock = mock(CategoryRepository.class);
        conversionLogHistoryServiceMock = mock(ConversionLogHistoryService.class);


    }

    @Test
    void anyMandatoryParameterIsNullTest() {
        UrlToDeepLinkConverterServiceImpl urlToDeepLinkConverterService = new UrlToDeepLinkConverterServiceImpl(
                productRepositoryMock,
                brandRepositoryMock,
                categoryRepositoryMock,
                conversionLogHistoryServiceMock);
        assert (urlToDeepLinkConverterService.convertUrlToDeeplink(notNullBrandOrCategoryName,
                nullProductName,
                notNullContentId,
                notNullBoutiqueId,
                notNullMerchantId,
                notNullRequest)).equals(CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK);
    }

    @Test
    void mandatoryFieldsAreNotNullButProductNotFound() {
        UrlToDeepLinkConverterServiceImpl urlToDeepLinkConverterService = new UrlToDeepLinkConverterServiceImpl(
                productRepositoryMock,
                brandRepositoryMock,
                categoryRepositoryMock,
                conversionLogHistoryServiceMock);

        when(productRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());


        assert (urlToDeepLinkConverterService.convertUrlToDeeplink(notNullBrandOrCategoryName,
                nullProductName,
                notNullContentId,
                notNullBoutiqueId,
                notNullMerchantId,
                notNullRequest)).equals(CommonConstantsAndOperations.HOME_PAGE_FOR_DEEP_LINK);
    }

    @Test
    void successScenario() {
        UrlToDeepLinkConverterServiceImpl urlToDeepLinkConverterService = new UrlToDeepLinkConverterServiceImpl(
                productRepositoryMock,
                brandRepositoryMock,
                categoryRepositoryMock,
                conversionLogHistoryServiceMock);

        Category categoryMock = new Category();
        categoryMock.setCategoryName("casio");
        Brand brand = new Brand();
        brand.setBrandName("casio");

        Product product = new Product();

        product.getCategories().add(categoryMock);
        product.setContentId(notNullContentId);
        product.setBrand(brand);

        Optional<Product> productOptional = Optional.of(product);

        when(productRepositoryMock.findByName(anyString())).thenReturn(productOptional);

        assert (urlToDeepLinkConverterService.convertUrlToDeeplink(notNullBrandOrCategoryName,
                notNullProductName,
                notNullContentId,
                notNullBoutiqueId,
                notNullMerchantId,
                notNullRequest)).equals("yb://?Page=Product&ContentId=42424");
    }


}