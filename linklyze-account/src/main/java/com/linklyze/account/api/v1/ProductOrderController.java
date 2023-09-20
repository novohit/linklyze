package com.linklyze.account.api.v1;


import com.linklyze.account.api.v1.request.ProductOrderPageRequest;
import com.linklyze.account.service.ProductOrderService;
import com.linklyze.common.model.vo.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author novo
* @since 2023-09-19
*/
@RestController
@RequestMapping("/product-order/v1")
public class ProductOrderController {

    private final  ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/page")
    public Resp<Void> page(@RequestBody @Validated ProductOrderPageRequest pageRequest) {
        return null;
    }
}
