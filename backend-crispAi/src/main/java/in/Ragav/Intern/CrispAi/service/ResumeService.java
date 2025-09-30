// src/main/java/com/example/interviewassistant/service/ResumeService.java

package in.Ragav.Intern.CrispAi.service;

import in.Ragav.Intern.CrispAi.entity.*;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeService {

    public Candidate parseResume(MultipartFile file) throws Exception {
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        AutoDetectParser parser = new AutoDetectParser();

        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, handler, metadata);
        }

        String text = handler.toString();
        Candidate candidate = new Candidate();

        // Regex patterns
        Pattern namePattern = Pattern.compile("Name:\\s*(.*)", Pattern.CASE_INSENSITIVE);
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Pattern phonePattern = Pattern.compile("(?:\\+\\d{1,3}[- ]?)?(\\d{10})");

        // Name
        Matcher nameMatcher = namePattern.matcher(text);
        if (nameMatcher.find()) {
            candidate.setName(nameMatcher.group(1).trim());
        } else {
            // fallback: take first non-empty line as name
            String[] lines = text.split("\\r?\\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    candidate.setName(line.trim());
                    break;
                }
            }
        }

        // Email
        Matcher emailMatcher = emailPattern.matcher(text);
        if (emailMatcher.find()) {
            candidate.setEmail(emailMatcher.group().trim());
        } else {
            candidate.setEmail("Not Found");
        }

        // Phone
        Matcher phoneMatcher = phonePattern.matcher(text);
        if (phoneMatcher.find()) {
            candidate.setPhone(phoneMatcher.group(1));
        } else {
            candidate.setPhone("Not Found");
        }

        return candidate;
    }
}
