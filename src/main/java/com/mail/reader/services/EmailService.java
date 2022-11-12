package com.mail.reader.services;

import com.mail.reader.objects.Email;
import com.mail.reader.repository.EmailRepository;
import com.mail.reader.utils.Mail;
import com.sun.mail.imap.IMAPBodyPart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FromStringTerm;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {
    private EmailRepository emailRepository;
    private Mail mail;

    public List<Email> findEmails() {
        System.out.println("Retriving from database");
        return emailRepository.findAll();
    }

    public ArrayList<Email> ReadEmails() {
        ArrayList<Email> emailsList = new ArrayList<>();
        ArrayList<Email> storedEmails = (ArrayList<Email>) findEmails();

        try {
            // create the inbox object and open it
            Store store = mail.setupStore();
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);

            // retrieve the messages from the folder
            Message[] messages = inbox.search(new FromStringTerm("newsletter@filipedeschamps.com.br"));

            if (messages.length > storedEmails.size()) {
                for (int i = storedEmails.size(), messagesLength = messages.length; i < messagesLength; i++) {
                    Email email = new Email();
                    email.setSubject(messages[i].getSubject());
                    MimeMultipart mmp = (MimeMultipart) messages[i].getContent();

                    ArrayList<String> bodyParts = new ArrayList<>();
                    for (int j = 0; j < mmp.getCount(); j++) {
                        IMAPBodyPart bodyPart = (IMAPBodyPart) mmp.getBodyPart(j);
                        bodyParts.add((String) bodyPart.getContent());
                    }

                    email.setBody(bodyParts);
                    emailsList.add(email);

                    if (emailRepository.findBySubject(email.getSubject()).isEmpty()) {
                        emailRepository.save(email);
                    }
                }
            } else {
                return storedEmails;
            }

            inbox.close(false);
            store.close();
            return emailsList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailsList;
    }

}
