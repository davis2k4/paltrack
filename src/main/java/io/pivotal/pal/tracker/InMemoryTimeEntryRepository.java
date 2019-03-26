package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private final List<TimeEntry> data = new ArrayList<>();
    int currentId = 1;

    public InMemoryTimeEntryRepository() {
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry te = new TimeEntry(currentId++,timeEntry);
        data.add(te);
        return te;

    }

    @Override
    public TimeEntry find(long id) {
        return data.stream().filter(te -> te.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        return data;
    }


    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry te = find(id);
        if(te != null){
            te.setDate(timeEntry.getDate());
            te.setHours(timeEntry.getHours());
            te.setProjectId(timeEntry.getProjectId());
            te.setUserId(timeEntry.getUserId());
        }
        return te;
    }

    @Override
    public void delete(long id) {
        data.removeIf(t -> t.getId() == id);
    }
}
