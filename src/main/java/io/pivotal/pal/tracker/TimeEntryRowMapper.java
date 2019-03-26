package io.pivotal.pal.tracker;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class TimeEntryRowMapper implements RowMapper{
    public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {

        return TimeEntryBuilder.builder()
                .id(rs.getLong("id"))
                .projectId(rs.getLong("project_id"))
                .userId(rs.getLong("user_id"))
                .hours(rs.getInt("hours"))
                .date(rs.getTimestamp("date")
                        .toLocalDateTime().toLocalDate()).build();
    }
}
