package com.vg.project.note.recordType.service;

import com.vg.project.note.recordType.domain.RecordType;

import java.util.List;

/**
 * 记账类型 服务层
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
public interface IRecordTypeService 
{
	/**
     * 查询记账类型信息
     * 
     * @param iD 记账类型ID
     * @return 记账类型信息
     */
	public RecordType selectRecordTypeById(Long iD);
	
	/**
     * 查询记账类型列表
     * 
     * @param recordType 记账类型信息
     * @return 记账类型集合
     */
	public List<RecordType> selectRecordTypeList(RecordType recordType);
	
	/**
     * 新增记账类型
     * 
     * @param recordType 记账类型信息
     * @return 结果
     */
	public int insertRecordType(RecordType recordType);
	
	/**
     * 修改记账类型
     * 
     * @param recordType 记账类型信息
     * @return 结果
     */
	public int updateRecordType(RecordType recordType);
		
	/**
     * 删除记账类型信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRecordTypeByIds(String ids);
	
}
