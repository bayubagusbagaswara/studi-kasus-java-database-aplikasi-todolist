package repository;


import entity.Todolist;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodolistRepositoryImpl implements  TodolistRepository{

    private DataSource dataSource;

    // buat Constructor dataSource
    public TodolistRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Todolist[] getAll() {
        // kita harus dapatkan datanya dari database dengan melakukan query
        String sql = "select id, todo from todolist";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){

            // masukkan hasil resultset kedalam list
            List<Todolist> list = new ArrayList<>();
            while(resultSet.next()){
                Todolist todolist = new Todolist();
                todolist.setId(resultSet.getInt("id"));
                todolist.setTodo(resultSet.getString("todo"));

                list.add(todolist);
            }
            // kita konversi ke array
            return list.toArray(new Todolist[]{});

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void add(Todolist todolist) {
        // ini dihubungkan ke database
        String sql = "insert into todolist(todo) values(?)";

        // buat connection dan sekaligus bisa otomatis ditutup dengan try withresources
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, todolist.getTodo());
            statement.executeUpdate();

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    private boolean isExists(Integer number){
        // kita cek dulu, datanya ada atau tidak didalam database
        String sql = "select id from todolist where id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, number);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    // artinya datanya kita dapatkan, maka retrun true
                    return true;
                } else {
                    // artinya datanya tidak kita dapatkan, alias kosong didatabase
                    return false;
                }
            }
        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean remove(Integer number) {
        if (isExists(number)){
            // jika ternyata datanya ada di database, maka kita lakukan hapus
            String sql = "delete from todolist where id = ?";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)){

                statement.setInt(1, number);
                statement.executeUpdate();

                // kalo berhasil kita return true, artinya data berhasil dihapus
                return true;
            } catch (SQLException exception){
                throw new RuntimeException(exception);
            }
        } else {
            // jika number tidak ada di database, maka return false
            return false;
        }
    }
}
