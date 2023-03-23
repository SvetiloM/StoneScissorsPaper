package org.ssp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.ssp.repository.entity.Game;
import org.ssp.repository.entity.User;
import org.ssp.services.ResultValues;
import org.ssp.services.StepValues;

public interface GameRepository extends CrudRepository<Game, Integer> {

    @Query("SELECT g from Game g where g.user=?1 and result=NULL")
    Game getLastGame(User user);

    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.game_step_1=?2, g.user_step_1=?3 WHERE g.id=?1")
    void setStep1(Integer id, StepValues gameStep, StepValues userStep);

    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.game_step_2=?2, g.user_step_2=?3 WHERE g.id=?1")
    void setStep2(Integer id, StepValues gameStep, StepValues userStep);

    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.game_step_3=?2, g.user_step_3=?3 WHERE g.id=?1")
    void setStep3(Integer id, StepValues gameStep, StepValues userStep);

    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.result=?2 WHERE g.id=?1")
    void setResult(Integer id, ResultValues result);

}
