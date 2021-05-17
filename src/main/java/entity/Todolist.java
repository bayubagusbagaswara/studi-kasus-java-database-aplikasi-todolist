package entity;

public class Todolist {
    // representasi datanya hanya String aja, dan di enkapsulasi

    /**
     * Representasi Data
     */
    private String todo;

    /**
     * Id dari database
     */
    private Integer id;

    /**
     * Constructor dengan parameter
     * @param todo
     */
    public Todolist(String todo) {
        this.todo = todo;
    }

    /**
     * Constructor tanpa parameter
     */
    public Todolist() {
    }

    // biasanya untuk representasi data, dibiking setter dan getter

    /**
     * Getter
     * @return
     */
    public String getTodo() {
        return todo;
    }

    /**
     * Setter
     * @param todo
     */
    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
