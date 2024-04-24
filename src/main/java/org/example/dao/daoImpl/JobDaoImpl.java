package org.example.dao.daoImpl;

import org.example.config.DatabaseConfig;
import org.example.dao.EmployeeDao;
import org.example.dao.JobDao;
import org.example.models.Employee;
import org.example.models.Job;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private static final Connection connection = DatabaseConfig.getConnection();

    @Override
    public void createJobTable() {
        String sql = """
                 create table if not exists jobs(
                 id serial primary key, 
                 position varchar(50),
                 profession varchar(50),
                 description varchar(50),
                 experience varchar(50)
                )
                                 """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully created a table of job: ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addJob(Job job) {
        String sql = """
                insert into jobs(position, profession, description, experience) 
                values(?,?,?,?)""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setLong(4, job.getExperience());

            System.out.println("Successfully added: ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Job getJobById(Long jobId) {
        Job job = new Job();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                     
                         select * from jobs where id = ?
                        
                    """);
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
            }
        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
        return job;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        String sql = "select * from jobs order by experience " + ascOrDesc;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Job job = new Job();
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        Job job = new Job();
        String sql = """
                select * from jobs where id = ? """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
            }
        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
        return job;
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql = """
                alter table jobs drop column description""";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully deleted: ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
