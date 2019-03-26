package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private DataSource dataSource;
    private final JdbcTemplate t;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.t = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        t.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {
        try {
            return (TimeEntry) t.queryForObject("select id, project_id, user_id, date, hours from time_entries where id = ?",
                    new TimeEntryRowMapper(), id);
        }catch(IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        return t.query("select id, project_id, user_id, date, hours from time_entries",
                new TimeEntryRowMapper());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        t.update("update time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?",
                timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(), id);
        return find(id);
    }

    @Override
    public void delete(long id) {
        t.update("DELETE FROM time_entries WHERE id = ?",id);
    }
}
