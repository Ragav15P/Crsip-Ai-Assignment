// src/components/Interviewee/ResumeUpload.jsx
import React, { useState } from "react";
import { Card, Upload, Button, message, Form, Input, Space, Spin } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import api from "../../api";
import { useDispatch } from "react-redux";
import { setCandidate } from "../../store/CandidateSlice";

export default function ResumeUpload() {
  const [fileList, setFileList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [candidate, setLocalCandidate] = useState(null);
  const [missing, setMissing] = useState({ name: "", email: "", phone: "" });
  const dispatch = useDispatch();

  const beforeUpload = (file) => {
    setFileList([file]);
    return false; // prevent auto upload by Upload component
  };

  const handleUpload = async () => {
    if (fileList.length === 0) {
      message.warning("Please choose a resume file (PDF or DOCX).");
      return;
    }

    const fd = new FormData();
    fd.append("file", fileList[0]);

    try {
      setLoading(true);
      const res = await api.post("/resume/upload", fd, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      const saved = res.data;
      dispatch(setCandidate(saved));
      setLocalCandidate(saved);

      // prepare missing fields for UI input if 'Not Found' or empty
      setMissing({
        name: saved.name === "Not Found" ? "" : saved.name || "",
        email: saved.email === "Not Found" ? "" : saved.email || "",
        phone: saved.phone === "Not Found" ? "" : saved.phone || "",
      });

      message.success("Resume uploaded and parsed.");
    } catch (err) {
      console.error(err);
      message.error("Upload failed: " + (err?.response?.data || err.message));
    } finally {
      setLoading(false);
    }
  };

  const handleSaveProfile = async () => {
    if (!candidate) return message.warning("Upload resume first.");

    const payload = {
      name: missing.name || candidate.name,
      email: missing.email || candidate.email,
      phone: missing.phone || candidate.phone,
    };

    try {
      setLoading(true);
      const res = await api.put(`/dashboard/candidates/${candidate.id}`, payload);
      const updated = res.data;
      dispatch(setCandidate(updated));
      setLocalCandidate(updated);
      message.success("Profile updated.");
    } catch (err) {
      console.error(err);
      message.error("Failed to update profile: " + (err?.response?.data || err.message));
    } finally {
      setLoading(false);
    }
  };

  const handleStartInterview = async () => {
    if (!candidate) return message.warning("Upload resume and save profile first.");
    try {
      setLoading(true);
      await api.post(`/interview/start/${candidate.id}`);
      message.success("Interview started. Question will appear in chat area below.");
    } catch (err) {
      console.error(err);
      message.error("Failed to start interview.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card title="Resume Upload & Profile">
      <Spin spinning={loading}>
        <Space direction="vertical" style={{ width: "100%" }}>
          <Upload
            beforeUpload={beforeUpload}
            fileList={fileList}
            onRemove={() => setFileList([])}
            accept=".pdf,.docx"
            maxCount={1}
          >
            <Button icon={<UploadOutlined />}>Select Resume (PDF/DOCX)</Button>
          </Upload>

          <Button type="primary" onClick={handleUpload} disabled={fileList.length === 0}>
            Upload & Parse
          </Button>

          {candidate && (
            <div style={{ marginTop: 12 }}>
              <h3>Extracted Profile</h3>
              <Form layout="vertical">
                <Form.Item label="Name">
                  <Input
                    value={missing.name}
                    onChange={(e) => setMissing((s) => ({ ...s, name: e.target.value }))}
                    placeholder="Name (if missing, please enter)"
                  />
                </Form.Item>

                <Form.Item label="Email">
                  <Input
                    value={missing.email}
                    onChange={(e) => setMissing((s) => ({ ...s, email: e.target.value }))}
                    placeholder="Email (if missing, please enter)"
                  />
                </Form.Item>

                <Form.Item label="Phone">
                  <Input
                    value={missing.phone}
                    onChange={(e) => setMissing((s) => ({ ...s, phone: e.target.value }))}
                    placeholder="Phone (if missing, please enter)"
                  />
                </Form.Item>

                <Space>
                  <Button onClick={handleSaveProfile}>Save Profile</Button>
                  <Button type="primary" onClick={handleStartInterview}>
                    Start Interview
                  </Button>
                </Space>
              </Form>
            </div>
          )}
        </Space>
      </Spin>
    </Card>
  );
}
