package org.ssp.repository.entity;

import jakarta.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    private String game_step_1;
    private String user_step_1;
    private String game_step_2;
    private String user_step_2;
    private String game_step_3;
    private String user_step_3;
    private String result;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGame_step_1() {
        return game_step_1;
    }

    public void setGame_step_1(String game_step_1) {
        this.game_step_1 = game_step_1;
    }

    public String getUser_step_1() {
        return user_step_1;
    }

    public void setUser_step_1(String user_step_1) {
        this.user_step_1 = user_step_1;
    }

    public String getGame_step_2() {
        return game_step_2;
    }

    public void setGame_step_2(String game_step_2) {
        this.game_step_2 = game_step_2;
    }

    public String getUser_step_2() {
        return user_step_2;
    }

    public void setUser_step_2(String user_step_2) {
        this.user_step_2 = user_step_2;
    }

    public String getGame_step_3() {
        return game_step_3;
    }

    public void setGame_step_3(String game_step_3) {
        this.game_step_3 = game_step_3;
    }

    public String getUser_step_3() {
        return user_step_3;
    }

    public void setUser_step_3(String user_step_3) {
        this.user_step_3 = user_step_3;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
