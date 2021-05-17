package repository;

import com.zaxxer.hikari.HikariDataSource;
import entity.Todolist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseUtil;


public class TodolistRepositoryImplTest {

    private HikariDataSource dataSource;
    private TodolistRepository todolistRepository;

    @BeforeEach
    void setUp() {
        dataSource = DatabaseUtil.getDataSource();
        todolistRepository = new TodolistRepositoryImpl(dataSource);
    }

    @Test
    void testAdd() {
        Todolist todolist = new Todolist();
        todolist.setTodo("Bayu");
        todolist.setTodo("Aan");

        todolistRepository.add(todolist);
    }

    @Test
    void testRemove() {
        System.out.println(todolistRepository.remove(1));
        System.out.println(todolistRepository.remove(2));
        System.out.println(todolistRepository.remove(3));
    }

    @Test
    void testGetAll() {
        // coba tambahkan todolist dulu
        todolistRepository.add(new Todolist("Bayu"));
        todolistRepository.add(new Todolist("Bagus"));
        todolistRepository.add(new Todolist("Bagaswara"));

        // tampilkan semua todolist
        Todolist[] todolists = todolistRepository.getAll();
        for (var todo :
                todolists) {
            System.out.println(todo.getId() + " : " + todo.getTodo());
        }
    }

    @AfterEach
    void tearDown() {
        dataSource.close();
    }
}
