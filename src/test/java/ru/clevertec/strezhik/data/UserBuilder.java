package ru.clevertec.strezhik.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@NoArgsConstructor(staticName = "aUser")
@AllArgsConstructor
@With
public class UserBuilder implements TestBuilder<User>{

    private Integer object = 5;
    private String[] array = {"inki", "yanki", "tsianki"};
    private String name = "Fedor";
    private int integer = 89;
    private String job = "Rexona";
    private int[] integerArray = {3, 4, 3};
    private boolean some = true;
    private List<String> list;

    @Override
    public User build() {
        User user = new User();
        user.setObject(object);
        user.setArray(array);
        user.setName(name);
        user.setInteger(integer);
        user.setJob(job);
        user.setIntegerArray(integerArray);
        user.setSome(some);
        user.setList(list);
        return user;
    }
}
