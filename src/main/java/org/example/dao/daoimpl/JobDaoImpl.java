package org.example.dao.daoimpl;

import cenfig.Hibernatiprojec;
import org.example.dao.JobDao;
import org.example.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private Connection connection;

    public JobDaoImpl() {
        this.connection = Hibernatiprojec.connectionToDatabase();
    }

    public void createJobTable() {
        String query = "  create table if not exists job (id serial primary key," +
                "        position varchar,\n" +
                "        profession varchar(50)," +
                "        description varchar(50)," +
                "        experience integer);" ;

        try (Statement statement = connection.createStatement()){
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addJob(Job job) {
        String update = "insert into job (position, profession, description, experience)" +
                "                values(?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)){
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Job getJobById(Long jobId) {
        String query = " select * from job where id = ?;";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Job job = new Job();
            while(resultSet.next()){
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
            }
            return job;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        String query = null;
        if (ascOrDesc.equals("asc")){
            query = " select * from job order by experience asc";
        } else if (ascOrDesc.equals("desc")) {
            query = "select * from job order by experience desc";
        }
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Job job = new Job();
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
                jobs.add(job);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jobs;
    }

    public Job getJobByEmployeeId(Long employeeId) {
        Job job = new Job();
        String query = "select j.* from employees join job j on employees.job_id = j.id where employees.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return job;
    }

    public void deleteDescriptionColumn() {
        String query = "alter table job drop column description";
        try (Statement statement = connection.createStatement()){
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
