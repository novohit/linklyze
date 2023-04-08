package com.wyu.plato.visual.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceGroupVO {

    private List<BrowserStats> browserStats;

    private List<OsStats> osStats;

    private Long pvSum;

    private Long uvSum;
}
