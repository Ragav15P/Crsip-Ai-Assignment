🎯 Crisp — AI-Powered Interview Assistant (Swipe Internship Assignment)

Crisp is a web application simulating an AI-powered interview assistant. It allows candidates to take timed, structured interviews and helps interviewers evaluate performance efficiently.


Features
Interviewee (Chat) Tab

Upload resume (PDF required, DOCX optional)

Auto-extract Name, Email, Phone from resume

Chatbot collects missing info before the interview

Timed Interview Flow:

6 questions: 2 Easy → 2 Medium → 2 Hard

Easy: 20s, Medium: 60s, Hard: 120s per question

Automatic submission when timer expires

Keyword-based AI evaluation with final score and summary

Interviewer (Dashboard) Tab

View all candidates with scores and summaries

Detailed candidate view:

Chat history

Profile info

AI-generated score breakdown

Search & sort functionality for easy management

Data Persistence

All interview progress, answers, and timers stored locally using Redux + persistence

Sessions can be resumed after refresh or browser close

“Welcome Back” modal for unfinished interviews



🛠 Tech Stack
Layer	Technology
Frontend	React, Redux (with persistence), Ant Design / shadcn UI
Backend	Spring Boot
Database	SQL (H2 / MySQL / PostgreSQL compatible)
Resume Parsing	Extracts Name, Email, Phone from PDF/DOCX
State Management	Redux + redux-persist / IndexedDB

🚀 How It Works

Candidate uploads a resume

Missing details are collected via chatbot prompts

Candidate answers 6 timed questions

AI evaluates answers using keywords

Interviewer reviews candidates on dashboard

All data persists locally, allowing session resumption
