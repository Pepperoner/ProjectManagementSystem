package controller.controllerImpl;

import controller.SkillController;
import dao.SkillDAO;
import instance.*;
import java.util.ArrayList;
import java.util.List;

public class SkillControllerImpl implements SkillController {

    private SkillDAO dao;

    public SkillControllerImpl(SkillDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Skill> readAllTable() {
        List<Skill> skillList = dao.readAllTable();
        if (skillList == null || skillList.isEmpty()){
            System.out.println("List is empty!");
            return new ArrayList<>();
        }
        return dao.readAllTable();
    }

    @Override
    public Skill addData(String skillName) {
        return dao.save(new Skill(skillName));
    }

    @Override
    public void update(int id, String skillName) {
        dao.update(id,new Skill(skillName));
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
