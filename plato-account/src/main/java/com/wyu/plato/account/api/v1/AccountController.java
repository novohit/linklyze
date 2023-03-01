package com.wyu.plato.account.api.v1;

import com.wyu.plato.account.api.v1.request.RegisterRequest;
import com.wyu.plato.account.service.AccountService;
import com.wyu.plato.account.service.FileService;
import com.wyu.plato.common.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
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
     * 用户注册
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public Resp register(@RequestBody RegisterRequest registerRequest) {
        this.accountService.register(registerRequest);
        return Resp.success();
    }
}
