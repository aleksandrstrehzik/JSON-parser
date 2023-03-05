package ru.clevertec.strezhik.data;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User {
    private Integer object;
    private String[] array;
    private String name;
    private int integer;
    private String job;
    private int[] integerArray;
    private boolean some;
    private List<String> list;
}
