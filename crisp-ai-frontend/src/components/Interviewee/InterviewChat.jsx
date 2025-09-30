// src/components/Interviewee/InterviewChat.jsx
import React, { useEffect, useState } from "react";
import { Card, Button, Input, List, message } from "antd";
import api from "../../api";
import { useSelector, useDispatch } from "react-redux";
import { addChatEntry, setCandidate } from "../../store/CandidateSlice";

export default function InterviewChat() {
  const candidate = useSelector((state) => state.candidate.candidate);
  const chatHistory = useSelector((state) => state.candidate.chatHistory);
  const dispatch = useDispatch();

  const [currentQ, setCurrentQ] = useState(null);
  const [answer, setAnswer] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (candidate) fetchNextQuestion(); // attempt to get a question on load
    // eslint-disable-next-line
  }, [candidate]);

  const fetchNextQuestion = async () => {
    if (!candidate) return;
    try {
      const res = await api.get(`/interview/question/${candidate.id}`);
      if (res.data === "No more questions" || res.data === "No more questions") {
        message.info("No more questions available.");
        setCurrentQ(null);
        return;
      }
      // res.data contains { questionNumber, question }
      setCurrentQ(res.data);
    } catch (err) {
      console.error(err);
      message.error("Failed to fetch question.");
    }
  };

  const submitAnswer = async () => {
    if (!candidate || !currentQ) return message.warning("No question available.");
    try {
      setLoading(true);
      const payload = { question: currentQ.question, answer: answer || "" };
      const res = await api.post(`/interview/answer/${candidate.id}`, payload);

      // res.data should include { question, answer, score }
      dispatch(addChatEntry(res.data));
      setAnswer("");

      // fetch next question
      const next = await api.get(`/interview/question/${candidate.id}`);
      if (next.data === "No more questions") {
        // finish interview
        const fin = await api.post(`/interview/finish/${candidate.id}`);
        // update candidate with final score & summary in store
        const updated = { ...candidate, finalScore: fin.data.finalScore, summary: fin.data.summary };
        dispatch(setCandidate(updated));
        message.success("Interview finished. Summary saved.");
        setCurrentQ(null);
      } else {
        setCurrentQ(next.data);
      }
    } catch (err) {
      console.error(err);
      message.error("Failed to submit answer.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card title="Interview Chat">
      {!candidate && <p>Please upload your resume first (use the Resume Upload section).</p>}

      {candidate && (
        <div>
          <h4>Candidate: {candidate.name || candidate.email}</h4>

          {currentQ ? (
            <div>
              <div style={{ marginBottom: 8 }}>
                <strong>Q{currentQ.questionNumber}:</strong> {currentQ.question}
              </div>

              <Input.TextArea
                rows={4}
                value={answer}
                onChange={(e) => setAnswer(e.target.value)}
                placeholder="Type your answer here..."
                style={{ marginBottom: 8 }}
              />

              <Button type="primary" onClick={submitAnswer} loading={loading}>
                Submit Answer
              </Button>
            </div>
          ) : (
            <p>No active question. Click "Start Interview" in Resume Upload to begin (or interview may be finished).</p>
          )}

          <div style={{ marginTop: 16 }}>
            <h4>Chat History</h4>
            <List
              bordered
              dataSource={chatHistory}
              renderItem={(item, idx) => (
                <List.Item>
                  <div style={{ width: "100%" }}>
                    <div><strong>Q:</strong> {item.question}</div>
                    <div><strong>A:</strong> {item.answer}</div>
                    <div><strong>Score:</strong> {item.score}</div>
                  </div>
                </List.Item>
              )}
            />
          </div>
        </div>
      )}
    </Card>
  );
}
