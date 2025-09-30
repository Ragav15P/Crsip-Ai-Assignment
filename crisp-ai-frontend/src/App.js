// src/App.js
import React from "react";
import { Layout, Tabs } from "antd";
import ResumeUpload from "./components/Interviewee/ResumeUpload";
import InterviewChat from "./components/Interviewee/InterviewChat";
import InterviewerDashboard from "./components/Interviewer/InterviewerDashboard";
import "antd/dist/reset.css";

const { Header, Content } = Layout;

function App() {
  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Header style={{ color: "white", fontSize: 20 }}>Crisp AI — Interview Assistant</Header>
      <Content style={{ padding: 24 }}>
        <Tabs defaultActiveKey="1">
          <Tabs.TabPane tab="Interviewee" key="1">
            <div style={{ maxWidth: 900, margin: "0 auto" }}>
              <ResumeUpload />
              <div style={{ marginTop: 24 }}>
                <InterviewChat />
              </div>
            </div>
          </Tabs.TabPane>
          <Tabs.TabPane tab="Interviewer (Dashboard)" key="2">
            <InterviewerDashboard />
          </Tabs.TabPane>
        </Tabs>
      </Content>
    </Layout>
  );
}

export default App;
