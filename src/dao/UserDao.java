package dao;

import domain.Message;
import domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的Dao
 */
public interface UserDao {
    public List<Message> findAll();

    User findUserByUsernameAndPassword(String username,String password);

    void add(Message message);

    void delete(int id);

    Message findById(int parseInt);

    void update(Message message);

    /**
     * 查询总记录数
     * @return
     * @param condition
     */
    int findTotalCount(Map<String, String[]> condition);

    /**
     * 每页的记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    List<Message> findByPage(int start, int rows, Map<String, String[]> condition);
}
