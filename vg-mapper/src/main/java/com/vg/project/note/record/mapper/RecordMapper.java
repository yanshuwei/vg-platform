package com.vg.project.note.record.mapper;

import com.vg.project.note.record.domain.Record;

import java.util.List;

/**
 * 记账记录 数据层
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
public interface RecordMapper 
{
	/**
     * 查询记账记录信息
     * 
     * @param iD 记账记录ID
     * @return 记账记录信息
     */
	public Record selectRecordById(Integer iD);
	
	/**
     * 查询记账记录列表
     * 
     * @param record 记账记录信息
     * @return 记账记录集合
     */
	public List<Record> selectRecordList(Record record);
	
	/**
     * 新增记账记录
     * 
     * @param record 记账记录信息
     * @return 结果
     */
	public int insertRecord(Record record);
	
	/**
     * 修改记账记录
     * 
     * @param record 记账记录信息
     * @return 结果
     */
	public int updateRecord(Record record);
	
	/**
     * 删除记账记录
     * 
     * @param iD 记账记录ID
     * @return 结果
     */
	public int deleteRecordById(Integer iD);
	
	/**
     * 批量删除记账记录
     * 
     * @param iDs 需要删除的数据ID
     * @return 结果
     */
	public int deleteRecordByIds(String[] iDs);

	List<Record> selectRecordListCascade(Record record);
}