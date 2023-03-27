package org.ssp.repository.entity;

import jakarta.persistence.*;
import org.ssp.ResultValue;
import org.ssp.StepValue;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @ManyToOne
    private User user;
    private StepValue game_step_1;
    private StepValue user_step_1;
    private StepValue game_step_2;
    private StepValue user_step_2;
    private StepValue game_step_3;
    private StepValue user_step_3;
    private ResultValue result;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StepValue getGame_step_1() {
        return game_step_1;
    }

    public void setGame_step_1(StepValue game_step_1) {
        this.game_step_1 = game_step_1;
    }

    public StepValue getUser_step_1() {
        return user_step_1;
    }

    public void setUser_step_1(StepValue user_step_1) {
        this.user_step_1 = user_step_1;
    }

    public StepValue getGame_step_2() {
        return game_step_2;
    }

    public void setGame_step_2(StepValue game_step_2) {
        this.game_step_2 = game_step_2;
    }

    public StepValue getUser_step_2() {
        return user_step_2;
    }

    public void setUser_step_2(StepValue user_step_2) {
        this.user_step_2 = user_step_2;
    }

    public StepValue getGame_step_3() {
        return game_step_3;
    }

    public void setGame_step_3(StepValue game_step_3) {
        this.game_step_3 = game_step_3;
    }

    public StepValue getUser_step_3() {
        return user_step_3;
    }

    public void setUser_step_3(StepValue user_step_3) {
        this.user_step_3 = user_step_3;
    }

    public ResultValue getResult() {
        return result;
    }

    public void setResult(ResultValue result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
