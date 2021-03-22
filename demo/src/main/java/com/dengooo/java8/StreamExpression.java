package com.dengooo.java8;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * java8流式表达式基本用法
 */
public class StreamExpression {
    /**
     * 按年级分组
     * @param students
     * @return
     */
    public Map<String, List<Student>> groupByGrade(List<Student> students) {
        Map<String, List<Student>> groupMap = new HashMap<>();
        for (int i = 0; i < students.size(); i++) {
            String grade = students.get(i).getGrade();
            List<Student> groupList = groupMap.get(grade);
            if (Objects.isNull(groupList)) {
                groupList = new ArrayList<>();
                groupMap.put(grade, groupList);
            }
            groupList.add(students.get(i));
        }
        return groupMap;
    }

    /**
     * 根据姓名过滤
     * @param students
     * @param filterStr
     * @return
     */
    public List<Student> filterUserName(List<Student> students, String filterStr) {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            Student stu = students.get(i);
            if (stu.getUserName().contains(filterStr)) {
                list.add(stu);
            }
        }
        return list;
    }

    /**
     * 根据年级过滤
     * @param students
     * @param filterStr
     * @return
     */
    public List<Student> filterUserGrade(List<Student> students, String filterStr) {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            Student stu = students.get(i);
            if (stu.getGrade().compareTo(filterStr) >= 0) {
                list.add(stu);
            }
        }
        return list;
    }
    /**
     * 根据ID排序
     * @param students
     * @return
     */
    public void orderById(List<Student> students) {
        //for....
        students.sort((stu1, stu2) -> {
            if (stu1.getId() > stu2.getId()) {
                return 1;
            } else if (stu1.getId().equals(stu2.getId())) {
                return 0;
            } else {
                return -1;
            }
        });
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"李一","1"));
        students.add(new Student(5,"李五","1"));
        students.add(new Student(8,"李八一","2"));
        students.add(new Student(7,"李七一","3"));
        students.add(new Student(9,"李九","4"));
        students.add(new Student(2,"李二一","1"));
        students.add(new Student(6,"李六","2"));
        students.add(new Student(3,"李三","3"));
        students.add(new Student(4,"李四一","2"));

//        StreamExpression streamExpression = new StreamExpression();
//        //1.先找出名字中包含“一”的
//        students = streamExpression.filterUserName(students, "一");
//        //2.找出年级大于等于2的
//        students = streamExpression.filterUserGrade(students, "2");
//        //3.根据ID排序
//        streamExpression.orderById(students);
//        //4.根据年级分组
//        Map<String, List<Student>> stringListMap = streamExpression.groupByGrade(students);
//        System.out.println(stringListMap);

        Map<String, List<Student>> collect = students.stream()
                .filter(predicate -> predicate.getUserName().contains("一"))
                .filter(predicate -> predicate.getGrade().compareTo("2") >= 0)
                .sorted(Comparator.comparing(Student::getId)) //降序 Comparator.comparing(Student::getId).reversed()
                .collect(Collectors.groupingBy(Student::getGrade));
//                .collect(Collectors.toList());
        System.out.println(collect);
        //map,findAny
//        students.stream().map(Student::getGrade)
    }
}

class Student {
    private Integer id;
    private String userName;
    private String grade;

    public Student() {
    }

    public Student(Integer id, String userName, String grade) {
        this.id = id;
        this.userName = userName;
        this.grade = grade;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
