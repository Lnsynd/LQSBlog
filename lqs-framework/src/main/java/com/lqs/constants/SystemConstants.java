package com.lqs.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  状态正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     *  友联审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 评论类型为文章评论
     */
    public static final String ARTICLE_COMMENT ="0";

    /**
     * 评论类型为友链评论
     */
    public static final String LINK_COMMENT ="1";
    /**
     * 菜单 用C表示
     */
    public static final String MENU ="C";
    /**
     * 按钮用F表示
     */
    public static final String BUTTON ="F";
    /**
     *  身份是管理员
     */
    public static final String ADMIN = "1";
    /**
     *  身份是普通用户
     */
    public static final String NORMAL_USER = "0";
    /**
     *  菜单为 父级菜单
     */
    public static final Long PARENT_MENU = Long.valueOf(0);
}
