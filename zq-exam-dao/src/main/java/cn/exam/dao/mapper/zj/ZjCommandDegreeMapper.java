package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjCommandDegree;
import cn.exam.vo.CommandDegreeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjCommandDegreeMapper extends CommonBaseMapper<ZjCommandDegree> {

    ZjCommandDegree queryExistenceByUserIdAndSectionId(@Param("userId")String userId,@Param("sectionId")Integer sectionId);
    void insertOneCommandDegree(@Param("userId")String userId,@Param("sectionId")Integer sectionId,@Param("commandDegree")double commandDegree);

    void updateOneCommandDegree(@Param("userId")String userId,@Param("sectionId")Integer sectionId,@Param("commandDegree")double commandDegree);

    List<CommandDegreeVO> queryCommandDegreesByUserId(@Param("userId")String userId);
}
