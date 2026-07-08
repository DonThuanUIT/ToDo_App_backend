package com.daosicoder.todoapp.domain;

import jakarta.persistence.CascadeType;
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

/**
 * Thực thể ánh xạ bảng "habits" trong PostgreSQL.
 * Kế thừa BaseEntity để sở hữu các trường kiểm toán thời gian: id, created_at, và updated_at.
 */
@Entity
@Table(name = "habits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit extends BaseEntity {

    @Column(nullable = false)
    private String name;

    /**
     * Cấu hình lịch trình thực hiện thói quen.
     * Ví dụ lưu chuỗi: "MON,WED,FRI" hoặc "DAILY".
     */
    @Column(name = "schedule_config")
    private String scheduleConfig;

    // =========================================================
    // LIÊN KẾT KHÓA NGOẠI (RELATIONSHIPS)
    // =========================================================

    /**
     * Mỗi thói quen bắt buộc phải thuộc về một người dùng sở hữu.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Mối quan hệ hai chiều với Nhật ký thói quen (HabitLog).
     * Khi xóa Habit, tự động cascade xóa sạch các HabitLog liên quan (ON DELETE CASCADE).
     */
    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HabitLog> habitLogs = new ArrayList<>();

    // =========================================================
    // HELPER METHODS (Đồng bộ hóa quan hệ hai chiều trong bộ nhớ)
    // =========================================================

    /**
     * Thêm nhật ký thói quen và tự động thiết lập liên kết ngược.
     */
    public void addLog(HabitLog log) {
        if (log != null) {
            this.habitLogs.add(log);
            log.setHabit(this);
        }
    }

    /**
     * Xóa nhật ký thói quen và gỡ bỏ liên kết ngược.
     */
    public void removeLog(HabitLog log) {
        if (log != null) {
            this.habitLogs.remove(log);
            log.setHabit(null);
        }
    }

    // =========================================================
    // OVERRIDE EQUALS & HASHCODE (Chuẩn JPA để tránh lỗi Proxy)
    // =========================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return getId() != null && Objects.equals(getId(), habit.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}