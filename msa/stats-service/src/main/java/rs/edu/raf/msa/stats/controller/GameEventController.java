package rs.edu.raf.msa.stats.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GameEventController
{
    private LinkedList<String> events = new LinkedList<>();

    public synchronized void insertEvent(String event)
    {
        events.addFirst(event);
    }


    @GetMapping(value = "latest/{n}")
    public List<String> latestN(@PathVariable Integer n)
    {
        return events.stream().limit(n).collect(Collectors.toList());
    }

    @GetMapping(value = "latest")
    public List<String> latest()
    {
        return events;
    }

}
