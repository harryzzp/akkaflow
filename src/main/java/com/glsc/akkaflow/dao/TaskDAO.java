package com.glsc.akkaflow.dao;


import com.glsc.akkaflow.beans.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * DAO for handling {@link Task} creation.
 */
@Repository
public class TaskDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long createTask(final Task task) {

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection connection) -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO tasks (payload, updated" +
                                    ") VALUES(?, NOW())",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getPayload());

            return ps;
        }, holder);

        return holder.getKey().longValue();
    }

}
