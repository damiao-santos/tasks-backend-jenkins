package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

    @Mock //cria o mock para evitar nullpointexception
    private TaskRepo taskRepo;

    @InjectMocks //injeta o metodo no mock
    private TaskController controller;

    @Before //inicializa o mock antes de cada teste
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naodeveSalvarTarefaSemDescricao(){
        Task todo = new Task();
        //todo.setTask("Descrição");
        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naodeveSalvarTarefaSemData(){
        Task todo = new Task();
        todo.setTask("Descrição");
        //todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naodeveSalvarTarefaComDataPassada(){
        Task todo = new Task();
        todo.setTask("Descrição");
        todo.setDueDate(LocalDate.of(2010, 01, 01));
        try {
            controller.save(todo);
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException{
        Task todo = new Task();
        todo.setTask("Descrição");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }
}
