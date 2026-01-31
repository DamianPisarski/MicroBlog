package edu.atins.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "follower",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_follower_followed",
        columnNames = {"follower_id", "followed_id"}
    )
)
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // follower_id -> user.id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_follower_user"))
    private User follower;

    // followed_id -> user.id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_followed_user"))
    private User followed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    //konstruktor bezargumentowy
    public Follower() {}

    // gettery i settery
    public Long getId() {
        return id;
    }

    public User getFollower() {
        return follower;
    }
    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }
    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
