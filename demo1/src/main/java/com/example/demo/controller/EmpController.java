package com.example.demo.controller;

import com.example.demo.pojo.Emp;
import com.example.demo.pojo.Result;
import com.example.demo.utils.XmlParserUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpController {

        @RequestMapping("/listEmp")
    public Result list(){
//1.加载emp.xml,并解析emp.xml中的数据
        String file = this.getClass().getClassLoader().getResource("emp.xml").getFile();
            List<Emp> empList = XmlParserUtils.parse(file, Emp.class);

//2.对员工信息中的gender,job字段进行处理

        empList.stream().forEach(emp -> {
            String gender = emp.getGender();
            if("1".equals(gender)){
                emp.setGender("男");
            } else if ("2".equals(gender)) {
                emp.setGender("女");
            }

            String job = emp.getJob();
            if("1".equals(job)){
                emp.setJob("讲师");
            } else if ("2".equals(job)) {
                emp.setJob("班主任");
            }else if ("3".equals(job)) {
                emp.setJob("就业指导");
            }
        });
//3.组装数据并且返回
            return Result.success(empList);
        }
}
