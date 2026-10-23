package org.example.pr4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "QuestionTags")
public class QuestionTagsApiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private QuestionsModel question;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TagsModel tag;

    public QuestionTagsApiModel() {}

    public QuestionTagsApiModel(Integer id, QuestionsModel question, TagsModel tag) {
        this.id = id;
        this.question = question;
        this.tag = tag;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public QuestionsModel getQuestion() { return question; }
    public void setQuestion(QuestionsModel question) { this.question = question; }

    public TagsModel getTag() { return tag; }
    public void setTag(TagsModel tag) { this.tag = tag; }
}