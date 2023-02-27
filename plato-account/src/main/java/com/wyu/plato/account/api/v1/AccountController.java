package com.wyu.plato.account.api.v1;

import com.wyu.plato.account.service.FileService;
import com.wyu.plato.common.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/upload")
    public Resp uploadImage(MultipartFile file) {
        String imageUrl = this.fileService.uploadImage(file);
        // TODO
        return Resp.success(imageUrl);
    }
}
