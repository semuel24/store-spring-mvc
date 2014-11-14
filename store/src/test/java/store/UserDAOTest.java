package store;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.store.dao.UserDAO;
import com.store.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class UserDAOTest {

	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void testUserTableConnection() {
		User user = userDAO.findById(1L);
		assertNotNull(user);
	}
}
