package in.Ragav.Intern.CrispAi.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiService {

    private final Map<String, List<String>> questions = Map.of(
            "easy", Arrays.asList(
                    "What is Java and its main features?",
                    "Explain OOP concepts in Java.",
                    "What is Spring Boot and why is it used?",
                    "What is a variable and data type in Java?",
                    "Explain useState hook in React.",
                    "What is HTML and why do we use it?"
            ),
            "medium", Arrays.asList(
                    "Explain Java memory management and garbage collection.",
                    "What is the difference between HashMap and TreeMap?",
                    "Explain the lifecycle methods of React components.",
                    "What is the difference between Node.js and Express.js?",
                    "Explain dependency injection in Spring.",
                    "What is a linked list and its applications?"
            ),
            "hard", Arrays.asList(
                    "Explain multithreading in Java with examples.",
                    "How would you optimize a Java application for performance?",
                    "Explain dynamic programming with an example.",
                    "How does JWT authentication work in full-stack apps?",
                    "How would you improve performance of a React app?",
                    "Explain how Spring handles transactions."
            )
    );

    private final Random random = new Random();

    // Generate question based on candidate progress
    public String generateQuestion(int questionNumber) {
        if (questionNumber < 2) {
            List<String> pool = questions.get("easy");
            return pool.get(random.nextInt(pool.size()));
        }
        if (questionNumber < 4) {
            List<String> pool = questions.get("medium");
            return pool.get(random.nextInt(pool.size()));
        }
        if (questionNumber < 6) {
            List<String> pool = questions.get("hard");
            return pool.get(random.nextInt(pool.size()));
        }
        return null; // no more questions
    }

//    // Mock scoring: random score between 60-100
//    public int scoreAnswer(String question, String answer) {
//        return 60 + random.nextInt(41); // 60-100
//    }
    
    
    
 // Improved scoring: keyword-based
    public int scoreAnswer(String question, String answer) {
        if (answer == null || answer.isEmpty()) return 0;

        // Define keywords for all questions (no duplicates)
        Map<String, List<String>> keywords = Map.ofEntries(
            // Easy
            Map.entry("What is Java and its main features?", Arrays.asList("object-oriented", "platform-independent", "jvm", "class", "method")),
            Map.entry("Explain OOP concepts in Java.", Arrays.asList("inheritance", "encapsulation", "polymorphism", "abstraction")),
            Map.entry("What is Spring Boot and why is it used?", Arrays.asList("spring", "boot", "microservice", "configuration", "starter")),
            Map.entry("What is a variable and data type in Java?", Arrays.asList("variable", "datatype", "int", "string", "boolean")),
            Map.entry("Explain useState hook in React.", Arrays.asList("useState", "state", "hook", "react", "update")),
            Map.entry("What is HTML and why do we use it?", Arrays.asList("html", "markup", "structure", "tags", "webpage")),

            // Medium
            Map.entry("Explain Java memory management and garbage collection.", Arrays.asList("heap", "stack", "gc", "garbage", "memory")),
            Map.entry("What is the difference between HashMap and TreeMap?", Arrays.asList("hashmap", "treemap", "order", "sorted", "key")),
            Map.entry("Explain the lifecycle methods of React components.", Arrays.asList("componentDidMount", "componentDidUpdate", "componentWillUnmount", "lifecycle")),
            Map.entry("What is the difference between Node.js and Express.js?", Arrays.asList("node", "express", "framework", "server", "middleware")),
            Map.entry("Explain dependency injection in Spring.", Arrays.asList("di", "dependency", "injection", "spring", "bean")),
            Map.entry("What is a linked list and its applications?", Arrays.asList("linkedlist", "node", "pointer", "next", "data structure")),

            // Hard
            Map.entry("Explain multithreading in Java with examples.", Arrays.asList("thread", "synchronized", "concurrent", "run", "start")),
            Map.entry("How would you optimize a Java application for performance?", Arrays.asList("optimize", "performance", "memory", "threads", "efficient")),
            Map.entry("Explain dynamic programming with an example.", Arrays.asList("dp", "memoization", "subproblem", "optimization")),
            Map.entry("How does JWT authentication work in full-stack apps?", Arrays.asList("jwt", "token", "authentication", "server", "client")),
            Map.entry("How would you improve performance of a React app?", Arrays.asList("react", "performance", "memo", "lazy", "optimization")),
            Map.entry("Explain how Spring handles transactions.", Arrays.asList("spring", "transaction", "rollback", "commit", "database"))
        );

        List<String> keyList = keywords.getOrDefault(question, Collections.emptyList());
        if (keyList.isEmpty()) return 70; // default score if no keywords defined

        int count = 0;
        String lowerAnswer = answer.toLowerCase();
        for (String key : keyList) {
            if (lowerAnswer.contains(key.toLowerCase())) count++;
        }

        // Score = 60 + proportion of keywords matched * 40
        int score = 60 + (int)((40.0 * count) / keyList.size());
        return Math.min(score, 100); // cap at 100
    }

    // Mock summary: generate simple summary based on scores
    public String summarizeCandidate(List<Integer> scores) {
        double avg = scores.stream().mapToInt(Integer::intValue).average().orElse(0);
        if (avg >= 85) return "Excellent candidate with strong understanding.";
        else if (avg >= 70) return "Good candidate with moderate understanding.";
        else return "Candidate needs improvement in technical skills.";
    }
}
