package com.example.springbatchdemo.springbatchconfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//决策器的使用
//决策器的使用更多的是添加条件，如果满足条件一那么就执行step1，如果满足条件二就执行条件二。条件由自己进行定义
@Configuration
@EnableBatchProcessing
public class DeciderDemoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step deciderStep01() {
        return stepBuilderFactory.get("deciderStep01").tasklet((contribution, chunkContext) -> {
            System.out.println("deciderStep01");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step deciderStep02() {
        return stepBuilderFactory.get("deciderStep02").tasklet((contribution, chunkContext) -> {
            System.out.println("deciderStep02");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step deciderStep03() {
        return stepBuilderFactory.get("deciderStep03").tasklet((contribution, chunkContext) -> {
            System.out.println("deciderStep03");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public MyDecider myDecider() {
        return new MyDecider();
    }

    @Bean
    public Job deciderJobDemo04() {
        return jobBuilderFactory.get("deciderJobDemo04")
                .start(deciderStep01())
                .next(myDecider())
                .from(myDecider()).on("偶数").to(deciderStep02())
                .from(myDecider()).on("奇数").to(deciderStep03())
                .end().build();
    }

    @Bean
    public Job deciderJobDemo07() {
        return jobBuilderFactory.get("deciderJobDemo07")
                .start(deciderStep01())
                .next(myDecider())
                .from(myDecider()).on("偶数").to(deciderStep02())
                .from(myDecider()).on("奇数").to(deciderStep03())
                .from(deciderStep03()).on("*").to(myDecider())
                .end().build();
    }
}
