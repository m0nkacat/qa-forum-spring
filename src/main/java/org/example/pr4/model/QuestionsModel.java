package org.example.pr4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Questions")
public class QuestionsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;

    @NotBlank(message = "Текст вопроса не может быть пустым")
    private String body;

    public QuestionsModel() {}

    public QuestionsModel(Integer questionId, String title, String body) {
        this.questionId = questionId;
        this.title = title;
        this.body = body;
    }

    public Integer getQuestionId() { return questionId; }
    public void setQuestionId(Integer questionId) { this.questionId = questionId; }

    public UserModel getUser() { return user; }
    public void setUser(UserModel user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}