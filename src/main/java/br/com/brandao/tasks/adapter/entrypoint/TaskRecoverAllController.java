package br.com.brandao.tasks.adapter.entrypoint;

import br.com.brandao.tasks.usecase.input.TaskGetAllInputBoundary;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskRecoverAllController {

    private final TaskGetAllInputBoundary taskGetAllInputBoundary;

    @GetMapping("/tasks")
    List<TaskResponseModel> getAll() {
        return taskGetAllInputBoundary.execute();
    }
}

