package com.vg.project.monitor.maps.controller;

import com.vg.project.monitor.map.domain.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sqp
 * @date 2019/3/13 16:06
 */
@Controller
public class MapsController
{
    @PostMapping("/items")
    @ResponseBody
    public List<Maps> list(@RequestParam("beginTime") String beginTime,
                           @RequestParam("endTime") String endTime) {
        List<Maps> result = new ArrayList<>();
        result.add(new Maps("如风达", "已开班", 113.76, 23.04));
        result.add(new Maps("南京", "未开班", 118.78, 32.04));
        return result;
    }
}
