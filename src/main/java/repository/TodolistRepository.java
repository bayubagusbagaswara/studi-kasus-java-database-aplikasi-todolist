package repository;


import entity.Todolist;

public interface TodolistRepository {
    // bikin function atau method yang akan kita gunakan

    // 1. mendapatkan semua todolistnya, dan simpan ke array
    Todolist[] getAll();

    // 2. untuk menambah todolist
    void add(Todolist todolist);

    // 3. remove todolist
    boolean remove(Integer number);
}
