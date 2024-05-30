package com.revature.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(columnDefinition = "TEXT")
    private int commentText;

    private Date timePosted;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private BlogPost blogPost;

    @JoinColumn(name = "parent_id", referencedColumnName = "comment_id")
    @ManyToOne
    private Comment parentComment;

/*
    The following code can potentially be used to create a separate table recording the child comments for each comment
    if it's believed to be necessary or useful.

    @OneToMany
    private Set<Comment> childComments;

    public void addChild(Comment child) {
        child.parentComment = this;
        this.childComments.add(child);
    }

    public void setParent(Comment parent) {
        parent.addChild(this);
    }
*/
}
