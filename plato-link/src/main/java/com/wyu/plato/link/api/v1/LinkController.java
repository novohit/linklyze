package com.wyu.plato.link.api.v1;


import com.wyu.plato.common.util.Resp;
import com.wyu.plato.link.api.v1.request.LinkCreateRequest;
import com.wyu.plato.link.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RestController;

/**
* @author novo
* @since 2023-03-11
*/
@RestController
@RequestMapping("/link/v1")
@Validated
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping
    public Resp create(@RequestBody @Validated LinkCreateRequest linkCreateRequest) {
        this.linkService.create(linkCreateRequest);
        return Resp.success();
    }

}
