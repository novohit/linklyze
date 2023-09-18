package com.linklyze.account.api.v1;

import com.linklyze.account.api.v1.request.LoginRequest;
import com.linklyze.account.api.v1.request.RegisterRequest;
import com.linklyze.account.model.AccountDO;
import com.linklyze.account.service.AccountService;
import com.linklyze.account.service.FileService;
import com.wyu.plato.common.model.vo.Resp;
import com.wyu.plato.common.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户接口
 *
 * @author novo
 * @since 2023-02-27 18:45
 */
@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private FileService fileService;

    @Autowired
    private AccountService accountService;


    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Resp uploadImage(@RequestPart("file") MultipartFile file) {
        String imageUrl = this.fileService.uploadImage(file);
        return Resp.success(imageUrl);
    }


    /**
     * 用户登录
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public Resp<String> login(@RequestBody @Validated LoginRequest loginRequest) {
        String token = this.accountService.login(loginRequest);
        return Resp.success(token);
    }

    /**
     * @ignore
     * @return
     */
    @PostMapping("/test-token")
    public Resp<String> token() {
        String token = TokenUtil.generateAccessToken(1L);
        return Resp.success(token);
    }

    /**
     * @ignore
     * @param token
     * @return
     */
    @PostMapping("/test-token-verify")
    public Resp<Void> tokenVerify(String token) {
        Claims claims = TokenUtil.verifyToken(token);
        System.out.println(claims);
        return Resp.success();
    }

    /**
     * 用户注册
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public Resp<Void> register(@RequestBody @Validated RegisterRequest registerRequest) {
        this.accountService.register(registerRequest);
        return Resp.success();
    }


    @PostMapping("/{account_no}")
    public Resp<AccountDO> findByAccountNo(@PathVariable("account_no") Long accountNo) {
        AccountDO account = this.accountService.findByAccountNo(accountNo);
        return Resp.success(account);
    }
}
