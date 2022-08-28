package com.techelevator;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

public class JdbcUserDaoTest extends BaseDaoTests{
    private JdbcUserDao sut;
    private User newUser;
    private List<User> newUsers;
    @Before
    public void setup(){
        sut = new JdbcUserDao(dataSource);
        newUser = new User(1001L,"Noah", "10101", "true");
    }
    @Test
    public void findByUsername_returns_correct_user_for_username() {
        User user = sut.findByUsername("Noah");
        assertUsersMatch(newUser, user);
    }
    @Test
    public void findIdByUsername_returns_correct_id() {
        Assert.assertEquals(1001, sut.findIdByUsername("Noah"));
    }

    @Test
    public void findAll_retrieves_correct_number_of_users() {
        List<User> users= sut.findAll();
        Assert.assertEquals(2, users.size());
    }


    private void assertUsersMatch(User expected, User actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }

}