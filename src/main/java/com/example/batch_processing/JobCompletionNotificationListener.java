package com.example.batch_processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate applicationJdbcTemplate;

    public JobCompletionNotificationListener(@Qualifier("applicationJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.applicationJdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            applicationJdbcTemplate
                    .query("SELECT first_name, last_name FROM people", new DataClassRowMapper<>(Person.class))
                    .forEach(person -> log.info("Found <{}> in the database.", person));
        }
    }
}
