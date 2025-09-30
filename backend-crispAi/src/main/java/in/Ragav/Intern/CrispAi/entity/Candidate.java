package in.Ragav.Intern.CrispAi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Candidate 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private double finalScore;
    private String summary;
    @Lob
    @Column(name = "chat_history", columnDefinition = "LONGTEXT")
    private String chatHistory;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getChatHistory() {
		return chatHistory;
	}
	public void setChatHistory(String chatHistory) {
		this.chatHistory = chatHistory;
	}
	public Candidate(Long id, String name, String email, String phone, double finalScore, String summary,
			String chatHistory) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.finalScore = finalScore;
		this.summary = summary;
		this.chatHistory = chatHistory;
	}
	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
