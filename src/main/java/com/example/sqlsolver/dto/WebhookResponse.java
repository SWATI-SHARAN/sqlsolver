package com.example.sqlsolver.dto;

public class WebhookResponse {
    private String webhookUrl;
    private String token;
    private String sqlProblem;

    public WebhookResponse() {}

    public String getWebhookUrl() { return webhookUrl; }
    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getSqlProblem() { return sqlProblem; }
    public void setSqlProblem(String sqlProblem) { this.sqlProblem = sqlProblem; }
}
