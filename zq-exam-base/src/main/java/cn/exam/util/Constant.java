package cn.exam.util;

import java.math.BigDecimal;

// 常量类

public class Constant {
    public static final String HZMNG_USER_SESSION = "kousi_user_session_info";//用户session

    /**
     * 默认每页数量
     */
    public static final int DEFAULT_PAGESIZE = 10;

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGENUM = 1;

    /**
     * 分页查询返回总条数
     */
    public static final String MAP_VALUE_COUNT = "count";

    /**
     * 分页查询返回结果
     */
    public static final String MAP_VALUE_LIST = "result";

    //默认的父类id指
    public static final String DEFAULT_PARENT_ID = "-1";//parent_id
    //一级菜单
    public static final Integer MENU_DEGREE_1 = 1;
    //二级菜单
    public static final Integer MENU_DEGREE_2 = 2;

    //----------error code----------//
    //数据处理失败异常
    public static final String DATABASE_FAILED_CODE = "9998";//数据处理失败code
    public static final String DATABASE_FAILED_MSG = "数据处理失败";//数据处理失败msg

    //100的BigDecimal类型
    public static final BigDecimal BIGDECIMAL_100 = new BigDecimal(100);
    public static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal("0.00");

    /**
     * Cookie名字:保存上次审核的位置[初审]
     */
    public static final String AUDIT_COOKIE_NAME = "last_audit_position";

    /**
     * 请求超时
     */
    public static final String SESSION_STATUS = "TIME_OUT";

    public static final String RULE_RETURN_CODE_ERROR = "返回编码不存在";

    public static final int KEY_IN_REDIS_TIME = 2 * 60 * 60;

    /**
     * 线程次中每个任务执行时的案件数量
     */
    public static final int EACH_TASK_CASE_COUNT = 20;

    /**
     * 案件批量查询每次查询的案件数量
     */
    public static final int EACH_BATCH_QUERY_CASE_COUNT = 500;

    /**
     *需要创建的线程数
     */
    public static final int THREAD_NUM_SHOULD_CREATE = Runtime.getRuntime().availableProcessors()*2;

    /**
     * 数据库每次批量插入的数据数据条数
     */
    public static final int DB_BATCH_INSERT_COUNT = 50;

    /**
     * 线上案件标识
     */
    public static final String ONLINE = "ONLINE";

    /**
     * 案件样例标识
     */
    public static final String SAMPLE = "SAMPLE";

}
