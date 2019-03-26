package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntryBuilder {
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;
    private long id;

    public static TimeEntryBuilder builder() {
        return new TimeEntryBuilder();
    }

    public static TimeEntryBuilder builder(TimeEntry timeEntry) {
        return builder().projectId(timeEntry.getProjectId())
                .date(timeEntry.getDate())
                .hours(timeEntry.getHours())
                .userId(timeEntry.getUserId())
                .id(timeEntry.getId());

    }

    public TimeEntryBuilder projectId(long projectId) {
        this.projectId = projectId;
        return this;
    }

    public TimeEntryBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    public TimeEntryBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public TimeEntryBuilder hours(int hours) {
        this.hours = hours;
        return this;
    }

    public TimeEntryBuilder id(long id) {
        this.id = id;
        return this;
    }

    public TimeEntry build() {
        return new TimeEntry(id, projectId, userId, date, hours);
    }
}