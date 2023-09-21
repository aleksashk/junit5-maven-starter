package by.flameksandr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    Integer id;
    String username;
    String password;
}
