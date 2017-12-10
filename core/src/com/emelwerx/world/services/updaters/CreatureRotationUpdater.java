package com.emelwerx.world.services.updaters;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.emelwerx.world.databags.components.ModelComponent;
import com.emelwerx.world.databags.systemstates.CreatureSystemState;

public class CreatureRotationUpdater {

    public static Quaternion update(ModelComponent targetModelComponent,
                                                         ModelComponent hunterModelComponent,
                                                         CreatureSystemState creatureSystemState) {
        float theta = getTheta(targetModelComponent, hunterModelComponent);
        return creatureSystemState.getQuaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);
    }

    private static float getTheta(ModelComponent target,
                                  ModelComponent hunter) {
        Vector3 targetPosition = target.getPosition();
        target.getInstance().transform.getTranslation(targetPosition);

        Vector3 hunterPosition = hunter.getPosition();
        hunter.getInstance().transform.getTranslation(hunterPosition);

        float dX = targetPosition.x - hunterPosition.x;
        float dZ = targetPosition.z - hunterPosition.z;
        return (float) (Math.atan2(dX, dZ));
    }
}