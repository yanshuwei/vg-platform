package com.vg.project.note.recordType.service;

import java.util.List;

import com.vg.common.support.Convert;
import com.vg.project.note.recordType.domain.RecordType;
import com.vg.project.note.recordType.mapper.RecordTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 记账类型 服务层实现
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
@Service
public class RecordTypeServiceImpl implements IRecordTypeService 
{
	@Autowired
	private RecordTypeMapper recordTypeMapper;

	/**
     * 查询记账类型信息
     * 
     * @param iD 记账类型ID
     * @return 记账类型信息
     */
    @Override
	public RecordType selectRecordTypeById(Long iD)
	{
	    return recordTypeMapper.selectRecordTypeById(iD);
	}
	
	/**
     * 查询记账类型列表
     * 
     * @param recordType 记账类型信息
     * @return 记账类型集合
     */
	@Override
	public List<RecordType> selectRecordTypeList(RecordType recordType)
	{
	    return recordTypeMapper.selectRecordTypeList(recordType);
	}
	
    /**
     * 新增记账类型
     * 
     * @param recordType 记账类型信息
     * @return 结果
     */
	@Override
	public int insertRecordType(RecordType recordType)
	{
	    return recordTypeMapper.insertRecordType(recordType);
	}
	
	/**
     * 修改记账类型
     * 
     * @param recordType 记账类型信息
     * @return 结果
     */
	@Override
	public int updateRecordType(RecordType recordType)
	{
	    return recordTypeMapper.updateRecordType(recordType);
	}

	/**
     * 删除记账类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRecordTypeByIds(String ids)
	{
		return recordTypeMapper.deleteRecordTypeByIds(Convert.toStrArray(ids));
	}
	
}
