package com.daosicoder.todoapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "daoist_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DaoistLog extends BaseEntity {
    @Column(name = "points_changed", nullable = false)
    private Integer pointsChanged;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 100)
    private DaoistActionType actionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaoistLog daoistLog = (DaoistLog) o;
        return getId() != null && Objects.equals(getId(), daoistLog.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}