package com.daosicoder.todoapp.domain.converter;

import com.daosicoder.todoapp.domain.TaskPriority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class TaskPriorityConverter implements AttributeConverter<TaskPriority, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TaskPriority attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public TaskPriority convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TaskPriority.fromValue(dbData);
    }
}