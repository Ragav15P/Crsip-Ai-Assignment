package in.Ragav.Intern.CrispAi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import in.Ragav.Intern.CrispAi.entity.Candidate;
import in.Ragav.Intern.CrispAi.service.ResumeService;
import in.Ragav.Intern.CrispAi.repo.CandidateRepository;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final CandidateRepository candidateRepository;

    public ResumeController(ResumeService resumeService, CandidateRepository candidateRepository) {
        this.resumeService = resumeService;
        this.candidateRepository = candidateRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            Candidate candidate = resumeService.parseResume(file);
            
            // Save candidate to DB
            Candidate savedCandidate = candidateRepository.save(candidate);

            return ResponseEntity.ok(savedCandidate);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Failed to parse resume: " + e.getMessage());
        }
    }
}
