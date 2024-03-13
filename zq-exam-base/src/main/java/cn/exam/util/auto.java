package cn.exam.util;

import org.junit.Test;

// 干啥使的：
// 没用到

public class auto {
    private static final String DRIVE = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/cat";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "zy010818";
    private static final String TABLE ="zj_paper_user";

    @Test
    public  void testDomain() throws Exception {
        AutoCodeUtil.autoDomain(URL,USERNAME,PASSWORD,TABLE,"cn.exam.domain.zj","zq-exam-domain");
        AutoCodeUtil.autoMapper(TABLE,"cn.exam.dao.mapper.zj","zq-exam-dao");
    }
}
