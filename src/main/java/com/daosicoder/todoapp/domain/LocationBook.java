package com.daosicoder.todoapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// chứa thông tin về địa điểm của người dùng để kích hoạt các tác vụ nhắc nhở theo vị trí địa lý.
@Entity
@Table(name = "location_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationBook extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    private Double latitude;

    private Double longitude;

    @Column(name = "geofence_radius", nullable = false)
    @Builder.Default
    private Integer geofenceRadius = 200;

    // n location <-> 1 user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "location")
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        if (task != null) {
            this.tasks.add(task);
            task.setLocation(this);
        }
    }
    public void removeTask(Task task) {
        if (task != null) {
            this.tasks.remove(task);
            task.setLocation(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationBook that = (LocationBook) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
