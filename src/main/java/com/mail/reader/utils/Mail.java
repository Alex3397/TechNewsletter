package com.mail.reader.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

@Component
public class Mail {

    @SneakyThrows
    public Store setupStore() {
        // create properties
        Properties properties = new Properties();

        properties.put("mail.imap.host", System.getenv("IMAP_HOST"));
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");
        properties.put("mail.imap.ssl.trust", System.getenv("IMAP_HOST"));
        properties.put("mail.imap.sasl.mechanisms", "XOAUTH2");

        Session emailSession = Session.getDefaultInstance(properties);

        // create the imap store object and connect to the imap server
        Store store = emailSession.getStore("imaps");
        store.connect(System.getenv("IMAP_HOST"), System.getenv("HOST_EMAIL"), System.getenv("HOST_PASSWORD"));

        return store;
    }
}
