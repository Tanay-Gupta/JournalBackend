package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry entry, String userName) {

        try {
            User user= userService.findByUserName(userName);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved=journalEntryRepository.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e) {
            throw new RuntimeException("Error saving journal entry: " + e.getMessage(), e);
        }

    }
    public void saveEntry(JournalEntry entry) {
        journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public void deleteJournalEntryById(ObjectId myId, String userName) {

        try{
            User user = userService.findByUserName(userName);
            boolean removed= user.getJournalEntries().removeIf( x -> x.getId().equals(myId));

            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(myId);

            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error deleting journal entry: " + e.getMessage(), e);
        }


    }


}
