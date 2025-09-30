import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  candidate: null,      // stores candidate profile from backend
  chatHistory: [],      // list of { question, answer, score, feedback }
  currentQuestion: 0,   // index of current question
  interviewFinished: false,
};

const candidateSlice = createSlice({
  name: "candidate",
  initialState,
  reducers: {
    setCandidate: (state, action) => {
      state.candidate = action.payload;
    },
    addChatEntry: (state, action) => {
      state.chatHistory.push(action.payload);
    },
    setCurrentQuestion: (state, action) => {
      state.currentQuestion = action.payload;
    },
    finishInterview: (state) => {
      state.interviewFinished = true;
    },
    resetCandidate: (state) => {
      state.candidate = null;
      state.chatHistory = [];
      state.currentQuestion = 0;
      state.interviewFinished = false;
    }
  },
});

export const { setCandidate, addChatEntry, setCurrentQuestion, finishInterview, resetCandidate } = candidateSlice.actions;
export default candidateSlice.reducer;
