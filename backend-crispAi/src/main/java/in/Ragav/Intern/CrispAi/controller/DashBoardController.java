package in.Ragav.Intern.CrispAi.controller;

import in.Ragav.Intern.CrispAi.entity.Candidate;
import in.Ragav.Intern.CrispAi.repo.CandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {

    private final CandidateRepository candidateRepository;

    public DashBoardController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    // Get all candidates (can be sorted by finalScore)
    @GetMapping("/candidates")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        // Optional: sort by finalScore descending
        candidates.sort((a, b) -> Double.compare(b.getFinalScore(), a.getFinalScore()));
        return ResponseEntity.ok(candidates);
    }

    // Get candidate details by ID
    @GetMapping("/candidates/{id}")
    public ResponseEntity<?> getCandidateDetails(@PathVariable Long id) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isEmpty()) return ResponseEntity.badRequest().body("Candidate not found");

        Candidate candidate = candidateOpt.get();
        return ResponseEntity.ok(candidate);
    }

    // Optional: search by name or email
    @GetMapping("/candidates/search")
    public ResponseEntity<List<Candidate>> searchCandidates(@RequestParam String query) {
        List<Candidate> candidates = candidateRepository.findAll();
        // filter by name or email contains query (case-insensitive)
        List<Candidate> filtered = candidates.stream()
                .filter(c -> (c.getName() != null && c.getName().toLowerCase().contains(query.toLowerCase()))
                        || (c.getEmail() != null && c.getEmail().toLowerCase().contains(query.toLowerCase())))
                .toList();
        return ResponseEntity.ok(filtered);
    }
    
 // Update candidate (partial update)
    @PutMapping("/candidates/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody Candidate updated) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isEmpty()) return ResponseEntity.badRequest().body("Candidate not found");

        Candidate candidate = candidateOpt.get();
        // Only update fields if provided (avoid overwriting other data)
        if (updated.getName() != null && !updated.getName().isEmpty()) candidate.setName(updated.getName());
        if (updated.getEmail() != null && !updated.getEmail().isEmpty()) candidate.setEmail(updated.getEmail());
        if (updated.getPhone() != null && !updated.getPhone().isEmpty()) candidate.setPhone(updated.getPhone());

        candidateRepository.save(candidate);
        return ResponseEntity.ok(candidate);
    }

}
