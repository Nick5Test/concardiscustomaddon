package com.alten.testsigma.addons.android;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class ExtractDataFromYopmail {
    public static void main(String[] args) {
        // Configurazione delle proprietà per accedere alla casella di posta di YOPmail
        String host = "pop.yopmail.com";
        String username = "carte_debito@yopmail.com";

        try {
            // Configurazione delle proprietà della sessione di posta
            Properties props = new Properties();
            props.put("mail.store.protocol", "pop3");
            props.put("mail.pop3.host", host);
            props.put("mail.pop3.port", "110");

            // Creazione dell'oggetto Session
            Session session = Session.getDefaultInstance(props);

            // Connessione al server POP3 di YOPmail
            Store store = session.getStore("pop3");
            store.connect(host, 110, username, null);

            // Apertura della cartella "INBOX"
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            // Recupero dei messaggi
            javax.mail.Message[] messages = inbox.getMessages();

            // Analisi dei messaggi
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String subject = message.getSubject();

                // Verifica del soggetto del messaggio
                if (subject.equals("Soggetto della mail di interesse")) {
                    // Estrazione del contenuto del messaggio
                    Object content = message.getContent();

                    if (content instanceof String) {
                        // Il messaggio è un testo semplice
                        String body = (String) content;
                        // Effettua l'analisi del corpo del messaggio e recupera il valore desiderato
                        // Esempio: valore = body.substring(10, 20);
                    } else if (content instanceof MimeMultipart) {
                        // Il messaggio contiene parti multiple
                        MimeMultipart multiPart = (MimeMultipart) content;
                        // Effettua l'analisi delle parti e recupera il valore desiderato
                    }
                }
            }

            // Chiusura delle risorse
            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
