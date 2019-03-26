package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private final Map<Long,TimeEntry> data = new ConcurrentHashMap<>();
    int currentId = 1;

    public InMemoryTimeEntryRepository() {
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry te = TimeEntryBuilder.builder(timeEntry).id(currentId++).build();
        data.put(te.getId(),te);
        return te;

    }

    @Override
    public TimeEntry find(long id) {
        return data.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(data.values());
    }


    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(data.get(id) != null){
            TimeEntry te = TimeEntryBuilder.builder(timeEntry).id(id).build();
            this.data.put(id,te);
            return te;
        }else{
            return null;
        }
    }

    @Override
    public void delete(long id) {
        data.remove(id);
    }
}
