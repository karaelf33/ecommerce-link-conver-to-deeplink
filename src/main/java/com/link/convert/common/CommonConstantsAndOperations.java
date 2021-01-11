package com.link.convert.common;


import com.link.convert.entity.Product;

public class CommonConstantsAndOperations {

    CommonConstantsAndOperations() {

    }

    public static final String HOME_PAGE_FOR_URL = "https://www.yusufbestas.com";
    public static final String HOME_PAGE_FOR_DEEP_LINK = "yb://?Page=Home";
    public static final String PRODUCT_DETAIL_PREFIX = "-p-";

    public static boolean isProductsBoutiqueIdMatched(Product product, String boutiqueId) {

        return product.getBoutiques().stream()
                .map(boutique -> String.valueOf(boutique.getBoutiqueId())).
                        anyMatch(boutiqueIdFromList -> boutiqueIdFromList.equals(boutiqueId));
    }

    public static boolean isProductsMerchantIdMatched(Product product, String merchantId) {

        return product.getMerchants().stream()
                .map(merchant -> String.valueOf(merchant.getMerchantId())).
                        anyMatch(merchantIdFromList -> merchantIdFromList.equals(merchantId));
    }

}
