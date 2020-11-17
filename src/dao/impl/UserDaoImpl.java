package dao.impl;

import dao.UserDao;
import domain.Message;
import domain.User;

import util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Message> findAll() {
        //使用JDBC操作数据库
        //1.定义sql
        String sql="select * from message";
        List<Message> users = template.query(sql, new BeanPropertyRowMapper<Message>(Message.class));
        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void add(Message message) {
        //定义sql
        String sql="insert into message values(null,?,?,?,?,?,?)";
        template.update(sql,message.getName(),message.getGander(),message.getAge(),message.getAddress(),message.getQq(),message.getEmail());
    }

    @Override
    public void delete(int id) {
        String sql="delete from message where id=?";
        template.update(sql,id);
    }

    @Override
    public Message findById(int id) {
        String sql="select * from message where id=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Message>(Message.class),id);
    }

    @Override
    public void update(Message message) {
        String sql="update message set name=?,gander=?,age=?,address=?,qq=?,email=? where id=?";
        template.update(sql,message.getName(),message.getGander(),message.getAge(),message.getAddress(),message.getQq(),message.getEmail(),message.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql="select count(*) from message where 1 = 1";
        StringBuilder sb=new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params=new ArrayList<>();
        for (String key:keySet){
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if(value!=null && ! "".equals(value) ){
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");
            }
        }
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<Message> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql="select * from message where 1=1";
        StringBuilder sb=new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params=new ArrayList<>();
        for (String key:keySet){
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if(value!=null && ! "".equals(value) ){
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");
            }
        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);
        return template.query(sb.toString(),new BeanPropertyRowMapper<Message>(Message.class),params.toArray());
    }
}
