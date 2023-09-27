package com.linklyze.account.api.v1;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.account.api.v1.request.PlaceOrderRequest;
import com.linklyze.account.api.v1.request.ProductOrderPageRequest;
import com.linklyze.account.api.v1.response.ProductOrderResponse;
import com.linklyze.account.model.ProductOrderDO;
import com.linklyze.account.service.ProductOrderService;
import com.linklyze.account.service.strategy.PayResponse;
import com.linklyze.common.config.AliPayProperties;
import com.linklyze.common.exception.BizException;
import com.linklyze.common.model.vo.PageResponse;
import com.linklyze.common.model.vo.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/place")
    public String placeOrder(@RequestBody @Validated PlaceOrderRequest request) {
        return productOrderService.placeOrder(request);
    }

    @Autowired
    private AliPayProperties aliPayProperties;

    @GetMapping("/test-pay")
    public String pay(@RequestParam("trade_no") String tradeNo) throws AlipayApiException {
//        AliPayRequest aliPayRequest = payRequest.getAliPayRequest();
        AlipayConfig alipayConfig = new AlipayConfig();
        BeanUtils.copyProperties(aliPayProperties, alipayConfig);
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setSubject("App支付测试Java"); //
        model.setBody("我是测试数据"); // 商品描述
        model.setTimeoutExpress("60m");
        model.setOutTradeNo(tradeNo);
        model.setTotalAmount("11");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setNotifyUrl(aliPayProperties.getNotifyUrl());
        request.setBizModel(model);
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
//            log.info("发起支付宝支付，订单号：{}，子订单号：{}，订单请求号：{}，订单金额：{} \n调用支付返回：\n\n{}\n",
//                    aliPayRequest.getOrderSn(),
//                    aliPayRequest.getOutOrderSn(),
//                    aliPayRequest.getOrderRequestId(),
//                    aliPayRequest.getTotalAmount(),
//                    JSONObject.toJSONString(response));
            if (!response.isSuccess()) {
                throw new BizException("调用支付宝发起支付异常");
            }
//            return new PayResponse(StrUtil.replace(StrUtil.replace(response.getBody(), "\"", "'"), "\n", ""));
            return response.getBody();
        } catch (AlipayApiException ex) {
            throw new BizException("调用支付宝支付异常");
        }
    }
}
