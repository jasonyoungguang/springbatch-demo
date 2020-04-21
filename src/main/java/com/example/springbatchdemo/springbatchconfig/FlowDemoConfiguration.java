package com.example.springbatchdemo.springbatchconfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//通篇看下来这里的flow的作用就是可以将step进行整合重复利用，进行多种组合然后进行变化
//这里要注意的是我们的Flow创建不再是直接调用Spring的对象管理器中的buildFactory进行创建的，是我们自己new的new FlowBuilder<Flow>("");
@Configuration
public class FlowDemoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step flowDemoStep1() {
        return stepBuilderFactory.get("flowDemoStep1").tasklet((contribution, chunkContext) -> {
            System.out.println("flowDemoStep1");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step flowDemoStep2() {
        return stepBuilderFactory.get("flowDemoStep2").tasklet((contribution, chunkContext) -> {
            System.out.println("flowDemoStep2");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step flowDemoStep3() {
        return stepBuilderFactory.get("flowDemoStep3").tasklet((contribution, chunkContext) -> {
            System.out.println("flowDemoStep3");
            return RepeatStatus.FINISHED;
        }).build();
    }

    //创建flow对象,flow的作用更像是说聚合step，整合各个step，然后让job去进行调用
    @Bean
    public Flow flowDemo() {
        return new FlowBuilder<Flow>("flowDemo").start(flowDemoStep1()).next(flowDemoStep2()).build();
    }

    //这里是启用job，job可以使用step进行启用，也可以使用flow进行启用，使用flow也就是使用flow整合的step
    @Bean
    public Job jobDemoFlow() {
        return jobBuilderFactory.get("jobDemoFlow").start(flowDemo()).next(flowDemoStep3()).end().build();
    }

    @Bean
    public Job jobDemoFlow02() {
        return jobBuilderFactory.get("jobDemoFlow02").start(flowDemo()).next(flowDemoStep3()).end().build();
    }
}
