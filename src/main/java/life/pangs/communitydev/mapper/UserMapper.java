package life.pangs.communitydev.mapper;

import life.pangs.communitydev.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user(NAME,ACCOUNT_ID,TOKEN,GMT_CREATE,GMT_MODIFIED)values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user t where t.token = #{token}")
    User findByToken(@Param("token") String token);
}
