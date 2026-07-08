package com.daosicoder.todoapp.domain;
import lombok.Getter;

@Getter
public enum TaskPriority {
    P1(1),
    P2(2),
    P3(3),
    P4(4);

    private final int value;

    TaskPriority(int value) {
        this.value = value;
    }

    public static TaskPriority fromValue(int value) {
        for (TaskPriority priority : TaskPriority.values()) {
            if (priority.getValue() == value) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Giá trị độ ưu tiên không hợp lệ: " + value);
    }
}