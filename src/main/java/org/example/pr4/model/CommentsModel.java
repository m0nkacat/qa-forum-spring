package org.example.pr4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "comments")
public class CommentsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "question_id")
    private QuestionsModel question;

    @ManyToOne(optional = true)
    @JoinColumn(name = "answer_id")
    private AnswersModel answer;

    @NotBlank(message = "Комментарий не может быть пустым")
    private String body;

    public CommentsModel() {}

    public CommentsModel(Integer commentId, UserModel user, QuestionsModel question, AnswersModel answer, String body) {
        this.commentId = commentId;
        this.user = user;
        this.question = question;
        this.answer = answer;
        this.body = body;
    }

    public Integer getCommentId() { return commentId; }
    public void setCommentId(Integer commentId) { this.commentId = commentId; }

    public UserModel getUser() { return user; }
    public void setUser(UserModel user) { this.user = user; }

    public QuestionsModel getQuestion() { return question; }
    public void setQuestion(QuestionsModel question) { this.question = question; }

    public AnswersModel getAnswer() { return answer; }
    public void setAnswer(AnswersModel answer) { this.answer = answer; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}