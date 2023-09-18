package com.wyu.plato.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author novo
 * @since 2023-02-27 18:48
 */
public interface FileService {

    String uploadImage(MultipartFile file);
}
