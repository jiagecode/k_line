package com.line.backstage.vo;

import lombok.Data;

import java.util.List;

/**
 * @author scxfsc
 */
@Data
public class MenuRouteVo {

    private String path;
    private String name;
    private Meta meta;
    private String redirect;
    private List<MenuRouteVo> children;

    @Data
    public static class Meta {
        private String title;
        private String icon;
    }
}
