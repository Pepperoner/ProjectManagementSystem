package dao;

import instance.*;
import java.util.List;

public interface SkillDAO {

    Skill save(Skill skill);

    Skill read(int id);

    void update(int id, Skill skill);

    void delete(int id);

    List<Skill> readAllTable();
}
