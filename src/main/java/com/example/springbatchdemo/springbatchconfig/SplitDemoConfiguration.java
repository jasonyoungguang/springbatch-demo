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
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class SplitDemoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step splitStepDemo01() {
        return stepBuilderFactory.get("splitStepDemo01").tasklet((contribution, chunkContext) -> {
            System.out.println("splitStepDemo01");
            Thread.sleep(1000 * 3);
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step splitStepDemo02() {
        return stepBuilderFactory.get("splitStepDemo02").tasklet((contribution, chunkContext) -> {
            System.out.println("splitStepDemo02");
            Thread.sleep(1000 * 3);
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step splitStepDemo03() {
        return stepBuilderFactory.get("splitStepDemo03").tasklet((contribution, chunkContext) -> {
            System.out.println("splitStepDemo03");
            Thread.sleep(1000 * 3);
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Flow splitFlowDemo01() {
        return new FlowBuilder<Flow>("splitFlowDemo01").start(splitStepDemo01()).build();
    }


    @Bean
    public Flow splitFlowDemo02() {
        return new FlowBuilder<Flow>("splitFlowDemo02").start(splitStepDemo02()).next(splitStepDemo03()).build();
    }

    @Bean
    public Job splitDemoJob01() {
        return jobBuilderFactory.get("splitDemoJob01").start(splitFlowDemo01())
                .split(new SimpleAsyncTaskExecutor()).add(splitFlowDemo02()).end().build();
    }
}
