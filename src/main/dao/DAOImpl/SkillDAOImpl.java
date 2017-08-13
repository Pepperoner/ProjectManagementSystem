package dao.DAOImpl;

import dao.SkillDAO;
import dao.pooledJdbc.PooledJDBCUserDAO;
import dao.logger.ExceptionLogger;
import instance.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAOImpl extends PooledJDBCUserDAO implements SkillDAO {

    public SkillDAOImpl(DataSource dataSource) {
        super(dataSource);
    }
    @Override
    public Skill save(Skill skill) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO skills(skills) VALUE(?);")) {
            ps.setString(1, skill.getSkillName());
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return skill;
    }

    @Override
    public Skill read(int id) {
        Skill skill = null;
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT skills.id, skills.skills FROM skills WHERE skills.id = ?;")) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int skillID = resultSet.getInt("id");
                    String skillName = resultSet.getString("skills");
                    skill = new Skill(skillName);
                    skill.setId(skillID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return skill;
    }

    @Override
    public void update(int id, Skill skill) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE skills SET skills = ? WHERE id = ?;")) {
            ps.setString(1, skill.getSkillName());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM developers_has_skills WHERE Skills_id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM skills WHERE id = ?;")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                ExceptionLogger.initLogger(e1.toString());
            }
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    ExceptionLogger.initLogger(e.toString());
                }
                try {
                    connection.close();
                } catch (SQLException e) {
                    ExceptionLogger.initLogger(e.toString());
                }
            }
        }
    }

    @Override
    public List<Skill> readAllTable() {
        List<Skill> skillsList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM skills")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String skillName = resultSet.getString("skills");
                    Skill skill = new Skill(skillName);
                    skill.setId(id);
                    skillsList.add(skill);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return skillsList;
    }
}
