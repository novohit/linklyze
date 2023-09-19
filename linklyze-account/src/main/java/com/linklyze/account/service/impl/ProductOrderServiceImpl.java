package com.linklyze.account.service.impl;

import com.linklyze.account.model.ProductOrderDO;
import com.linklyze.account.mapper.ProductOrderMapper;
import com.linklyze.account.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-09-19
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {

}
