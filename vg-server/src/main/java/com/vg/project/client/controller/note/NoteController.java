package com.vg.project.client.controller.note;

import com.vg.common.utils.StringUtils;
import com.vg.framework.web.controller.BaseController;
import com.vg.project.client.annotation.RequestDecode;
import com.vg.project.client.annotation.ResponseEncode;
import com.vg.project.client.dto.user.UserDto;
import com.vg.project.client.utils.ResponseData;
import com.vg.project.note.record.domain.Record;
import com.vg.project.note.record.service.IRecordService;
import com.vg.project.note.recordType.domain.RecordType;
import com.vg.project.note.recordType.service.IRecordTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 登录验证
 *
 * @author James
 */
@RestController
public class NoteController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private IRecordTypeService recordTypeService;
    @Autowired
    private IRecordService recordService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final static String[] weekArr = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

    @RequestDecode
    @ResponseEncode
    @PostMapping(value = "/client/recordTypeList")
    public ResponseData recordTypeList(@RequestBody UserDto dto) {
        ResponseData result;
        try {
            List<RecordType> list = recordTypeService.selectRecordTypeList(new RecordType());
            result = new ResponseData("10000", "用户登录验证通过！");
            result.setProperties(list);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseData("100002", "用户名或密码错误！");
        }

        return result;
    }

    @RequestDecode
    @ResponseEncode
    @PostMapping(value = "/client/recordList")
    public ResponseData recordList(@RequestBody Record record) {
        String userId = request.getHeader("userId");
        ResponseData result;
        try {
            record.setUserId(Long.parseLong(userId));
            record.setDate(record.getDate() + "%");
            List<Record> list = recordService.selectRecordListCascade(record);
            result = new ResponseData("10000", "获取记账列表成功！");
            result.setProperties(list);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseData("100002", "获取记账列表失败！");
        }

        return result;
    }

    @RequestDecode
    @ResponseEncode
    @PostMapping(value = "/client/record/submit")
    public ResponseData submit(@RequestBody Record dto) {
        logger.info("上传的Record:"+dto.toString());
        ResponseData result;
        String userId = request.getHeader("userId");
        try {
            if(dto != null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(dto.getDate()));
                String week = weekArr[calendar.get(Calendar.DAY_OF_WEEK)-1];
                dto.setWeek(week);
                dto.setUserId(Long.parseLong(userId));
                recordService.insertRecord(dto);
            }
            result = new ResponseData("10000", "提交成功！");
//            result.setProperties(list);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseData("100002", "提交失败，请联系管理员！");
        }

        return result;
    }
}
