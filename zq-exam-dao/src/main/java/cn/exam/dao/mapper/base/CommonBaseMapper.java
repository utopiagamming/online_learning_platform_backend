package cn.exam.dao.mapper.base;


import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.delete.DeleteByPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.delete.DeleteMapper;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;
import tk.mybatis.mapper.common.base.select.*;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeySelectiveMapper;
import tk.mybatis.mapper.common.condition.*;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;
import tk.mybatis.mapper.common.example.UpdateByExampleSelectiveMapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

@RegisterMapper
public interface CommonBaseMapper<T> extends Marker,
		//tk-mybatis 基础查询接口
		SelectOneMapper<T>,
		SelectMapper<T>,
		SelectAllMapper<T>,
		SelectCountMapper<T>,
		SelectByPrimaryKeyMapper<T>,
		ExistsWithPrimaryKeyMapper<T>,
		SelectByIdsMapper<T>,
		SelectByConditionMapper<T>,
		SelectCountByConditionMapper<T>,
		SelectByExampleMapper<T>,

		//基础更新接口
		UpdateByPrimaryKeyMapper<T>,
        UpdateByPrimaryKeySelectiveMapper<T>,
        UpdateByConditionMapper<T>,
        UpdateByConditionSelectiveMapper<T>,
        UpdateByExampleSelectiveMapper<T>,

        //基础删除接口
        DeleteMapper<T>,
        DeleteByPrimaryKeyMapper<T>,
        DeleteByConditionMapper<T>,
        DeleteByIdsMapper<T>,
        //基础新增
        BaseInsertMapper<T>,
        InsertSelectiveMapper<T>,
        BaseMapper<T>,

        //高级api接口
        ExampleMapper<T>,
        RowBoundsMapper<T>,
        MySqlMapper<T>,
        ConditionMapper<T>{

}