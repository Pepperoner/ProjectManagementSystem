package controller;

import instance.*;
import java.util.List;

public interface SkillController {

    List<Skill> readAllTable();

    Skill addData(String skillName);

    void update(int id, String skillName);

    void delete(int id);
}
