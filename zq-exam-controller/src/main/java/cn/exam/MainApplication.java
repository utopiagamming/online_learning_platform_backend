package cn.exam;

import cn.exam.controller.DistributionVectorCalcController;
import cn.exam.controller.PersonalizationController;
import cn.exam.enums.SuccessorMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
//@EnableAsync
@MapperScan("cn.exam.dao.mapper")
@EnableScheduling
public class MainApplication{
//public class MainApplication implements CommandLineRunner{
//    @Autowired
//    private DistributionVectorCalcController distributionVectorCalcController;
//
//    @Override
//    public void run(String... args) throws Exception{
//        SuccessorMap.initializing();
//        distributionVectorCalcController.videoHashingTest();
//        // distributionVectorCalcController.showRecommendResult("person3",90);
//    }

//    @Autowired
//    private PersonalizationController personalizationController;
//
//    @Override
//    public void run(String... args) throws Exception{
//        SuccessorMap.initializing();
//        personalizationController.createPersonalRecommendationPaper("person3");
//    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
