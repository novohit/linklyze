package com.linklyze.account.api.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.account.api.v1.request.ProductOrderPageRequest;
import com.linklyze.account.api.v1.response.ProductOrderResponse;
import com.linklyze.account.model.ProductOrderDO;
import com.linklyze.account.service.ProductOrderService;
import com.linklyze.common.model.vo.PageResponse;
import com.linklyze.common.model.vo.Response;
import com.linklyze.common.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author novo
 * @since 2023-09-19
 */
@RestController
@RequestMapping("/product-order/v1")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/page")
    public Response<PageResponse<ProductOrderResponse>> page(@RequestBody @Validated ProductOrderPageRequest pageRequest) {
        Page<ProductOrderDO> page = this.productOrderService.page(pageRequest);
        List<ProductOrderDO> records = page.getRecords();

        List<ProductOrderResponse> responseList = records.stream()
                .map(productOrderDO -> {
                    ProductOrderResponse response = new ProductOrderResponse();
                    BeanUtils.copyProperties(productOrderDO, response);
                    return response;
                }).collect(Collectors.toList());

        PageResponse<ProductOrderResponse> pageResponse = new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), responseList);
        return Response.success(pageResponse);
    }
}
