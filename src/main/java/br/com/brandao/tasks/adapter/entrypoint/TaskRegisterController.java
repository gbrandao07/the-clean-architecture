package br.com.brandao.tasks.adapter.entrypoint;

import br.com.brandao.tasks.usecase.input.TaskCreateInputBoundary;
import br.com.brandao.tasks.usecase.model.TaskRequestModel;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskRegisterController {

    private final TaskCreateInputBoundary taskCreateInputBoundary;

    @PostMapping("/task")
    TaskResponseModel create(@RequestBody TaskRequestModel requestModel) {
        return taskCreateInputBoundary.execute(requestModel);
    }
}

