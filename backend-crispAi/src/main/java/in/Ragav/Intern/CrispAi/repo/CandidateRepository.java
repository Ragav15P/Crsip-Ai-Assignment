package in.Ragav.Intern.CrispAi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import in.Ragav.Intern.CrispAi.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
