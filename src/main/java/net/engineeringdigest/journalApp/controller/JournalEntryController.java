package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    // This class will handle journal entry operations
    // such as creating, reading, updating, and deleting entries.

 private Map<Long, JournalEntry> journalEntries=new HashMap<>();

 @GetMapping
 public ArrayList<JournalEntry> getAll(){
     return new ArrayList<>(journalEntries.values());
 }
 @PostMapping
 public boolean createEntry(@RequestBody JournalEntry entry) {

     journalEntries.put(entry.getId(), entry);
     return true;
 }
 @GetMapping("id/{myId}")
 public JournalEntry getJournalEntryById(@PathVariable Long myId) {
     return journalEntries.get(myId);
 }
 @DeleteMapping("id/{myId}")
 public boolean deleteJournalEntryById(@PathVariable Long myId) {

        if (journalEntries.containsKey(myId)) {
            journalEntries.remove(myId);
            return true;
        } else {
            return false;
        }
 }

 @PutMapping("id/{myId}")
 public boolean updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry entry) {
        if (journalEntries.containsKey(myId)) {
            journalEntries.put(myId, entry);
            return true;
        } else {
            return false;
        }
    }
}


