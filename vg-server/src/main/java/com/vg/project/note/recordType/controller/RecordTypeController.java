package com.vg.project.note.recordType.controller;

import java.util.List;

import com.vg.framework.aspectj.lang.annotation.Log;
import com.vg.framework.aspectj.lang.enums.BusinessType;
import com.vg.framework.web.controller.BaseController;
import com.vg.framework.web.domain.AjaxResult;
import com.vg.framework.web.page.TableDataInfo;
import com.vg.project.note.recordType.domain.RecordType;
import com.vg.project.note.recordType.service.IRecordTypeService;
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
 * 记账类型 信息操作处理
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
@Controller
@RequestMapping("/note/recordType")
public class RecordTypeController extends BaseController
{
    private String prefix = "note/recordType";
	
	@Autowired
	private IRecordTypeService recordTypeService;
	
	@RequiresPermissions("note:recordType:view")
	@GetMapping()
	public String recordType()
	{
	    return prefix + "/recordType";
	}
	
	/**
	 * 查询记账类型列表
	 */
	@RequiresPermissions("note:recordType:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(RecordType recordType)
	{
		startPage();
        List<RecordType> list = recordTypeService.selectRecordTypeList(recordType);
		return getDataTable(list);
	}
	
	/**
	 * 新增记账类型
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存记账类型
	 */
	@RequiresPermissions("note:recordType:add")
	@Log(title = "记账类型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(RecordType recordType)
	{		
		return toAjax(recordTypeService.insertRecordType(recordType));
	}

	/**
	 * 修改记账类型
	 */
	@GetMapping("/edit/{iD}")
	public String edit(@PathVariable("iD") Long iD, ModelMap mmap)
	{
		RecordType recordType = recordTypeService.selectRecordTypeById(iD);
		mmap.put("recordType", recordType);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存记账类型
	 */
	@RequiresPermissions("note:recordType:edit")
	@Log(title = "记账类型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(RecordType recordType)
	{		
		return toAjax(recordTypeService.updateRecordType(recordType));
	}
	
	/**
	 * 删除记账类型
	 */
	@RequiresPermissions("note:recordType:remove")
	@Log(title = "记账类型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(recordTypeService.deleteRecordTypeByIds(ids));
	}
	
}
