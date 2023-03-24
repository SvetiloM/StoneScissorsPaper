package org.ssp.repository.entity;

import jakarta.persistence.*;
import org.ssp.ResultValues;
import org.ssp.StepValues;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @ManyToOne
    private User user;
    private StepValues game_step_1;
    private StepValues user_step_1;
    private StepValues game_step_2;
    private StepValues user_step_2;
    private StepValues game_step_3;
    private StepValues user_step_3;
    private ResultValues result;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StepValues getGame_step_1() {
        return game_step_1;
    }

    public void setGame_step_1(StepValues game_step_1) {
        this.game_step_1 = game_step_1;
    }

    public StepValues getUser_step_1() {
        return user_step_1;
    }

    public void setUser_step_1(StepValues user_step_1) {
        this.user_step_1 = user_step_1;
    }

    public StepValues getGame_step_2() {
        return game_step_2;
    }

    public void setGame_step_2(StepValues game_step_2) {
        this.game_step_2 = game_step_2;
    }

    public StepValues getUser_step_2() {
        return user_step_2;
    }

    public void setUser_step_2(StepValues user_step_2) {
        this.user_step_2 = user_step_2;
    }

    public StepValues getGame_step_3() {
        return game_step_3;
    }

    public void setGame_step_3(StepValues game_step_3) {
        this.game_step_3 = game_step_3;
    }

    public StepValues getUser_step_3() {
        return user_step_3;
    }

    public void setUser_step_3(StepValues user_step_3) {
        this.user_step_3 = user_step_3;
    }

    public ResultValues getResult() {
        return result;
    }

    public void setResult(ResultValues result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
