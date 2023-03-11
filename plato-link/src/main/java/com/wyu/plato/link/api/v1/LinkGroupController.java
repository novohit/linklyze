package com.wyu.plato.link.api.v1;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

import org.springframework.web.bind.annotation.RestController;

/**
* @author novo
* @since 2023-03-11
*/
@RestController
@RequestMapping("/link-group/v1")
public class LinkGroupController {

//    @PostMapping("")
//    public CreatedVO create() {
//        return new CreatedVO();
//    }
//
//    @PutMapping("/{id}")
//    public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Long id) {
//        return new UpdatedVO();
//    }
//
//    @DeleteMapping("/{id}")
//    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
//        return new DeletedVO();
//    }
//
//    @GetMapping("/{id}")
//    public LinkGroupDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
//        return null;
//    }
//
//    @GetMapping("/page")
//    public PageResponseVO<LinkGroupDO> page(
//            @RequestParam(name = "count", required = false, defaultValue = "10")
//            @Min(value = 1, message = "{page.count.min}")
//            @Max(value = 30, message = "{page.count.max}") Long count,
//            @RequestParam(name = "page", required = false, defaultValue = "0")
//            @Min(value = 0, message = "{page.number.min}") Long page
//    ) {
//        return null;
//    }

}
