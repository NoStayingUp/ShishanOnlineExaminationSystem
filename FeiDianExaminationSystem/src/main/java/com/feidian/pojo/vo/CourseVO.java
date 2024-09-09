package com.feidian.pojo.vo;

import com.feidian.pojo.entity.Course;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseVO {

    private List<Course> courses;
}
