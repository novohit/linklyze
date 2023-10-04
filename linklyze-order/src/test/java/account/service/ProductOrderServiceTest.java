package account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.order.OrderApplication;
import com.linklyze.order.api.request.ProductOrderPageRequest;
import com.linklyze.order.model.ProductOrderDO;
import com.linklyze.common.enums.PayStateEnum;
import com.linklyze.common.util.CommonUtil;
import com.linklyze.order.service.ProductOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * @author novo
 * @since 2023-09-20
 */
@SpringBootTest(classes = OrderApplication.class)
@Slf4j
class ProductOrderServiceTest {

    @Autowired
    private ProductOrderService productOrderService;
    // https://stackoverflow.com/questions/51867650/junit-5-no-parameterresolver-registered-for-parameter


    @Test
    void create() {
        // 分表入库测试
        for (int i = 0; i < 2; i++) {
            ProductOrderDO productOrderDO = ProductOrderDO.builder()
                    .productTitle("1天")
                    .nickname("novohit")
                    .accountNo(10L + i)
                    .outTradeNo(CommonUtil.generateUUID())
                    .actualPayAmount(BigDecimal.valueOf(99L))
                    .state(PayStateEnum.PAID)
                    .productId(1L)
                    .build();

            int rows = productOrderService.create(productOrderDO);
            Assertions.assertEquals(1, rows);
        }
    }

    @Test
    void findByOutTradeNo() {
    }

    @Test
    void updateState() {
    }

    @Test
    void page() {
        ProductOrderPageRequest pageRequest = new ProductOrderPageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(10);

        Page<ProductOrderDO> orderList = productOrderService.page(pageRequest);
        log.info("{}", orderList);
    }
}
