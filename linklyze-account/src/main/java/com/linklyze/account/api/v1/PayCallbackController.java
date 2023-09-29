package com.linklyze.account.api.v1;

import com.linklyze.account.service.strategy.PayStrategy;
import com.linklyze.account.service.strategy.PayStrategyFactory;
import com.linklyze.common.enums.PayType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api/pay/callback")
public class PayCallbackController {

    private final PayStrategyFactory payStrategyFactory;

    public PayCallbackController(PayStrategyFactory payStrategyFactory) {
        this.payStrategyFactory = payStrategyFactory;
    }

    @PostMapping
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("支付成功");
        String callback = payStrategyFactory.chooseStrategy(PayType.ALI_PAY)
                .callback(request, (map) -> {
                    System.out.println(map);
                });
        response.getWriter().println("success");
    }
}
