package com.example.LoginPage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loginUser")
@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;
    private String userName;
    private String password;
}
