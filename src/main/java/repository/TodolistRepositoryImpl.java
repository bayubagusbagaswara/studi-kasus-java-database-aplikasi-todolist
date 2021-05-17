package repository;


import entity.Todolist;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TodolistRepositoryImpl implements  TodolistRepository{

    public Todolist[] data = new Todolist[10];

    private DataSource dataSource;

    // buat Constructor dataSource
    public TodolistRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Todolist[] getAll() {
        return data;
    }

    // method untuk cek model udah penuh atau belum
    public boolean isFull(){
        boolean isFull = true;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null){
                isFull = false;
                break;
            }
        }
        return isFull;
    }

    // method function untuk resize
    public void resizeIfFull(){
        // jika penuh, kita resize ukuran array 2x lipat
        if (isFull()) {
            var temporary = data;
            data = new Todolist[data.length * 2];
            for (int i = 0; i < temporary.length; i++) {
                data[i] = temporary[i];
            }
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
