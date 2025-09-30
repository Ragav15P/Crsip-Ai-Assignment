# 🎯 Crisp — AI-Powered Interview Assistant
*Swipe Internship Assignment*

**Crisp** is a web application that simulates an AI-powered interview assistant. It allows candidates to take **timed, structured interviews** while helping interviewers efficiently evaluate performance.

---

## ✨ Features

### 🧑‍💻 Interviewee (Chat) Tab
- **Upload resume** (PDF required, DOCX optional)  
- **Auto-extract** Name, Email, Phone from resume  
- Chatbot collects missing information before the interview  

**Timed Interview Flow:**  
- 6 questions: 2 Easy → 2 Medium → 2 Hard  
- Easy: 20s per question  
- Medium: 60s per question  
- Hard: 120s per question  
- Automatic submission when timer expires  

**AI Evaluation:**  
- Keyword-based scoring  
- Provides **final score and summary**

---

### 👨‍🏫 Interviewer (Dashboard) Tab
- View **all candidates** with scores and summaries  
- Detailed candidate view:  
  - Chat history  
  - Profile info  
  - AI-generated score breakdown  
- **Search & sort** functionality for easy management  

---

### 💾 Data Persistence
- All interview progress, answers, and timers stored **locally** using Redux + persistence  
- Sessions can be resumed after browser refresh or closure  
- “Welcome Back” modal for unfinished interviews  

---

## 🛠 Tech Stack

| Layer            | Technology |
|-----------------|------------|
| Frontend        | React, Redux (with persistence), Ant Design / shadcn UI |
| Backend         | Spring Boot |
| Database        | SQL (H2 / MySQL / PostgreSQL compatible) |
| Resume Parsing  | Extracts Name, Email, Phone from PDF/DOCX |
| State Management | Redux + redux-persist / IndexedDB |

---

## 🚀 How It Works
1. Candidate uploads a resume  
2. Chatbot collects any missing details  
3. Candidate answers **6 timed questions**  
4. AI evaluates answers using **keywords**  
5. Interviewer reviews candidates on the **dashboard**  
6. All data persists locally, allowing **session resumption**

---

## 📌 Key Highlights
- AI-powered evaluation for faster interviewer insights  
- Resume auto-parsing reduces manual input  
- Persistent state ensures seamless experience even on refresh  
- Clean, modern UI with React and Ant Design/shadcn UI  

---
