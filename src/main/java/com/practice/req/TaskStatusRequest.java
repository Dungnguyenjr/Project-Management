package com.practice.req;

import com.practice.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskStatusRequest {
    private TaskStatus status;
}
