// src/components/Interviewer/InterviewerDashboard.jsx
import React, { useEffect, useState } from "react";
import { Card, Table, message } from "antd";
import api from "../../api";

export default function InterviewerDashboard() {
  const [candidates, setCandidates] = useState([]);

  const fetchCandidates = async () => {
    try {
      const res = await api.get("/dashboard/candidates");
      setCandidates(res.data || []);
    } catch (err) {
      console.error(err);
      message.error("Failed to load candidates.");
    }
  };

  useEffect(() => {
    fetchCandidates();
  }, []);

  const columns = [
    { title: "Name", dataIndex: "name", key: "name" },
    { title: "Email", dataIndex: "email", key: "email" },
    { title: "Phone", dataIndex: "phone", key: "phone" },
    { title: "Score", dataIndex: "finalScore", key: "finalScore", sorter: (a,b)=> (a.finalScore||0)-(b.finalScore||0) },
    { title: "Summary", dataIndex: "summary", key: "summary", render: text => text || "-" }
  ];

  return (
    <Card title="Interviewer Dashboard">
      <Table dataSource={candidates} columns={columns} rowKey="id" />
    </Card>
  );
}
