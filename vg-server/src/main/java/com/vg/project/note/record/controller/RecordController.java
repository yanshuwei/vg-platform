package com.vg.project.note.record.controller;

import java.util.List;

import com.vg.framework.aspectj.lang.annotation.Log;
import com.vg.framework.aspectj.lang.enums.BusinessType;
import com.vg.framework.web.controller.BaseController;
import com.vg.framework.web.domain.AjaxResult;
import com.vg.framework.web.page.TableDataInfo;
import com.vg.project.note.record.domain.Record;
import com.vg.project.note.record.service.IRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 记账记录 信息操作处理
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
@Controller
@RequestMapping("/note/record")
public class RecordController extends BaseController
{
    private String prefix = "note/record";
	
	@Autowired
	private IRecordService recordService;
	
	@RequiresPermissions("note:record:view")
	@GetMapping()
	public String record()
	{
	    return prefix + "/record";
	}
	
	/**
	 * 查询记账记录列表
	 */
	@RequiresPermissions("note:record:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Record record)
	{
		startPage();
        List<Record> list = recordService.selectRecordList(record);
		return getDataTable(list);
	}
	
	/**
	 * 新增记账记录
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存记账记录
	 */
	@RequiresPermissions("note:record:add")
	@Log(title = "记账记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Record record)
	{		
		return toAjax(recordService.insertRecord(record));
	}

	/**
	 * 修改记账记录
	 */
	@GetMapping("/edit/{iD}")
	public String edit(@PathVariable("iD") Integer iD, ModelMap mmap)
	{
		Record record = recordService.selectRecordById(iD);
		mmap.put("record", record);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存记账记录
	 */
	@RequiresPermissions("note:record:edit")
	@Log(title = "记账记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Record record)
	{		
		return toAjax(recordService.updateRecord(record));
	}
	
	/**
	 * 删除记账记录
	 */
	@RequiresPermissions("note:record:remove")
	@Log(title = "记账记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(recordService.deleteRecordByIds(ids));
	}
	
}
