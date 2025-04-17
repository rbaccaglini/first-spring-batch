package com.example.batch_processing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {

    // tag::readerwriterprocessor[]

    @Bean
    public JdbcCursorItemReader<Person> reader(@Qualifier("sourceDS") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("personItemReader")
                .dataSource(dataSource)
                .sql("SELECT name, last_name FROM people_source")
                .rowMapper((rs, rowNum) -> {
                    String firstName = rs.getString("name");
                    String lastName = rs.getString("last_name");
                    return new Person(firstName, lastName);
                })
                .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(@Qualifier("applicationDS") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      JdbcCursorItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    // end::jobstep[]
}
