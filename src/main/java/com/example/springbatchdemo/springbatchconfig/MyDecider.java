package com.example.springbatchdemo.springbatchconfig;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

//自定义decider
public class MyDecider implements JobExecutionDecider {

    private int count;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;
        System.out.println("======================：" + count);
        if (count % 2 == 0) {
            return new FlowExecutionStatus("偶数");
        } else {
            return new FlowExecutionStatus("奇数");
        }
    }
}
