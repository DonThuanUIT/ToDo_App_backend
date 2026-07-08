package com.daosicoder.todoapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_daoists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserDaoist {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "current_points", nullable = false)
    @Builder.Default
    private Integer currentPoints = 0;

    @Column(name = "daily_goal", nullable = false)
    @Builder.Default
    private Integer dailyGoal = 5;

    @Column(name = "weekly_goal", nullable = false)
    @Builder.Default
    private Integer weeklyGoal = 25;

    @Column(name = "daily_streak", nullable = false)
    @Builder.Default
    private Integer dailyStreak = 0;

    @Column(name = "weekly_streak", nullable = false)
    @Builder.Default
    private Integer weeklyStreak = 0;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDaoist that = (UserDaoist) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}