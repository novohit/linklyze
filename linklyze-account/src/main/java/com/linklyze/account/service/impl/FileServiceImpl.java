package com.linklyze.account.service.impl;

import com.linklyze.account.component.UploadComponent;
import com.linklyze.account.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author novo
 * @since 2023-02-27 22:49
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private UploadComponent uploadComponent;

    @Override
    public String uploadImage(MultipartFile file) {
        return this.uploadComponent.upload(file);
    }
}
