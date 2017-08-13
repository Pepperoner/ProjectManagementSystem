package dao;

import instance.*;
import java.util.List;

public interface DeveloperDAO {

    Developer save(Developer developer);

    Developer read(int id);

    void update(int id, Developer developer);

    void delete(int id);

    List<Developer> readAllTable();
}
