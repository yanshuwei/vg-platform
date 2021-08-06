package com.vg.project.note.record.service;

import java.util.List;

import com.vg.common.support.Convert;
import com.vg.project.note.record.domain.Record;
import com.vg.project.note.record.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 记账记录 服务层实现
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
@Service
public class RecordServiceImpl implements IRecordService 
{
	@Autowired
	private RecordMapper recordMapper;

	/**
     * 查询记账记录信息
     * 
     * @param iD 记账记录ID
     * @return 记账记录信息
     */
    @Override
	public Record selectRecordById(Integer iD)
	{
	    return recordMapper.selectRecordById(iD);
	}
	
	/**
     * 查询记账记录列表
     * 
     * @param record 记账记录信息
     * @return 记账记录集合
     */
	@Override
	public List<Record> selectRecordList(Record record)
	{
	    return recordMapper.selectRecordList(record);
	}
	
    /**
     * 新增记账记录
     * 
     * @param record 记账记录信息
     * @return 结果
     */
	@Override
	public int insertRecord(Record record)
	{
	    return recordMapper.insertRecord(record);
	}
	
	/**
     * 修改记账记录
     * 
     * @param record 记账记录信息
     * @return 结果
     */
	@Override
	public int updateRecord(Record record)
	{
	    return recordMapper.updateRecord(record);
	}

	/**
     * 删除记账记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRecordByIds(String ids)
	{
		return recordMapper.deleteRecordByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<Record> selectRecordListCascade(Record record) {
		return recordMapper.selectRecordListCascade(record);
	}

}
