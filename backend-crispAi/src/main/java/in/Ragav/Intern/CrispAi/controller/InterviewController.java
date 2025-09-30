package in.Ragav.Intern.CrispAi.controller;

import in.Ragav.Intern.CrispAi.entity.Candidate;
import in.Ragav.Intern.CrispAi.repo.CandidateRepository;
import in.Ragav.Intern.CrispAi.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    private final CandidateRepository candidateRepository;
    private final AiService aiService;

    public InterviewController(CandidateRepository candidateRepository, AiService aiService) {
        this.candidateRepository = candidateRepository;
        this.aiService = aiService;
    }

    // Start interview: resets chat history
    @PostMapping("/start/{id}")
    public ResponseEntity<?> startInterview(@PathVariable Long id) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isEmpty()) return ResponseEntity.badRequest().body("Candidate not found");

        Candidate candidate = candidateOpt.get();
        candidate.setChatHistory("[]"); // empty JSON array
        candidate.setFinalScore(0);
        candidate.setSummary(null);
        candidateRepository.save(candidate);

        return ResponseEntity.ok(candidate);
    }

    // Get next question based on progress
    @GetMapping("/question/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable Long id) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isEmpty()) return ResponseEntity.badRequest().body("Candidate not found");

        Candidate candidate = candidateOpt.get();
        List<Map<String, Object>> chatHistory = new ArrayList<>();
        try {
            chatHistory = new ArrayList<>(Arrays.asList(new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(candidate.getChatHistory(), Map[].class)));
        } catch (Exception e) { /* ignore */ }

        String question = aiService.generateQuestion(chatHistory.size());
        if (question == null) return ResponseEntity.ok("No more questions");

        Map<String, Object> response = new HashMap<>();
        response.put("questionNumber", chatHistory.size() + 1);
        response.put("question", question);
        return ResponseEntity.ok(response);
    }

    // Submit answer
    @PostMapping("/answer/{id}")
    public ResponseEntity<?> submitAnswer(@PathVariable Long id,
                                          @RequestBody Map<String, String> payload) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isEmpty()) return ResponseEntity.badRequest().body("Candidate not found");

        Candidate candidate = candidateOpt.get();

        String question = payload.get("question");
        String answer = payload.get("answer");
        int score = aiService.scoreAnswer(question, answer);

        // Update chat history
        List<Map<String, Object>> chatHistory = new ArrayList<>();
        try {
            chatHistory = new ArrayList<>(Arrays.asList(new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(candidate.getChatHistory(), Map[].class)));
        } catch (Exception e) { /* ignore */ }

        Map<String, Object> qa = new HashMap<>();
        qa.put("question", question);
        qa.put("answer", answer);
        qa.put("score", score);
        chatHistory.add(qa);

        try {
            candidate.setChatHistory(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(chatHistory));
        } catch (Exception e) { /* ignore */ }

        candidateRepository.save(candidate);
        return ResponseEntity.ok(qa);
    }

    // Finish interview: calculate final score and summary
    @PostMapping("/finish/{id}")
    public ResponseEntity<?> finishInterview(@PathVariable Long id) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isEmpty()) return ResponseEntity.badRequest().body("Candidate not found");

        Candidate candidate = candidateOpt.get();

        List<Map<String, Object>> chatHistory = new ArrayList<>();
        try {
            chatHistory = new ArrayList<>(Arrays.asList(new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(candidate.getChatHistory(), Map[].class)));
        } catch (Exception e) { /* ignore */ }

        List<Integer> scores = new ArrayList<>();
        for (Map<String, Object> qa : chatHistory) {
            scores.add((Integer) qa.get("score"));
        }

        double finalScore = scores.stream().mapToInt(Integer::intValue).average().orElse(0);
        candidate.setFinalScore(finalScore);
        candidate.setSummary(aiService.summarizeCandidate(scores));

        candidateRepository.save(candidate);

        Map<String, Object> response = new HashMap<>();
        response.put("finalScore", finalScore);
        response.put("summary", candidate.getSummary());

        return ResponseEntity.ok(response);
    }
}
