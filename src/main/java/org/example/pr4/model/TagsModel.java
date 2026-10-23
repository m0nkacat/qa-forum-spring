package org.example.pr4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Tags")
public class TagsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tagId;

    @NotBlank(message = "Тег не может быть пустым")
    String name;

    public TagsModel(int tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagsModel(){};
}
